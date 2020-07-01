package com.blog.controller.admin;

import com.blog.entity.Link;
import com.blog.entity.PageBean;
import com.blog.service.BlogService;
import com.blog.service.LinkService;
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
@RequestMapping("/admin/link")
public class LinkAdminController {

    @Resource
    private LinkService linkService;

    @GetMapping("/manage")
    public String manage() {
        return "admin/linkManage";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Map<String, Object> list(@RequestParam(value = "page", required = false) int page,
                                    @RequestParam(value = "rows", required = false) int rows) {
        PageBean pageBean = new PageBean(page, rows);
        PageInfo<Link> info = linkService.list(pageBean);
        Map<String, Object> map = new HashMap<>(2);
        map.put("rows", info.getList());
        map.put("total", info.getTotal());
        return map;
    }

    @PostMapping("/save.do")
    @ResponseBody
    public Map<String, Object> save(Link link) {
        int resultTotal = 0;
        System.out.println(link);
        if (link.getId() == null){
            resultTotal = linkService.add(link);
        }else {
            resultTotal = linkService.update(link);
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
            linkService.delete(Integer.parseInt(idStr[i]));
        }
        msg.put("success", true);
        return msg;
    }
}
