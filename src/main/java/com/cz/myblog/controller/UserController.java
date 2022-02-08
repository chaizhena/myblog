package com.cz.myblog.controller;


import com.cz.myblog.entity.User;
import com.cz.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Author: @achai
 * @since 2021-11-02
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public Object test(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @PostMapping("/save")
    public Object testUser(@Validated @RequestBody User user) {
        return user.toString();
    }


}
