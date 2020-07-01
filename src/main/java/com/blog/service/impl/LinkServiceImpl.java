package com.blog.service.impl;

import com.blog.entity.Comment;
import com.blog.entity.Link;
import com.blog.entity.PageBean;
import com.blog.mapper.LinkMapper;
import com.blog.service.LinkService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Jennyism
 * @date 13/5/2020 上午10:22
 */
@Service
public class LinkServiceImpl implements LinkService {

    @Resource
    LinkMapper linkMapper;

    @Override
    public int add(Link paramLink) {
        return linkMapper.insert(paramLink);
    }

    @Override
    public int update(Link paramLink) {
        return linkMapper.updateByPrimaryKeySelective(paramLink);
    }

    @Override
    public PageInfo<Link> list(PageBean pageBean) {
        Condition condition = new Condition(Link.class);
        //设置时间从最近排序
        condition.setOrderByClause("order_no");
        PageHelper.startPage(pageBean.getPage(), pageBean.getPageSize());
        List<Link> links = linkMapper.selectByExample(condition);
        PageInfo<Link> pageInfo = new PageInfo<Link>(links);
        return pageInfo;
    }

    @Override
    public Long getTotal() {
        return null;
    }

    @Override
    public Integer delete(Integer paramInteger) {
        return linkMapper.deleteByPrimaryKey(paramInteger);
    }
}
