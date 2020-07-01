package com.blog.controller.admin;

import com.baidu.ueditor.ActionEnter;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author Jennyism
 * @date 26/4/2020 下午4:06
 */
@Controller
public class UeditorController {

    @RequestMapping("/admin/blog/ueditor")
    public void uEditor(HttpServletRequest request, HttpServletResponse response){
        try {
            request.setCharacterEncoding( "gbk" );
            response.setHeader("Content-Type" , "text/html");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String rootPath = request.getSession().getServletContext().getRealPath( "/" );

        try {
            response.getWriter().write( new ActionEnter( request, rootPath ).exec() );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
