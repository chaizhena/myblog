package com.cz.myblog.service.impl;

import com.cz.myblog.entity.Blog;
import com.cz.myblog.mapper.BlogMapper;
import com.cz.myblog.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Author: @achai
 * @since 2021-11-02
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
