package com.cz.myblog.service.impl;

import com.cz.myblog.entity.User;
import com.cz.myblog.mapper.UserMapper;
import com.cz.myblog.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
