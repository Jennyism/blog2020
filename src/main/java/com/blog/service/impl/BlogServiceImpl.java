package com.blog.service.impl;

import com.blog.entity.Blog;
import com.blog.entity.Comment;
import com.blog.entity.PageBean;
import com.blog.mapper.BlogMapper;
import com.blog.mapper.BlogTypeMapper;
import com.blog.mapper.CommentMapper;
import com.blog.service.BlogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Jennyism
 * @date 1/4/2020 上午10:50
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Resource
    BlogMapper mapper;

    @Resource
    CommentMapper commentMapper;

    @Resource
    BlogTypeMapper typeMapper;

    @Override
    public List<Blog> countList() {
        return mapper.countList();
    }

    @Override
    public PageInfo<Blog> list(PageBean pageBean, String title,Integer typeId,String releaseDateStr) {
        Example example = new Example(Blog.class);
        if (title != null && !title.equals("")) {
            example.createCriteria().andLike("title", "%" + title + "%");
        }
        if (typeId != null && typeId!=0) {
            example.createCriteria().andEqualTo("typeId" ,typeId);
        }
        if (releaseDateStr != null && !releaseDateStr.equals("")) {
            example.and().andCondition("DATE_FORMAT(release_date,'%Y年%m月')=",releaseDateStr);
        }
        example.orderBy("releaseDate").desc();
        PageHelper.startPage(pageBean.getPage(), pageBean.getPageSize());
        List<Blog> blogs = mapper.selectByExample(example);
        PageInfo<Blog> pageInfo = new PageInfo<Blog>(blogs);
        for (Blog b :
                pageInfo.getList()) {
            b.setBlogType(typeMapper.selectByPrimaryKey(b.getTypeId()));
        }
        return pageInfo;
    }

    @Override
    public Long getTotal(Map<String, Object> paramMap) {
        return mapper.getTotal(paramMap);
    }

    @Override
    public Blog findById(Integer paramInteger) {
        Blog byId = mapper.selectByPrimaryKey(paramInteger);
        System.out.println("byBlogId:" + byId);
        byId.setBlogType(typeMapper.selectByPrimaryKey(byId.getTypeId()));
        return byId;
    }

    @Override
    public Integer update(Blog paramBlog) {
        paramBlog.setTypeId(paramBlog.getBlogType().getId());
        return mapper.updateByPrimaryKeySelective(paramBlog);
    }

    @Override
    public Blog getLastBlog(Integer paramInteger) {
        return mapper.getLastBlog(paramInteger);
    }

    @Override
    public Blog getNextBlog(Integer paramInteger) {
        return mapper.getNextBlog(paramInteger);
    }

    @Override
    public Integer add(Blog paramBlog) {
        paramBlog.setTypeId(paramBlog.getBlogType().getId());
        System.out.println(paramBlog);
        return mapper.add(paramBlog);
    }

    @Override
    public Integer delete(Integer paramInteger) {
        Comment comment = new Comment();
        comment.setBlogId(paramInteger);
        commentMapper.delete(comment);
        return mapper.deleteByPrimaryKey(paramInteger);
    }

    @Override
    public Integer getBlogByTypeId(Integer paramInteger) {
        return mapper.getBlogByTypeId(paramInteger);
    }

    @Override
    public List<Blog> selectAll() {
        return mapper.selectAll();
    }
}
