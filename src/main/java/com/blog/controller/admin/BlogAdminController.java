package com.blog.controller.admin;

import com.blog.entity.Blog;
import com.blog.entity.BlogType;
import com.blog.entity.PageBean;
import com.blog.lucene.BlogIndex;
import com.blog.service.BlogService;
import com.blog.service.BlogTypeService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jennyism
 * @date 26/4/2020 下午3:38
 */
@Controller
@RequestMapping("/admin/blog")
public class BlogAdminController {
    @Resource
    BlogService blogService;

    @Resource
    BlogTypeService blogTypeService;

    BlogIndex blogIndex = new BlogIndex();

    @GetMapping("/write")
    public String write(Model model) {
        List<BlogType> blogTypes = blogTypeService.countList();
        model.addAttribute("blogTypeCountList", blogTypes);
        return "admin/writeBlog";
    }

    @GetMapping("/modify")
    public String modify(Model model) {
        List<BlogType> blogTypes = blogTypeService.countList();
        model.addAttribute("blogTypeCountList", blogTypes);
        return "admin/modifyBlog";
    }

    @GetMapping("/manage")
    public String manage() {
        return "admin/blogManage";
    }

    @PostMapping("/save.do")
    @ResponseBody
    public Map<String, Object> save(Blog blog) throws Exception {
        int resultTotal = 0;
        if (blog.getId() == null) {
            resultTotal = blogService.add(blog);
            blogIndex.addIndex(blog);
        } else {
            resultTotal = blogService.update(blog);
            blogIndex.updateIndex(blog);
        }
        Map<String, Object> map = new HashMap<>(2);
        if (resultTotal > 0) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Map<String, Object> list(@RequestParam(value = "page", required = false) int page,
                                    @RequestParam(value = "rows", required = false) int rows, Blog blog) {
        PageBean pageBean = new PageBean(page, rows);
        PageInfo<Blog> info = blogService.list(pageBean, blog.getTitle(),null,null);
        Map<String, Object> map = new HashMap<>(2);
        map.put("rows", info.getList());
        map.put("total", info.getTotal());
        return map;
    }

    @RequestMapping("/findById.do")
    @ResponseBody
    public Blog findById(@RequestParam int id) {
        Blog blog = blogService.findById(id);
        return blog;
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    public Map<String, Object> delete(@RequestParam String ids) throws Exception {
        Map<String, Object> msg = new HashMap<>(1);
        String[] split = ids.split(",");
        for (int i = 0; i < split.length; i++) {
            blogService.delete(Integer.parseInt(split[i]));
            blogIndex.deleteIndex(ids);
        }
        msg.put("success", true);
        return msg;
    }

    @RequestMapping("/initSearch.do")
    @ResponseBody
    public Map<String,Object> initSearch() throws Exception{
        Map<String, Object> msg = new HashMap<>(1);

        List<Blog> blogs = blogIndex.searchBlog("");
        blogs = blogService.selectAll();
        if (blogs.size() != 0){
            String luncenePath = blogIndex.getLuncenePath();
            File file = new File(luncenePath);
            for (File f :
                    file.listFiles()) {
                file.delete();
            }
        }
        for (Blog b :
                blogs) {
            b.setContentNoTag(getNoTag(b.getContent()));
            blogIndex.addIndex(b);
        }

        msg.put("success", true);
        return msg;
    }

    private String getNoTag(String str){
        //如果有双引号将其先转成单引号
        String htmlStr = str.replaceAll("\"", "'");
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签
        return htmlStr;
    }
}
