package com.liang.controller;

import com.liang.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.nio.channels.AcceptPendingException;

@Controller
public class LoginController {
    @Autowired
    AccountDao accountDao;
    //登录
    @RequestMapping("/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model,
                        HttpSession session){
        //具体的业务：判断账号密码
        /*
        暂时用username：admin
              password:123456
        进行测试
         */
        if(accountDao.check(username, password)){
            session.setAttribute("loginUser",username);
            return "redirect:/main.html";
        }
        else{
            //告诉用户登录失败
            model.addAttribute("msg","用户名或者密码错误！");
            return "index";
        }

    }
    //注销
    @RequestMapping("/user/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/index.html";

    }

}
