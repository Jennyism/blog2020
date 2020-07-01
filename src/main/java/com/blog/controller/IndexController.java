package com.blog.controller;

import com.blog.entity.Blog;
import com.blog.entity.Blogger;
import com.blog.entity.PageBean;
import com.blog.service.BlogService;
import com.blog.service.BlogTypeService;
import com.blog.service.BloggerService;
import com.blog.service.LinkService;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @author Jennyism
 * @date 13/5/2020 下午12:54
 */
@Controller
public class IndexController {

    @Resource
    BlogTypeService blogTypeService;

    @Resource
    BlogService blogService;

    @Resource
    LinkService linkService;

    @Resource
    BloggerService bloggerService;

    @GetMapping({"/","/index"})
    public String index(Model model,@RequestParam(value = "page",required = false) Integer page,
    @RequestParam(value = "typeId",required = false) Integer typeId,
    @RequestParam(value = "releaseDateStr",required = false) String releaseDateStr
    ){
        if (page==null || page == 0){
            page = 1;
        }
        PageBean pageBean = new PageBean(page, 10);
        PageInfo<Blog> list = blogService.list(pageBean, null,typeId,releaseDateStr);
        model.addAttribute("blogPage",list);
        model.addAttribute("pageTitle","个人博客系统");
        model.addAttribute("mainPage","foreground/blog/list.html");

        model.addAttribute("blogTypeCountList",blogTypeService.countList());
        model.addAttribute("blogCountList",blogService.countList());
        model.addAttribute("linkList",linkService.list(new PageBean(1,10)).getList());
        model.addAttribute("blogger",bloggerService.find());
        return "index";
    }

    @RequestMapping("/aboutMe")
    public String info(Model model){
        Blogger blogger = bloggerService.find();
        model.addAttribute("blogger",blogger);
        model.addAttribute("mainPage","foreground/blogger/info");
        return "index";
    }
}
