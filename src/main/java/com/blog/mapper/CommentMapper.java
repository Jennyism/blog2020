package com.blog.mapper;

import com.blog.entity.Blog;
import com.blog.entity.Comment;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author Jennyism
 * @date 1/4/2020 上午10:35
 */

public interface CommentMapper extends Mapper<Comment> {
    public int add(Comment paramComment);

    public int update(Comment paramComment);

    public Long getTotal(Map<String, Object> paramMap);
}
