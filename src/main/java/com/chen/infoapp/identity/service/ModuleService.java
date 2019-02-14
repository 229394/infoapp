package com.chen.infoapp.identity.service;

import com.chen.infoapp.common.pager.PageModel;
import com.chen.infoapp.identity.model.Module;
import com.chen.infoapp.identity.vo.TreeData;

import java.util.List;

public interface ModuleService {

    /**
     * 加载所有的模块树
     */
    List<TreeData> loadAllModuleTrees();

    /**
     * 根据父节点查询所有的子模块
     * @param parentCode 父节点
     * @param pageModel 分页实体
     */
    List<Module> getModulesByParent(String parentCode, PageModel pageModel);

    /**
     * 根据父节点查询所有的子模块
     * 不分页
     */
    List<Module> getModulesByParent(String parentCode);

    /**
     *批量删除菜单
     * @param ids id字符串
     */
    void deleteModules(String ids);

    /**
     * 为当前父节点菜单添加子节点模块
     * @param parentCode 父节点
     * @param module 模块参数
     */
    void addModule(String parentCode,Module module);

    /**
     * 根据编号查询模块信息
     * @param code 模块编号
     * @return 模块对象
     */
    Module getModuleByCode(String code);

    /**
     * 修改模块
     * @param module 模块参数
     */
    void updateModule(Module module);

}
