package com.blog.mapper;

import com.blog.entity.Blog;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

/**
 * @author Jennyism
 * @date 1/4/2020 上午10:35
 */

public interface BlogMapper extends Mapper<Blog> {

    List<Blog> countList();

    List<Blog> list(Map<String, Object> paramMap);

    Long getTotal(Map<String, Object> paramMap);

    Blog findById(Integer paramInteger);

    Blog getLastBlog(Integer paramInteger);

    Blog getNextBlog(Integer paramInteger);

    Integer add(Blog paramBlog);

    Integer getBlogByTypeId(Integer paramInteger);
}
