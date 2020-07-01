package com.blog.service.impl;

import com.blog.entity.Blogger;
import com.blog.mapper.BloggerMapper;
import com.blog.service.BloggerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jennyism
 * @date 20/4/2020 下午4:09
 */
@Service
public class BloggerServiceImpl implements BloggerService {
    @Resource
    private BloggerMapper bloggerMapper;

    @Override
    public Blogger getByUserName(String userName) {
        Blogger bloggerTemplate = new Blogger();
        bloggerTemplate.setUserName(userName);
        List<Blogger> select = bloggerMapper.select(bloggerTemplate);
        if (select.size()!=0){
            return select.get(0);
        }
        return null;
    }

    @Override
    public Blogger find() {
        List<Blogger> bloggers = bloggerMapper.selectAll();
        if (bloggers.size() > 0){
            return bloggers.get(0);
        }
        return null;
    }

    @Override
    public Integer update(Blogger paramBlogger) {
        return bloggerMapper.updateByPrimaryKeySelective(paramBlogger);
    }
}
