package com.chen.infoapp.identity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 用户注销控制器
 */
@Controller
public class LogoutController {

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session){
        try{
            //注销当前对话
            session.invalidate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/oa/login";
    }
}
