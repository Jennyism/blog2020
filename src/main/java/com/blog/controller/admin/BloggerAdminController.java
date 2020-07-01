package com.blog.controller.admin;

import com.blog.entity.Blog;
import com.blog.entity.Blogger;
import com.blog.service.BloggerService;
import com.blog.util.CryptographyUtil;
import com.blog.util.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jennyism
 * @date 6/5/2020 上午9:33
 */
@Controller
@RequestMapping("/admin/blogger")
public class BloggerAdminController {

    @Resource
    BloggerService bloggerService;

    @GetMapping("/modify")
    public String modifyInfo() {
        return "admin/modifyInfo";
    }

    @RequestMapping({"/save.do"})
    @ResponseBody
    public String save(@RequestParam("imageFile") MultipartFile imageFile, Blogger blogger, HttpServletRequest request)
            throws Exception {
        if (!imageFile.isEmpty()) {
            String filePath = request.getServletContext().getRealPath("/");
            String imageName = DateUtil.getCurrentDateStr() + "." + imageFile.getOriginalFilename().split("\\.")[1];
            File file = new File(filePath + "static/userImages");
            if (!file.exists()){
                file.mkdirs();
            }
            imageFile.transferTo(new File(filePath + "static/userImages/" + imageName));
            blogger.setImageName(imageName);
        }
        int resultTotal = bloggerService.update(blogger).intValue();
        StringBuffer result = new StringBuffer();
        if (resultTotal > 0) {
            result.append("<script language='javascript'>alert('修改成功！');</script>");
            request.getSession().setAttribute("blogger",bloggerService.getByUserName(blogger.getUserName()));
        } else {
            result.append("<script language='javascript'>alert('修改失败！');</script>");
        }
        return result.toString();
    }

    @RequestMapping("/find.do")
    @ResponseBody
    public Blogger find(HttpSession session){
        Blogger blogger = (Blogger) session.getAttribute("blogger");
        return blogger;
    }

    @RequestMapping({"/modifyPassword.do"})
    @ResponseBody
    public Map<String,Object> modifyPassword(String newPassword,HttpSession session){
        Map<String,Object> map = new HashMap<>(1);
        Blogger blogger = (Blogger) session.getAttribute("blogger");
        blogger.setPassword(CryptographyUtil.md5(newPassword, "java1234"));
        int resultTotal = this.bloggerService.update(blogger).intValue();
        if (resultTotal > 0) {
            map.put("success",true);
        }else {
            map.put("success",false);
        }
        return map;
    }

    @RequestMapping({"/logout.do"})
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "redirect:login";
    }
}
