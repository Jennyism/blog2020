package com.blog.mapper;

import com.blog.entity.Blog;
import com.blog.entity.BlogType;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Jennyism
 * @date 1/4/2020 上午10:35
 */

public interface BlogTypeMapper extends Mapper<BlogType> {

    BlogType findById(Integer paramInteger);
}
