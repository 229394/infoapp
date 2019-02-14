package com.chen.infoapp.identity.controller;

import com.chen.infoapp.common.pager.PageModel;
import com.chen.infoapp.identity.model.User;
import com.chen.infoapp.identity.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/identity/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/updateSelf")
    public String updateSelf(User user, Model model, HttpSession session){
        try{
            userService.updateSelf(user,session);
            model.addAttribute("tip","修改成功!");
        }catch (Exception e){
            model.addAttribute("tip","修改失败!");
            e.printStackTrace();
        }
        return "home";
    }


    @RequestMapping(value = "/selectUser", produces ={"application/json;charset=UTF-8"})
    public String selectUser(User user, HttpServletRequest request,PageModel pageModel,Model model) {
        try {
            //只需处理get请求的参数
            //if (request.getMethod().toLowerCase().indexOf("get") != -1) {
                if (user != null && !StringUtils.isEmpty(user.getName())) {
                    String name = user.getName();
                    user.setName(name);
                }
            //}
            List<User> users = userService.getUsersByPage(user, pageModel);
            model.addAttribute("users", users);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "identity/user/user";
    }

    @RequestMapping(value = "/deleteUser")
    public String deleteUser(String ids,Model model){
        try{
            userService.deleteUserByUserIds(ids);
            model.addAttribute("tip","删除成功!");
        }catch (Exception e){
            model.addAttribute("tip","删除失败!");
            e.printStackTrace();
        }
        return "forward:/identity/user/selectUser";
    }

    @RequestMapping(value = "/showAddUser")
    public String showAddUser(){
        return "identity/user/addUser";
    }

    @ResponseBody
    @RequestMapping(value = "/isUserVaildAjax")
    public String isUserVaild(String userId){
        try{
            return userService.isUserVaildAjax(userId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/addUser")
    public String addUser(User user,Model model){
        try{
            userService.addUser(user);
            model.addAttribute("tip","添加成功!");
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("tip","添加失败!");
        }
        return "identity/user/addUser";
    }

    @RequestMapping(value = "/updateUser")
    public String updateUser(User user,Model model){
        try{
            userService.updateUser(user);
            model.addAttribute("tip","修改成功!");
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("tip","修改失败!");
        }
        return "identity/user/updateUser";
    }

    @RequestMapping(value = "/activeUser")
    public String activeUser(User user, Model model){
        try{
            userService.activeUser(user);
            model.addAttribute("tip",user.getState()==1?"激活成功!":"冻结成功!");
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("tip",user.getState()==1?"激活失败!":"冻结失败!");
        }
        return "forward:/identity/user/selectUser";
    }

    @RequestMapping(value = "/showPreUser")
    public String showPreUser(String userId,Model model){
        try{
            User user = userService.getUserById(userId);
            model.addAttribute("user",user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "identity/user/preUser";
    }

    @RequestMapping(value = "/showUpdateUser")
    public String showUpdateUser(String userId,Model model){
        try{
            User user = userService.getUserById(userId);
            model.addAttribute("user",user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "identity/user/updateUser";
    }

}
