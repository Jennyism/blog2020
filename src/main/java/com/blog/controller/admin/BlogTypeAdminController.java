package com.blog.controller.admin;

import com.blog.entity.Blog;
import com.blog.entity.BlogType;
import com.blog.entity.PageBean;
import com.blog.service.BlogService;
import com.blog.service.BlogTypeService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jennyism
 * @date 20/4/2020 下午9:28
 */
@Controller
@RequestMapping("/admin/blogType")
public class BlogTypeAdminController {

    @Resource
    private BlogTypeService blogTypeService;

    @Resource
    private BlogService blogService;

    @GetMapping("/manage")
    public String manage() {
        return "admin/blogTypeManage";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Map<String, Object> list(@RequestParam(value = "page", required = false) int page,
                                    @RequestParam(value = "rows", required = false) int rows) {
        PageBean pageBean = new PageBean(page, rows);
        PageInfo<BlogType> info = blogTypeService.list(pageBean);
        Map<String, Object> map = new HashMap<>(2);
        map.put("rows", info.getList());
        map.put("total", info.getTotal());
        return map;
    }

    @PostMapping("/save.do")
    @ResponseBody
    public Map<String, Object> save(BlogType blogType) {
        int resultTotal = 0;
        System.out.println(blogType);
        if (blogType.getId() == null){
            resultTotal = blogTypeService.add(blogType);
        }else {
            resultTotal = blogTypeService.update(blogType);
        }
        Map<String, Object> map = new HashMap<>(2);
        if (resultTotal > 0){
            map.put("success",Boolean.valueOf(true));
        }else {
            map.put("success",Boolean.valueOf(false));
        }
        return map;
    }

    @PostMapping("/delete.do")
    @ResponseBody
    public Map<String, Object> delete(@RequestParam("ids") String ids) {
        Map<String,Object> msg = new HashMap<>(1);
        String[] idStr = ids.split(",");
        for (int i = 0; i < idStr.length; i++) {
            int id = Integer.parseInt(idStr[i]);
            if (blogService.getBlogByTypeId(id) > 0 ){
                if (msg.containsKey("exist")){
                    msg.put("exist",msg.get("exist")+";博客序号："+id+"下有博客，不能删除");
                }else {
                    msg.put("exist","博客序号："+id+"下有博客，不能删除");
                }
                continue;
            }
            blogTypeService.delete(Integer.parseInt(idStr[i]));
        }
        msg.put("success", true);
        return msg;
    }
}
