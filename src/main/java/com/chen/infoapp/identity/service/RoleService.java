package com.chen.infoapp.identity.service;

import com.chen.infoapp.common.pager.PageModel;
import com.chen.infoapp.identity.model.Role;
import com.chen.infoapp.identity.model.User;

import java.util.List;

public interface RoleService {

    /**
     * 分页查询角色信息
     * @param pageModel 分页实体
     * @return 角色列表
     */
    List<Role> getRoleByPage(PageModel pageModel);

    /**
     * 增加角色
     * @param role 角色对象
     */
    void addRole(Role role);

    /**
     * 批量删除角色
     * @param ids id字符串
     */
    void deleteRole(String ids);

    /**
     * 根据id查询角色
     * @param id 编号
     * @return 角色对象
     */
    Role getRoleById(Long id);

    /**
     * 修改角色
     * @param role 角色对象
     */
    void updateRole(Role role);

    /**
     * 分页查询属于这个角色的用户信息
     * @param role 角色对象
     * @param pageModel 分页实体
     * @return 用户信息
     */
    List<User> selectRoleUser(Role role,PageModel pageModel);

    /**
     * 分页查询不属于某个角色的用户
     * @param role 角色对象
     * @param pageModel 分页实体
     * @return 用户信息
     */
    List<User> selectNotRoleUser(Role role,PageModel pageModel);

    /**
     * 给用户绑定角色
     * @param role 角色对象
     * @param ids id字符串
     */
    void bindUser(Role role,String ids);

    /**
     * 给用户解绑
     * @param role 角色
     * @param ids id字符串
     */
    void unBindUser(Role role,String ids);

}
