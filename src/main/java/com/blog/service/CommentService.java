package com.blog.service;

import com.blog.entity.BlogType;
import com.blog.entity.Comment;
import com.blog.entity.PageBean;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @author Jennyism
 * @date 4/5/2020 下午10:37
 */
public interface CommentService {
    public int add(Comment paramComment);

    public int update(Comment paramComment);

    public PageInfo<Comment> list(PageBean pageBean, int state,int blogId);

    public List<Comment> findByIdAndState(int state,int blogId);

    public Long getTotal(Map<String, Object> paramMap);

    public Integer delete(Integer paramInteger);
}
