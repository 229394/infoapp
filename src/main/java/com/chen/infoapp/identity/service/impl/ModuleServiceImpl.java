package com.chen.infoapp.identity.service.impl;

import com.chen.infoapp.common.pager.PageModel;
import com.chen.infoapp.identity.model.Module;
import com.chen.infoapp.identity.repository.ModuleRepository;
import com.chen.infoapp.identity.repository.PopedomRepository;
import com.chen.infoapp.identity.service.ModuleService;
import com.chen.infoapp.identity.vo.TreeData;
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("moduleService")
@Transactional(readOnly = true)
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    @Qualifier("moduleRepository")
    private ModuleRepository moduleRepository;

    //@Autowired
    //@Qualifier("popedomRepository")
    //private PopedomRepository popedomRepository;

    @Override
    public List<TreeData> loadAllModuleTrees() {
        try{
            /**查询所有的模块信息 */
            List<Module> modules = moduleRepository.findAll();
            /**拼装成dtree需要的树节点 */
            List<TreeData> treeDatas = new ArrayList<>();
            for (Module m:modules){
                TreeData treeData = new TreeData();
                treeData.setId(m.getCode());
                treeData.setName(m.getName());
                //长度为4的父节点是0,其余父节点是从开始位置一致截取到总长度减去步长的位置
                String pid = m.getCode().length()==OaContants.CODE_LEN?"0":m.getCode().substring(0,m.getCode().length()-OaContants.CODE_LEN);
                treeData.setPid(pid);
                treeDatas.add(treeData);
            }
            return treeDatas;
        }catch (Exception e){
            throw new OaException("加载模块树异常!",e);
        }
    }

    @Override
    public List<Module> getModulesByParent(String parentCode, PageModel pageModel) {
        try{
            //判断父节点是否为空
            parentCode = parentCode==null?"":parentCode;
            List<Object> values = new ArrayList<>();
            values.add(parentCode+"%");
            values.add(parentCode.length()+OaContants.CODE_LEN);
            //子节点的编号长度是父节点编号长度+步长
            //子节点前几位的编号必须与父节点编码一致
            Page<Module> modulespager = moduleRepository.findAll(new Specification<Module>() {
                @Override
                public Predicate toPredicate(Root<Module> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    //本集合用于封装查询条件
                    List<Predicate> predicates = new ArrayList<>();
                    predicates.add(cb.like(root.get("code"),values.get(0)+""));
                    predicates.add(cb.equal(cb.length(root.get("code")),values.get(1)));
                    return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
                }
            },PageRequest.of(pageModel.getPageIndex()-1,pageModel.getPageSize()));
            //设置查询到的模块总条数
            pageModel.setRecordCount(modulespager.getTotalElements());
            /**取每个用户的延迟加载属性*/
            List<Module> sonModules = modulespager.getContent();
            for (Module m :sonModules){
                if (m.getCreater()!=null)m.getCreater().getName();
                if (m.getModifier()!=null && !StringUtils.isEmpty(m.getModifier()))m.getModifier().getName();
            }
            return sonModules;
        }catch (Exception e){
            throw new OaException("查询子模块异常!",e);
        }
    }

    @Override
    public List<Module> getModulesByParent(String parentCode) {
        try{
            parentCode = parentCode==null?"":parentCode;
            List<Module> sonmodules = moduleRepository.findModules(parentCode+"%",parentCode.length()+OaContants.CODE_LEN);
            for (Module m:sonmodules){
                if (m.getCreater()!=null && !StringUtils.isEmpty(m.getCreater()))m.getCreater().getName();
                if (m.getModifier()!=null)m.getModifier().getName();
            }
            return sonmodules;
        }catch (Exception e){
            throw new OaException("查询子模块异常!",e);
        }
    }

    @Transactional
    @Override
    public void deleteModules(String ids) {
        try{
            for (String code : ids.split(",")){
                moduleRepository.setCode(code);
                //考虑关联清空权限
                //popedomRepository.setByIdAndParentCode(Long.parseLong(ids),code);
            }
        }catch (Exception e){
            throw new OaException("批量删除菜单异常!",e);
        }
    }

    @Transactional
    @Override
    public void addModule(String parentCode, Module module) {
        try{
            module.setCode(getNextSonCode(parentCode,OaContants.CODE_LEN));
            module.setCreateDate(new Date());
            module.setCreater(UserHolder.getCurrentUser());
            moduleRepository.save(module);
        }catch (Exception e){
            throw new OaException("添加子菜单异常!",e);
        }
    }

    public String getNextSonCode(String parentCode,int codeLen) throws Exception{
        parentCode = parentCode==null?"":parentCode;
        /**1.查询当前父节点下的最大子节点编号 */
        String maxSonCode = moduleRepository.findUniqueEntity(parentCode+"%",parentCode.length()+codeLen);
        //System.out.println("当前最大子节点编号是:maxSonCode---->"+maxSonCode);
        String nextSonCode = "";//保存最终的下一个子节点编号
        /**2.判断最大子节点编号是否存在,可能父节点一个子节点都没有*/
        if (StringUtils.isEmpty(maxSonCode)){
            //子节点编号不存在
            String preSuffix = "";
            for (int i=0;i<codeLen-1;i++){
                preSuffix+="0";
            }
            nextSonCode = parentCode+preSuffix+1;
        }else {
            /**子节点编号存在*/
            /**截取当前子节点编号的步长*/
            String currentMaxSonCode = maxSonCode.substring(parentCode.length());
            int maxSonCodeInt = Integer.valueOf(currentMaxSonCode);
            maxSonCodeInt++;
            /**判断编号是否越界*/
            if ((maxSonCodeInt+"").length()>codeLen){
                throw new OaException("编号越界!");
            }else {
                //没有越界
                String preSuffix = "";
                for (int i=0;i<codeLen-(maxSonCodeInt+"").length();i++){
                    preSuffix+="0";
                }
                nextSonCode = parentCode+preSuffix+maxSonCodeInt;
            }
        }
        return nextSonCode;
    }

    @Override
    public Module getModuleByCode(String code) {
        try{
            return moduleRepository.findById(code).get();
        }catch (Exception e){
            throw new OaException("查询模块异常!",e);
        }
    }

    @Transactional
    @Override
    public void updateModule(Module module) {
        try {
            Module sessionModule = moduleRepository.findById(module.getCode()).get();
            sessionModule.setModifier(UserHolder.getCurrentUser());
            sessionModule.setModifyDate(new Date());
            sessionModule.setName(module.getName());
            sessionModule.setRemark(module.getRemark());
            sessionModule.setUrl(module.getUrl());
        }catch (Exception e){
            throw new OaException("修改模块异常!",e);
        }
    }

}
