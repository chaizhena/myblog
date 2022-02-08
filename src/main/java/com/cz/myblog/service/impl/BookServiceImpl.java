package com.cz.myblog.service.impl;

import com.cz.myblog.entity.Book;
import com.cz.myblog.mapper.BookMapper;
import com.cz.myblog.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Author: @achai
 * @since 2022-01-06
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

}
