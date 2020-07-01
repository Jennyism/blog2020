package com.blog.controller;

import com.blog.entity.Blogger;
import com.blog.util.CryptographyUtil;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Jennyism
 * @date 20/4/2020 下午2:45
 */
@Controller
@RequestMapping("/blogger")
public class BloggerController {
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/main")
    public String main(HttpSession session) {
        return "main";
    }

    @PostMapping("/login")
    public String login(Blogger blogger, Model model, HttpSession session) {
        blogger.setPassword(CryptographyUtil.md5(blogger.getPassword(), "java1234"));
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(blogger.getUserName(), blogger.getPassword());
        try {
            subject.login(token);
            session.setAttribute("blogger",subject.getSession().getAttribute("blogger"));
            return "redirect:/blogger/main";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorInfo", "用户名或密码有误！");
            model.addAttribute("blogger", blogger);
        }
        return "login";
    }

}
