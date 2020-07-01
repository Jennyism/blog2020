package com.blog.controller;

import com.blog.entity.Comment;
import com.blog.service.CommentService;
import com.blog.util.VerifyUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jennyism
 * @date 19/5/2020 上午11:18
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Resource
    CommentService commentService;

    @RequestMapping("save.do")
    @ResponseBody
    public Map<String, Object> save(Comment comment, String imageCode, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>(2);
        String sRand = (String) request.getSession().getAttribute(VerifyUtil.RANDOMCODEKEY);
        if (sRand != null && sRand.equalsIgnoreCase(imageCode)){
            System.out.println("comment:::"+comment);
            comment.setUserIp(request.getRemoteAddr());
            int add = commentService.add(comment);
            if(add>0){
                map.put("success",true);
            }else {
                map.put("success",false);
                map.put("errorInfo","数据库存入异常");
            }
        }else {
            map.put("success",false);
            map.put("errorInfo","验证码填写错误！");
        }
        return map;
    }
}
