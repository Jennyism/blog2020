package com.blog;

import com.blog.controller.BlogController;
import com.blog.entity.Blog;
import com.blog.lucene.BlogIndex;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.List;

/**
 * @author Jennyism
 * @date 20/5/2020 上午10:09
 */

public class TestLunce {
    @Test
    public void t1(){
        BlogIndex blogIndex = new BlogIndex();
        try {
            List<Blog> blogs = blogIndex.searchBlog(null);
            if (blogs.size() == 0){
                System.out.println("查询所有");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        URL lucenePath = TestLunce.class.getClassLoader().getResource("lucene");
//        if (lucenePath == null){
//            System.out.println("空");
//            URL resource = BlogController.class.getClassLoader().getResource("");
//            System.out.println(resource);
//        }else {
//            System.out.println(lucenePath);
//
//        }
    }
}
