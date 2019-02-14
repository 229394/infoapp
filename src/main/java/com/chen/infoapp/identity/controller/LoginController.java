package com.chen.infoapp.identity.controller;

import com.chen.infoapp.identity.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录控制器
 */
@Controller
public class LoginController {

    /**定义业务层对象*/
    @Resource
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/loginAjax",produces = "application/json;charset=utf-8")
    public Map<String,Object> login(@RequestParam("userId")String userId,
                                    @RequestParam("password")String password,
                                    @RequestParam("vcode")String vcode,
                                    HttpSession session){
        try{
            Map<String,Object> params = new HashMap<>();
            params.put("userId",userId);
            params.put("password",password);
            params.put("vcode",vcode);
            params.put("session",session);
            //获得数据
            Map<String,Object> result = userService.login(params);
            //写回客户端
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
