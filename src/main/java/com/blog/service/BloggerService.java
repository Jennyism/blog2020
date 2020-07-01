package com.blog.service;

import com.blog.entity.Blogger;

/**
 * @author Jennyism
 * @date 20/4/2020 下午4:08
 */
public interface BloggerService {
    Blogger getByUserName(String userName);

    Blogger find();

    Integer update(Blogger paramBlogger);
}
