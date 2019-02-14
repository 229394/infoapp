package com.chen.infoapp.identity.service.impl;

import com.chen.infoapp.common.pager.PageModel;
import com.chen.infoapp.identity.model.Role;
import com.chen.infoapp.identity.model.User;
import com.chen.infoapp.identity.repository.RoleRepository;
import com.chen.infoapp.identity.repository.UserRepository;
import com.chen.infoapp.identity.service.RoleService;
import com.chen.infoapp.util.OaException;
import com.chen.infoapp.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("roleService")
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    @Autowired
    @Qualifier("roleRepository")
    private RoleRepository roleRepository;

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Override
    public List<Role> getRoleByPage(PageModel pageModel) {
        try{
            Sort.Order order = new Sort.Order(Sort.Direction.ASC,"id");
            Sort sort = new Sort(order);
            /**
             * 封装分页实体
             * 参数1:表示当前查询的第几页(默认0,0表示第一页)
             * 参数2:表示每页多少条数据
             * 参数3:封装排序对象
             */
            Pageable page = PageRequest.of(pageModel.getPageIndex()-1,pageModel.getPageSize(),sort);
            Page<Role> rolesPager = roleRepository.findAll(page);
            pageModel.setRecordCount(rolesPager.getTotalElements());
            //pageModel.setTotalSize(rolesPager.getTotalPages());
            /**获取每一个用户的延迟加载属性 */
            List<Role> roles = rolesPager.getContent();
            for (Role r:roles){
                if (r.getModifier()!=null) r.getModifier().getName();
                if (r.getCreater()!=null) r.getCreater().getName();
            }
            return roles;
        }catch (Exception e){
            throw new OaException("查询角色异常!",e);
        }
    }

    @Transactional
    @Override
    public void addRole(Role role) {
        try{
            role.setCreateDate(new Date());
            role.setCreater(UserHolder.getCurrentUser());
            roleRepository.save(role);
        }catch (Exception e){
            throw new OaException("添加角色异常!",e);
        }
    }

    @Transactional
    @Override
    public void deleteRole(String ids) {
        try{
            List<Role> roles = new ArrayList<>();
            for (String id : ids.split(",")){
                Role role = new Role();
                role.setId(Long.valueOf(id));
            }
            //批量删除
            roleRepository.deleteInBatch(roles);
        }catch (Exception e){
            throw new OaException("批量删除角色异常!",e);
        }
    }

    @Override
    public Role getRoleById(Long id) {
        try{
            return roleRepository.findById(id).get();
        }catch (Exception e){
            throw new OaException("根据id查询角色异常!",e);
        }
    }

    @Transactional
    @Override
    public void updateRole(Role role) {
        try{
            Role r = roleRepository.findById(role.getId()).get();
            r.setName(role.getName());
            r.setRemark(role.getRemark());
            r.setModifier(UserHolder.getCurrentUser());
            r.setModifyDate(new Date());
        }catch (Exception e){
            throw new OaException("根据id修改角色异常!",e);
        }
    }

    @Override
    public List<User> selectRoleUser(Role role, PageModel pageModel) {
        try{
            Page<User> usersPager = userRepository.findAll(new Specification<User>() {
                @Override
                public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    /**本集合用于封装查询条件 */
                    List<Predicate> predicates = new ArrayList<>();
                    List<String> userIds = userRepository.findRoleUsers(role.getId());
                    predicates.add(root.get("userId").in(userIds));
                    return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
                }
            },PageRequest.of(pageModel.getPageIndex()-1,pageModel.getPageSize()));
            pageModel.setRecordCount(usersPager.getTotalElements());
            List<User> users = usersPager.getContent();
            for(User u: users){
                if (u.getDept()!=null)u.getDept().getName();
                if (u.getJob()!=null)u.getJob().getName();
                if (u.getChecker()!=null && !StringUtils.isEmpty(u.getChecker()))u.getChecker().getName();
            }
            return users;
        }catch (Exception e){
            throw new OaException("查询属于角色下的用户信息异常!",e);
        }
    }

    @Override
    public List<User> selectNotRoleUser(Role role, PageModel pageModel) {
       try{
           Page<User> usersPager = userRepository.findAll(new Specification<User>() {
               @Override
               public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                   List<Predicate> predicates = new ArrayList<>();
                   List<String> userId = userRepository.getRolesUsers(role.getId());
                   predicates.add(root.get("userId").in(userId));
                   return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
               }
           },PageRequest.of(pageModel.getPageIndex()-1,pageModel.getPageSize()));
           pageModel.setRecordCount(usersPager.getTotalElements());
           List<User> users = usersPager.getContent();
           for (User u:users){
               if (u.getDept()!=null) u.getDept().getName();
               if (u.getJob()!=null && !StringUtils.isEmpty(u.getJob())) u.getJob().getName();
               if (u.getChecker()!=null) u.getChecker().getName();
           }
           return users;
       }catch (Exception e){
           throw new OaException("查询不属于这个角色的用户信息异常!",e);
       }
    }

    @Transactional
    @Override
    public void bindUser(Role role, String ids) {
        try{
            //1.查询角色
            Role session = roleRepository.findById(role.getId()).get();
            //给角色的users添加需要绑定的用户
            for (String userId : ids.split(",")){
                User user = userRepository.findById(userId).get();
                session.getUsers().add(user);
            }
        }catch (Exception e){
            throw new OaException("绑定角色下的用户异常!",e);
        }
    }

    @Transactional
    @Override
    public void unBindUser(Role role, String ids) {
        try{
            Role session = roleRepository.findById(role.getId()).get();
            for (String userId : ids.split(",")){
                User user = userRepository.findById(userId).get();
                session.getUsers().remove(user);
            }
        }catch (Exception e){
            throw new OaException("解绑用户下的角色异常!",e);
        }
    }
}
