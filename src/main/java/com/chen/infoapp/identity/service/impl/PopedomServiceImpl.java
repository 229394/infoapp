package com.chen.infoapp.identity.service.impl;

import com.chen.infoapp.identity.dto.UserModule;
import com.chen.infoapp.identity.model.Module;
import com.chen.infoapp.identity.model.Popedom;
import com.chen.infoapp.identity.model.Role;
import com.chen.infoapp.identity.repository.ModuleRepository;
import com.chen.infoapp.identity.repository.PopedomRepository;
import com.chen.infoapp.identity.service.PopedomService;
import com.chen.infoapp.util.OaContants;
import com.chen.infoapp.util.OaException;
import com.chen.infoapp.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service("popedomService")
@Transactional(readOnly = true)
public class PopedomServiceImpl implements PopedomService {

    @Autowired
    @Qualifier("popedomRepository")
    private PopedomRepository popedomRepository;

    @Autowired
    @Qualifier("moduleRepository")
    private ModuleRepository moduleRepository;

    @Override
    public List<String> getRoleModuleOperasCodes(Role role, String parentCode) {
        try{
            List<String> roleModuleOperasCodes = popedomRepository.findByIdAndParentCode(role.getId(),parentCode);
            return roleModuleOperasCodes;
        }catch (Exception e){
            throw new OaException("查询当前角色在当前模块下拥有的操作权限编号异常!",e);
        }
    }

    @Transactional
    @Override
    public void bindPopedom(String codes, Role role, String parentCode) {
        try{
            //1.先清空此角色在此模块下的所有操作权限
            popedomRepository.setByIdAndParentCode(role.getId(),parentCode);
            //2.更新角色模块权限
            if(!StringUtils.isEmpty(codes)){
                Module parent = getModuleByCode(parentCode);
                /**添加一些更新的权限*/
                for (String code:codes.split(",")){
                    /**创建一个权限对象*/
                    Popedom popedom = new Popedom();
                    popedom.setRole(role);
                    popedom.setModule(parent);
                    popedom.setOpera(getModuleByCode(code));
                    popedom.setCreateDate(new Date());
                    popedom.setCreater(UserHolder.getCurrentUser());
                    popedomRepository.save(popedom);
                }
            }
        }catch (Exception e){
            throw new OaException("给角色绑定某个模块的操作权限异常!",e);
        }
    }

    public Module getModuleByCode(String code){
        try{
            return moduleRepository.findById(code).get();
        }catch (Exception e){
            throw new OaException("查询模块异常!",e);
        }
    }

    @Override
    public List<UserModule> getUserPopedomModules() {
        try {
            /**查询当前用户的权限模块 ：先查用户所有的角色,再查这些角色拥有的所有权限模块  */
            List<String> popedomModuleCodes = popedomRepository.getUserPopedomModuleCodes(UserHolder.getCurrentUser().getUserId());
            if(popedomModuleCodes!=null && popedomModuleCodes.size()>0){

                /** 定义一个Map集合用于保存用户的权限模块
                 *  Map<Module,List<Module>>
                 *  {系统管理=[用户管理,角色管理] , 假期模块=[查询信息,用户请假]}
                 *  */
                Map<Module,List<Module>> userModulesMap = new LinkedHashMap<>();
                Module fistModule = null ;
                List<Module> secondModules = null ;
                for(String moduleCode : popedomModuleCodes){
                    /** 截取当前模块的一级模块编号 */
                    String fistCode = moduleCode.substring(0, OaContants.CODE_LEN);
                    /** 查询出一级模块对象 */
                    fistModule = getModuleByCode(fistCode);
                    fistModule.setName(fistModule.getName().replaceAll("-", ""));
                    /**如果map集合中没有包含当前一级模块的key,说明是第一次添加一级模块 */
                    if(!userModulesMap.containsKey(fistModule)){
                        secondModules = new ArrayList<Module>();
                        userModulesMap.put(fistModule, secondModules);
                    }
                    Module secondModule = getModuleByCode(moduleCode);
                    secondModule.setName(secondModule.getName().replaceAll("-", ""));
                    secondModules.add(secondModule);
                }

                List<UserModule> userModules = new ArrayList<>();
                for(Map.Entry<Module, List<Module>> entry : userModulesMap.entrySet()){
                    Module key = entry.getKey();
                    List<Module> value = entry.getValue();
                    UserModule userModule = new UserModule();
                    userModule.setFirstModule(key);
                    userModule.setSecondModules(value);
                    userModules.add(userModule);
                }
                return userModules;

            }
            return null;

        } catch (Exception e) {
            throw new OaException("查询当前用户的权限模块异常!", e);
        }
    }

}
