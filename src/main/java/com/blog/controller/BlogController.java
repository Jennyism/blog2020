package com.blog.controller;

import com.blog.entity.*;
import com.blog.lucene.BlogIndex;
import com.blog.service.*;
import com.blog.util.VerifyUtil;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Jennyism
 * @date 1/4/2020 上午10:50
 */
@Controller
@RequestMapping("/blog")
public class BlogController {
    @Resource
    BlogService blogService;

    @Resource
    CommentService commentService;

    @Resource
    BloggerService bloggerService;

    @Resource
    LinkService linkService;

    @Resource
    BlogTypeService blogTypeService;

    BlogIndex blogIndex = new BlogIndex();

    @RequestMapping("/articles/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Blog blog = blogService.findById(id);
        model.addAttribute("blog", blog);
        blog.setClickHit(blog.getClickHit() + 1);
        blogService.update(blog);
        model.addAttribute("mainPage", "foreground/blog/view.html");
        model.addAttribute("pageTitle", blog.getTitle());

        //关键字
        String keyWord = blog.getKeyWord();
        if (!StringUtils.isEmpty(keyWord)) {
            String[] s = keyWord.split(" ");
            model.addAttribute("keyWords", s);
        }

        //上一篇，下一篇
        model.addAttribute("lastArticle", blogService.getLastBlog(id));
        model.addAttribute("nextArticle", blogService.getNextBlog(id));

        //评论
        List<Comment> comments = commentService.findByIdAndState(1, id);
        System.out.println("t_Date:"+comments);
        model.addAttribute("commentList", comments);

        //博主
        model.addAttribute("blogger",bloggerService.find());
        model.addAttribute("blogTypeCountList",blogTypeService.countList());
        model.addAttribute("blogCountList",blogService.countList());
        model.addAttribute("linkList",linkService.list(new PageBean(1,10)).getList());
        return "index";
    }

    //图片验证码
    @RequestMapping("/createImg")
    public void createImg(HttpSession session,HttpServletResponse response){
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        VerifyUtil randomValidateCode = new VerifyUtil();
        randomValidateCode.getRandcode(session, response);
    }

    //搜索
    @RequestMapping("/q")
    public String q(@RequestParam(value = "q",required = false)String q,
                    @RequestParam(value = "page",required = false)String page,
                    HttpServletRequest request, Model model
                    ) throws Exception {
        if (StringUtils.isEmpty(page)){
            page = "1";
        }
        model.addAttribute("mainPage","foreground/blog/result.html");

        //博主
        model.addAttribute("blogger",bloggerService.find());
        model.addAttribute("blogTypeCountList",blogTypeService.countList());
        model.addAttribute("blogCountList",blogService.countList());
        model.addAttribute("linkList",linkService.list(new PageBean(1,10)).getList());

        List<Blog> blogs = blogIndex.searchBlog(q.trim());
        model.addAttribute("q",q);
        model.addAttribute("pageTitle","搜索关键字"+q+"结果页面");
        model.addAttribute("resultTotal",blogs.size());
        model.addAttribute("blogList",blogs);
        return "index";
    }
}
