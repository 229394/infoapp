package com.chen.infoapp.identity.service.impl;

import com.chen.infoapp.common.CommonContants;
import com.chen.infoapp.common.pager.PageModel;
import com.chen.infoapp.identity.model.Module;
import com.chen.infoapp.identity.model.User;
import com.chen.infoapp.identity.repository.*;
import com.chen.infoapp.identity.service.UserService;
import com.chen.infoapp.util.OaContants;
import com.chen.infoapp.util.OaException;
import com.chen.infoapp.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service("userService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    /** 自动注入userRepository对象 */
    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Autowired
    @Qualifier("moduleRepository")
    private ModuleRepository moduleRepository;

    @Autowired
    @Qualifier("popedomRepository")
    private PopedomRepository popedomRepository;

    @Autowired
    @Qualifier("deptRepository")
    private DeptRepository deptRepository;

    @Autowired
    @Qualifier("jobRepository")
    private JobRepository jobRepository;

    public UserServiceImpl() {
        super();
    }

    @Override
    public Map<String, Object> login(Map<String, Object> params) {
        Map<String,Object> result = new HashMap<>();
        try{
            String userId = (String) params.get("userId");
            String password = (String) params.get("password");
            String vcode = (String) params.get("vcode");
            HttpSession session = (HttpSession) params.get("session");
            if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(password) || StringUtils.isEmpty(vcode)){
                result.put("state",0);
                result.put("tip","参数有为空的!");
            }else {
                /**
                 * 参数不为空
                 * 检验验证码是否正确
                 * 获取session中当前用户对应的验证码
                 */
                String syscode = (String) session.getAttribute(CommonContants.VERIFY_SESSION);
                if (vcode.equalsIgnoreCase(syscode)){
                    /**验证码正确了,根据登录的用户名查询用户:判断登录名是否存在*/
                    User user = getUserById(userId);
                    if (user!=null){
                        /**登录名存在,判断密码 */
                        if (user.getPassword().equals(password)){
                            /**判断用户是否已经被激活 */
                            if (user.getState() == 1){
                                /**登录成功 */
                                /**把登录成功的用户放入当前用户的session会话中 */
                                session.setAttribute(OaContants.USER_SESSION,user);
                                result.put("state",1);
                                result.put("tip","登录成功!");
                                /**把登录成功的用户存入UserHolder中*/
                                UserHolder.addCurrentUser(user);
                                /**查询该用户所拥有的全部操作权限,并存入当前用户的Session会话中 */
                                Map<String,List<String>> userAllOperasPopedomUrls = getUserAllOperasPopedomUrls();
                                session.setAttribute(OaContants.USER_ALL_OPERAS_POPEDOM_URLS,userAllOperasPopedomUrls);
                            }else {
                                /**账号未被激活 */
                                result.put("state",5);
                                result.put("tip","你的账号未被激活,请联系管理员!");
                            }
                        }else {
                            /**密码错误 */
                            result.put("state",2);
                            result.put("tip","密码错误");
                        }
                    }else {
                        /**登录名不存在 */
                        result.put("state",3);
                        result.put("tip","没有该账户!");
                    }
                }else {
                    /**验证码不正确*/
                    result.put("state",4);
                    result.put("tip","验证码不正确!");
                }
            }
            return result;
        }catch (Exception e){
            throw new OaException("异步登录业务层抛出异常!",e);
        }
    }

    /**
     * 通过异步查询所有的部门和职位
     */
    @Override
    public Map<String, Object> findAllDeptsAndJobsAjax() {
        try{
            Map<String,Object> deptJobDatas = new HashMap<>();
            List<Map<String,Object>> deptList = deptRepository.findDepts();
            List<Map<String,Object>> jobList = jobRepository.findJobs();
            deptJobDatas.put("depts",deptList);
            deptJobDatas.put("jobs",jobList);
            return deptJobDatas;
        }catch (Exception e){
            throw new OaException("查询部门与职位信息异常!",e);
        }
    }

    /**
     * 获取用户所有操作权限的地址
     */
    private Map<String,List<String>> getUserAllOperasPopedomUrls(){
        try{
            /**
             * 查询用户所拥有的所有权限操作编号
             */
            List<String> userAllPopedomOperasCodes = popedomRepository.getUserPopedomOperasCodes(UserHolder.getCurrentUser().getUserId());
            if (userAllPopedomOperasCodes != null && userAllPopedomOperasCodes.size()>0){
                Map<String,List<String>> userAllOperasPopedomUrls = new HashMap<>();
                String moduleUrl = "";
                List<String> moduleOperaUrls = null;
                for (String operaCode : userAllPopedomOperasCodes){
                    /**先得到模块的编号*/
                    String parentModuleCode = operaCode.substring(0,operaCode.length()-OaContants.CODE_LEN);
                    /**父模块地址*/
                    moduleUrl = getModuleByCode(parentModuleCode).getUrl();
                    /**判断map集合中是否已经存在该父模块地址*/
                    if (!userAllOperasPopedomUrls.containsKey(moduleUrl)){
                        moduleOperaUrls = new ArrayList<>();
                        userAllOperasPopedomUrls.put(moduleUrl,moduleOperaUrls);
                    }
                    return userAllOperasPopedomUrls;
                }
            }
            return null;
        }catch (Exception e){
            throw new OaException("登录查询用户的权限出现异常!",e);
        }
    }

    private Module getModuleByCode(String code){
        try{
            return moduleRepository.findById(code).get();
        }catch (Exception e){
            throw new OaException("查询模块异常!",e);
        }
    }

    @Override
    public User getUserById(String userId) {
        try{
            User user = userRepository.findById(userId).get();
            if (user!=null){
                if (user.getDept()!=null) user.getDept().getName();
                if (user.getJob()!=null)user.getJob().getName();
                if (user.getCreater()!=null)user.getCreater().getName();
                if (user.getModifier()!=null)user.getModifier().getName();
                if (user.getChecker()!=null)user.getChecker().getName();
                return user;
            }
            return null;
        }catch (Exception e){
            throw new OaException("查询用户失败!",e);
        }
    }

    @Transactional
    @Override
    public void updateSelf(User user, HttpSession session) {
        try {
            /**持久化修改*/
            User sessionUser = userRepository.findById(user.getUserId()).get();
            sessionUser.setModifyDate(new Date());
            sessionUser.setModifier(user);
            sessionUser.setName(user.getName());
            sessionUser.setEmail(user.getEmail());
            sessionUser.setTel(user.getTel());
            sessionUser.setPhone(user.getPhone());
            sessionUser.setQuestion(user.getQuestion());
            sessionUser.setAnswer(user.getAnswer());
            sessionUser.setQqNum(user.getQqNum());
            //获取延迟加载的属性
            if (sessionUser.getDept()!=null) sessionUser.getDept().getName();
            if (sessionUser.getJob()!=null) sessionUser.getJob().getName();
            session.setAttribute(OaContants.USER_SESSION,sessionUser);
        }catch (Exception e){
            throw new OaException("修改用户失败!",e);
        }
    }

    @Override
    public List<User> getUsersByPage(User user, PageModel pageModel) {
        try{
            Page<User> usersPager = userRepository.findAll(new Specification<User>() {
                @Override
                public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> predicates = new ArrayList<>();
                    if (user!=null){
                        /**用于传入用于查询的姓名*/
                        if (!StringUtils.isEmpty(user.getName())){
                            predicates.add(cb.like(root.<String> get("name"),"%"+user.getName()+"%"));
                        }
                        /**用于传入用于查询的手机号码*/
                        if (!StringUtils.isEmpty(user.getPhone())){
                            predicates.add(cb.like(root.<String> get("phone"),"%"+user.getPhone()+"%"));
                        }
                        /**用于传入用于查询的部门*/
                        if (user.getDept()!=null && user.getDept().getId()!=null && user.getDept().getId()!=0){
                            root.join("dept",JoinType.INNER);
                            Path<Long> d_id = root.get("dept").get("id");
                            predicates.add(cb.equal(d_id,user.getDept().getId()));
                        }
                        /**用于传入用于查询的职位*/
                        if (user.getJob() != null && !StringUtils.isEmpty(user.getJob().getCode()) && !user.getJob().getCode().equals("0")) {
                            root.join("job",JoinType.INNER);
                            Path<String> j_id = root.get("job").get("code");
                            predicates.add(cb.equal(j_id,user.getJob().getCode()));
                        }
                    }
                    return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
                }
            },PageRequest.of(pageModel.getPageIndex()-1,pageModel.getPageSize()));
            pageModel.setRecordCount(usersPager.getTotalElements());
            /**获取每个用户的延迟加载属性*/
            List<User> users = usersPager.getContent();
            for (User u:users){
                if (u.getDept()!=null) u.getDept().getName();
                if (u.getJob()!=null) u.getJob().getName();
                if (u.getChecker()!=null) u.getChecker().getName();
            }
            return users;
        }catch (Exception e){
            throw new OaException("查询用户信息异常!",e);
        }
    }

    @Transactional
    @Override
    public void deleteUserByUserIds(String ids) {
        try{
            List<User> users = new ArrayList<>();
            for (String id : ids.split(",")){
                User user = new User();
                user.setUserId(id);
                users.add(user);
            }
            userRepository.deleteInBatch(users);
        }catch (Exception e){
            throw new OaException("删除用户信息异常!",e);
        }
    }

    @Override
    public String isUserVaildAjax(String userId) {
        try {
            User user = userRepository.findById(userId).get();
            return user==null?"success":"error";
        } catch (Exception e) {
            throw new OaException("校验用户登录名是否注册异常了", e);
        }
    }

    @Transactional
    @Override
    public void addUser(User user) {
        try{
            user.setCreateDate(new Date());
            user.setCreater(UserHolder.getCurrentUser());
            userRepository.save(user);
        }catch (Exception e){
            throw new OaException("添加用户信息异常!",e);
        }
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        try{
            /**持久化修改 */
            User sessionUser = userRepository.findById(user.getUserId()).get();
            sessionUser.setModifyDate(new Date());
            sessionUser.setModifier(UserHolder.getCurrentUser());
            sessionUser.setPassword(user.getPassword());
            sessionUser.setName(user.getName());
            sessionUser.setDept(user.getDept());
            sessionUser.setJob(user.getJob());
            sessionUser.setEmail(user.getEmail());
            sessionUser.setSex(user.getSex());
            sessionUser.setTel(user.getTel());
            sessionUser.setPhone(user.getPhone());
            sessionUser.setQuestion(user.getQuestion());
            sessionUser.setAnswer(user.getAnswer());
            sessionUser.setQqNum(user.getQqNum());
            }catch (Exception e){
            throw new OaException("修改用户信息失败!",e);
        }
    }

    //必须要添加事务
    @Transactional
    @Override
    public void activeUser(User user) {
        try{
            User sessionUser = userRepository.findById(user.getUserId()).get();
            sessionUser.setCheckDate(new Date());
            sessionUser.setChecker(UserHolder.getCurrentUser());
            sessionUser.setState(user.getState());
            System.out.println(user.getState());
        }catch (Exception e){
            throw new OaException("激活用户失败!",e);
        }
    }
}
