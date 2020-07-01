package com.blog.service;

import com.blog.entity.Blog;
import com.blog.entity.PageBean;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @author Jennyism
 * @date 1/4/2020 上午10:38
 */
public interface BlogService {
    public abstract List<Blog> countList();

    public PageInfo<Blog> list(PageBean pageBean, String title,Integer typeId,String releaseDateStr);

    public abstract Long getTotal(Map<String, Object> paramMap);

    public abstract Blog findById(Integer paramInteger);

    public abstract Integer update(Blog paramBlog);

    public abstract Blog getLastBlog(Integer paramInteger);

    public abstract Blog getNextBlog(Integer paramInteger);

    public abstract Integer add(Blog paramBlog);

    public abstract Integer delete(Integer paramInteger);

    public abstract Integer getBlogByTypeId(Integer paramInteger);

    List<Blog> selectAll();
}
