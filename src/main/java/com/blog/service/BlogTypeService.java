package com.blog.service;

import com.blog.entity.BlogType;
import com.blog.entity.PageBean;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface BlogTypeService
{
  public List<BlogType> countList();

  public PageInfo<BlogType> list(PageBean pageBean);

  public Long getTotal(Map<String, Object> paramMap);

  public Integer add(BlogType paramBlogType);

  public Integer update(BlogType paramBlogType);

  public Integer delete(Integer paramInteger);
}



/* Location:           D:\classes\

 * Qualified Name:     com.blog.service.BlogTypeService

 * JD-Core Version:    0.7.0.1

 */