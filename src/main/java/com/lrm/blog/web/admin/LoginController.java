package com.lrm.blog.web.admin;

import com.lrm.blog.po.User;
import com.lrm.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

//add the @Controller annotation
@Controller
@RequestMapping("/admin") //全局映射
public class LoginController {

    //inject Service 注入Service - 要添加@Autowired 啊！！！！！！！
    @Autowired
    private UserService userService;

    //method for returning to the login page
    @GetMapping
    public String loginPage(){
        return "admin/login";

    }

    //After submit in the login page - return the data to db - /admin/login
    //Use POST method to 提交； @RequestParam - 接收参数
    // HttpSession - 把用户名密码放进session - session 作为参数可以传递过来
    // RedirectAttributes - 接收Redirect参数
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        //传入用户名和密码
        User user = userService.checkUser(username,password);
        if (user != null) {
            //不要把密码放进去 - 不安全
            user.setPassword(null);
            //setAttribute - 把user放进session
            session.setAttribute("user", user);
            //返回登录后的首页面 - 登录成功
            return "admin/index";
        }else{
            attributes.addFlashAttribute("message", "Incorrect username or password!");
            return "redirect:/admin";
        }
    }

    //logout method
    // HttpSession - 作为参数要把session清空
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        // After logout, redirect
        return "redirect:/admin";
    }
}
