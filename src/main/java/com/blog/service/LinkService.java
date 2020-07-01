package com.blog.service;

import com.blog.entity.Link;
import com.blog.entity.PageBean;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface LinkService
{
  int add(Link paramLink);

  int update(Link paramLink);

  PageInfo<Link> list(PageBean pageBean);

  Long getTotal();

  Integer delete(Integer paramInteger);
}