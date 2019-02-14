package com.chen.infoapp.identity.controller;

import com.chen.infoapp.identity.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/identity/dept")
public class DeptController {

    /**定义业务层对象 */
    @Resource
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/getAllDeptsAndJobsAjax",produces = "application/json; charset=utf-8")
    public Map<String,Object> getAllDeptsAndJobsAjax() {
        try {
            Thread.sleep(2000);
            Map<String, Object> result = userService.findAllDeptsAndJobsAjax();
            //System.out.println("result:" + result);
            return result;
        }catch(Exception e){
           e.printStackTrace();
        }
        return null;
    }

}
