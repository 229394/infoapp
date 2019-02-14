package com.chen.infoapp.identity.service;

import com.chen.infoapp.identity.dto.UserModule;
import com.chen.infoapp.identity.model.Role;

import java.util.List;

public interface PopedomService {

    /**
     * 查询当前角色在当前模块下拥有的操作权限编号
     * @param role 角色对象
     * @param parentCode 父结点
     * @return 权限编号列表
     */
    List<String> getRoleModuleOperasCodes(Role role,String parentCode);

    /**
     * 给角色绑定某个模块下的操作权限
     * @param codes 编号
     * @param role 角色对象
     * @param parentCode 父结点
     */
    void bindPopedom(String codes,Role role,String parentCode);

    /**
     * 查询当前用户的权限模块
     * @return 权限模块列表
     */
    List<UserModule> getUserPopedomModules();
}
