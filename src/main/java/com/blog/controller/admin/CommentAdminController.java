package com.blog.controller.admin;

import com.blog.entity.BlogType;
import com.blog.entity.Comment;
import com.blog.entity.PageBean;
import com.blog.service.CommentService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jennyism
 * @date 4/5/2020 下午10:44
 */
@Controller
@RequestMapping("/admin/comment")
public class CommentAdminController {
    @Resource
    private CommentService commentService;

    @GetMapping("/manage")
    public String manage() {
        return "admin/commentManage";
    }

    @GetMapping("/review")
    public String review() {
        return "admin/commentReview";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Map<String, Object> list(@RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "rows", required = false) Integer rows,
                                    @RequestParam(value = "state", required = false) Integer state) {
        PageBean pageBean = new PageBean(page, rows);
        int stateInt;
        if (state == null) {
            stateInt = -1;
        } else {
            stateInt = state.intValue();
        }
        PageInfo<Comment> info = commentService.list(pageBean, stateInt, 0);
        Map<String, Object> map = new HashMap<>(2);
        map.put("rows", info.getList());
        map.put("total", info.getTotal());
        return map;
    }

    @PostMapping("/delete.do")
    @ResponseBody
    public Map<String, Object> delete(@RequestParam("ids") String ids) {
        Map<String, Object> msg = new HashMap<>(1);
        String[] idStr = ids.split(",");
        for (int i = 0; i < idStr.length; i++) {
            int id = Integer.parseInt(idStr[i]);
            Integer delete = commentService.delete(Integer.parseInt(idStr[i]));
            System.out.println(delete);
        }
        msg.put("success", true);
        return msg;
    }

    @PostMapping("/review.do")
    @ResponseBody
    public Map<String, Object> review(@RequestParam("ids") String ids,
                                      @RequestParam(value = "state", required = false) Integer state) {
        Map<String, Object> msg = new HashMap<>(1);
        String[] idStr = ids.split(",");
        for (int i = 0; i < idStr.length; i++) {
            int id = Integer.parseInt(idStr[i]);
            Comment comment = new Comment();
            comment.setId(id);
            comment.setState(state);
            Integer delete = commentService.update(comment);
        }
        msg.put("success", true);
        return msg;

    }
}
