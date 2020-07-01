package com.blog;

import com.blog.entity.*;
import com.blog.mapper.*;
import com.blog.service.BlogService;
import com.blog.service.BlogTypeService;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tk.mybatis.spring.annotation.MapperScan;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@MapperScan(basePackages = "com.blog.mapper")
class BlogApplicationTests {

    @Resource
    BlogService service;

    @Test
    void contextLoads() {
        PageBean pageBean = new PageBean(1,5);
        PageInfo<Blog> blogs = service.list(pageBean, null,null,null);
        System.out.println("======================");
        System.out.println(blogs);
        for (Blog b :
                blogs.getList()) {
            System.out.println(b);
            System.out.println("==================");
        }
    }

}
