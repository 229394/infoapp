package com.chen.infoapp.identity.service;

import com.chen.infoapp.common.pager.PageModel;
import com.chen.infoapp.identity.model.User;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 用户业务接口类
 */
public interface UserService {

    /**
     * 用户登录
     * @param params
     * @return Map结果
     */
    Map<String,Object> login(Map<String,Object> params);

    /**
     * @return 异步加载部门和职位的Json字符,写回页面
     */
    Map<String,Object> findAllDeptsAndJobsAjax();

    /**
     * 根据用户主键获取用户信息
     * @param userId 主键
     * @return 用户对象
     */
    User getUserById(String userId);

    /**
     * 修改自己
     * @param user 用户对象
     */
    void updateSelf(User user, HttpSession session);

    /**
     * 分页查询用户信息
     * @param user 用户对象
     * @param pageModel 分页对象
     * @return 对象信息列表
     */
    List<User> getUsersByPage(User user, PageModel pageModel);

    /**
     * 通过id编号删除用户
     * @param ids id字符串
     */
    void deleteUserByUserIds(String ids);

    /**
     * 检验用户是否已经注册
     * @param userId 用户编号
     */
    String isUserVaildAjax(String userId);

    /**
     * 添加用户
     * @param user 用户对象参数
     */
    void addUser(User user);

    /**
     * 修改用户
     * @param user 用户对象参数
     */
    void updateUser(User user);

    /**
     * 激活用户
     * @param user 用户对象参数
     */
    void activeUser(User user);
}
