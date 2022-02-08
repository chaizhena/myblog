package com.cz.myblog.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cz.myblog.ResponseResult.Result;
import com.cz.myblog.common.dto.LoginDto;
import com.cz.myblog.entity.User;
import com.cz.myblog.service.UserService;
import com.cz.myblog.util.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@Api(tags = "账号管理模块")
@RequestMapping("/")
public class AccountController {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @ApiOperation("登录接口")
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, loginDto.getUsername());
        User user = userService.getOne(queryWrapper);
        //User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));

        Assert.notNull(user, "用户不存在");
        if(!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return Result.errorMessage("密码错误！");
        }
        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        // 用户可以另一个接口
        return Result.success("登陆成功",MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map()
        );
    }

    /**
     * 退出
     * */
    @ApiOperation("退出接口")
    @RequiresAuthentication //    @RequiresAuthentication说明需要登录之后才能访问的接口
    @GetMapping("/logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.success();
    }

}
