package com.blog.service.impl;

import com.blog.entity.Blog;
import com.blog.entity.BlogType;
import com.blog.entity.Comment;
import com.blog.entity.PageBean;
import com.blog.mapper.BlogMapper;
import com.blog.mapper.CommentMapper;
import com.blog.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Jennyism
 * @date 4/5/2020 下午10:39
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private BlogMapper blogMapper;

    @Override
    public int add(Comment paramComment) {
        return commentMapper.add(paramComment);
    }

    @Override
    public int update(Comment paramComment) {
        return commentMapper.updateByPrimaryKeySelective(paramComment);
    }

    @Override
    public PageInfo<Comment> list(PageBean pageBean, int state, int blogId) {
        Condition condition = new Condition(Comment.class);
        if (state != -1) {
            condition.createCriteria().andCondition("state = " + state);
        }
        if (blogId != 0) {
            condition.createCriteria().andCondition("blog_id = " + blogId);
        }
        //设置时间从最近排序
        condition.setOrderByClause("comment_date desc");
//        Comment comment = new Comment();
//        if (state != 0) {
//            comment.setState(state);
//        }
//        if (blogId != 0) {
//            Blog blog = new Blog();
//            blog.setId(blogId);
//            comment.setBlog(blog);
//        }
        PageHelper.startPage(pageBean.getPage(), pageBean.getPageSize());

        List<Comment> comments = commentMapper.selectByExample(condition);
        comments.forEach((Comment c) -> {
            c.setBlog(blogMapper.findById(c.getBlogId()));
        });
        PageInfo<Comment> pageInfo = new PageInfo<Comment>(comments);
        return pageInfo;
    }

    @Override
    public List<Comment> findByIdAndState(int state, int blogId) {
        Comment comment = new Comment();
        comment.setBlogId(blogId);
        comment.setState(state);
        return commentMapper.select(comment);
    }

    @Override
    public Long getTotal(Map<String, Object> paramMap) {
        return commentMapper.getTotal(paramMap);
    }

    @Override
    public Integer delete(Integer paramInteger) {
        return commentMapper.deleteByPrimaryKey(paramInteger);
    }
}
