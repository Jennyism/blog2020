package com.blog.service.impl;

import com.blog.entity.Blog;
import com.blog.entity.BlogType;
import com.blog.entity.PageBean;
import com.blog.mapper.BlogMapper;
import com.blog.mapper.BlogTypeMapper;
import com.blog.service.BlogTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Jennyism
 * @date 20/4/2020 下午7:18
 */
@Service
public class BlogTypeServiceImpl implements BlogTypeService {

    @Resource
    private BlogTypeMapper typeMapper;

    @Resource
    BlogMapper blogMapper;


    @Override
    public List<BlogType> countList() {
        List<BlogType> blogTypes = typeMapper.selectAll();
        for (BlogType type :
                blogTypes) {
            Blog blog = new Blog();
            blog.setTypeId(type.getId());
            int i = blogMapper.selectCount(blog);
            type.setBlogCount(i);
        }
        return blogTypes;
    }


    @Override
    public PageInfo<BlogType> list(PageBean pageBean) {
        PageHelper.startPage(pageBean.getPage(),pageBean.getPageSize());
        List<BlogType> blogTypes = typeMapper.selectAll();
        PageInfo pageInfo =  new PageInfo<BlogType>(blogTypes);
        return pageInfo;
    }

    @Override
    public Long getTotal(Map<String, Object> paramMap) {
        return null;
    }

    @Override
    public Integer add(BlogType paramBlogType) {
        return typeMapper.insert(paramBlogType);
    }

    @Override
    public Integer update(BlogType paramBlogType) {
//        Example example = new Example(BlogType.class);
////        example.
        return typeMapper.updateByPrimaryKey(paramBlogType);
    }

    @Override
    public Integer delete(Integer paramInteger) {
        return typeMapper.deleteByPrimaryKey(paramInteger);
    }

}
