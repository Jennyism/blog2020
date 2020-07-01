package com.blog.config;

import com.blog.entity.Blog;
import com.blog.lucene.BlogIndex;
import com.blog.mapper.BlogMapper;
import com.blog.service.BlogService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.swing.text.html.parser.ParserDelegator;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jennyism
 * @date 24/5/2020 上午10:04
 */
public class LuceneInitRunner {
//
//    @Resource
//    BlogMapper blogMapper;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        System.out.println("*************Init lucene*************");
//        System.out.println("-info::" + args + "::-");
//        BlogIndex blogIndex = new BlogIndex();
//        List<Integer> allBlogId = blogIndex.findAllBlogId();
//        System.out.println("-id::"+allBlogId+"::-");
//        List<Blog> blogs = blogMapper.selectAll();
//        for (Blog blog : blogs){
//            if (allBlogId.contains(blog.getId())){
//                continue;
//            }
//            blog.setContentNoTag(parse(blog.getContent()));
//            blogIndex.addIndex(blog);
//        }
//        System.out.println("*********END Init lucene*************");
//    }
//
//    private String parse(String content){
//        String contentNoTag = content.replaceAll("</?[^>]+>", "").replaceAll("<a>\\s*|\t|\r|\n</a>", "");
//        return contentNoTag;
//    }
}
