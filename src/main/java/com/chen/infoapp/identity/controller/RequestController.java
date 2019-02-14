package com.chen.infoapp.identity.controller;

import com.chen.infoapp.identity.dto.UserModule;
import com.chen.infoapp.identity.service.PopedomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 主界面控制器
 */
@Controller
@RequestMapping(value = "/oa")
public class RequestController {

    /**定义业务层对象 */
    @Resource
    private PopedomService popedomService;

    @RequestMapping(value = "/login")
    public String requestLogin(){
        //System.out.println("登录成功了!");
        return "login";
    }

    @RequestMapping(value = "/main")
    public String requestMain(Model model){
        try{
            //查询当前用户拥有的所有权限模块
            List<UserModule> userModules = popedomService.getUserPopedomModules();
            model.addAttribute("userPopedomModules",userModules);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "main";
    }

    @RequestMapping(value = "/home")
    public String requestHome(){
        return "home";
    }
}
