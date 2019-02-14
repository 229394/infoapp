package com.chen.infoapp.identity.controller;

import com.chen.infoapp.common.pager.PageModel;
import com.chen.infoapp.identity.model.Module;
import com.chen.infoapp.identity.service.ModuleService;
import com.chen.infoapp.identity.vo.TreeData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 模块控制器
 */
@Controller
@RequestMapping(value = "/identity/module")
public class ModuleController {

    @Resource
    private ModuleService moduleService;

    /**
     * 模块管理
     * @return 地址
     */
    @RequestMapping(value = "/mgrModule")
    public String mgrModule(){
        return "identity/module/mgrModule";
    }

    /**
     * 加载所有的模块树
     * @return treedata的列表
     */
    @ResponseBody
    @RequestMapping(value = "/loadAllModuleTrees",produces = "application/json;charset=utf-8")
    public List<TreeData> loadAllModuleTrees(){
        try{
            return moduleService.loadAllModuleTrees();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过父节点获取模块名称
     * @param parentCode 父节点
     * @param pageModel 分页实体
     * @param model 视图
     */
    @RequestMapping(value = "/getModulesByParent")
    public String getModulesByParent(String parentCode, PageModel pageModel, Model model){
        try{
            List<Module> sonModules = moduleService.getModulesByParent(parentCode,pageModel);
            model.addAttribute("modules",sonModules);
            model.addAttribute("parentCode",parentCode);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "identity/module/module";
    }

    /**
     * 删除模块
     * @param ids id字符串
     * @param model 视图
     */
    @RequestMapping(value = "/deleteModules")
    public String deleteModules(String ids,Model model){
        try{
            moduleService.deleteModules(ids);
            model.addAttribute("tip","删除成功!");
        }catch (Exception e){
            model.addAttribute("tip","删除失败!");
            e.printStackTrace();
        }
        return "forward:/identity/module/getModulesByParent";
    }

    /**
     * 添加模块
     * @param parentCode 父节点
     * @param module 模块对象
     * @param model 视图
     */
    @RequestMapping(value = "/addModule")
    public String addModule(String parentCode,Module module,Model model){
        try{
            moduleService.addModule(parentCode,module);
            model.addAttribute("tip","添加成功!");
            model.addAttribute("module",module);
        }catch (Exception e){
            model.addAttribute("tip","添加失败!");
            e.printStackTrace();
        }
        return "identity/module/addModule";
    }

    /**
     * 修改模块
     * @param module 模块对象
     * @param model 视图
     */
    @RequestMapping(value = "/updateModule")
    public String updateModule(Module module,Model model){
        try{
            moduleService.updateModule(module);
            model.addAttribute("tip","修改成功!");
        }catch (Exception e){
            model.addAttribute("tip","修改失败!");
            e.printStackTrace();
        }
        return "identity/module/updateModule";
    }

    @RequestMapping(value = "/showAddModule")
    public String showAddModule(String parentCode,Model model){
        model.addAttribute("parentCode",parentCode);
        return "identity/module/addModule";
    }

    @RequestMapping(value = "/showUpdateModule")
    public String showUpdateModule(Module module,Model model){
        try{
            module = moduleService.getModuleByCode(module.getCode());
            model.addAttribute("module",module);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "identity/module/updateModule";
    }

}
