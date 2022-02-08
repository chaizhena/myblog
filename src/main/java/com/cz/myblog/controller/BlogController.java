package com.cz.myblog.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cz.myblog.ResponseResult.Result;
import com.cz.myblog.entity.Blog;
import com.cz.myblog.exception.NcException;
import com.cz.myblog.service.BlogService;
import com.cz.myblog.util.ShiroUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Author: @achai
 * @since 2021-11-02
 */
@RestController
@RequestMapping("/")
@Api(tags = "博客管理模块")
@Slf4j
public class BlogController {
    @Autowired
    BlogService blogService;

    /**
     * 分页查询
     * @param currentPage 当前页
     * @return 查询结果
     */
    @ApiOperation("分页接口")
    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){
        //if (currentPage==null || currentPage<1) currentPage=1;
        Page page = new Page(currentPage,5);

        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete", 0);
        queryWrapper.orderByDesc("created");

        IPage pageDate= blogService.page(page,queryWrapper);
        return Result.success(pageDate);
    }

    /**
     * 查询文章接口
     * */
    @ApiOperation("查询文章接口")
    @GetMapping({"/blog/{id}"})
    public Result detail(@PathVariable(name = "id")Long id){
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已删除！");
        if (blog.getIsDelete() == 1) {
            throw new NcException("该博客已被删除");
        }
        return Result.success(blog);
    }

    /**
     *  编辑和添加是同一体的
     * @RequiresAuthentication 需要认证权限才能编辑
     * @Validated 校验
     * @RequestBody 从请求体里面获取参数过来
     * @RequestBody Blog blog  如果blog传过来有id则为可编辑；如果没有 则为可添加
     * */
    @ApiOperation("编辑和添加接口")
    @RequiresAuthentication //    @RequiresAuthentication说明需要登录之后才能访问的接口
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog){

        log.info("添加博客内容",blog);
        Blog temp=null;
        //编辑状态
        if (blog.getId()!=null){
            temp=blogService.getById(blog.getId());  //从数据库里查出这篇文章，然后进行更新  赋给temp这个对象
            // 只能编辑自己的文章
            System.out.println(ShiroUtil.getProfile().getId());
            Assert.isTrue(temp.getUserId()== ShiroUtil.getProfile().getId(),"没有编辑权限");
            temp.setUpdateTime(LocalDateTime.now());
        }else {
            temp=new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
            temp.setIsDelete(0);
        }
        // 将 blog里的title、description、content三个字段赋到新的对象temp里面 进行保存编辑   剩下的忽略
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status","updateTime","isDelete");
        blogService.saveOrUpdate(temp);
        return Result.success("操作测功");
    }

    @ApiOperation(value = "根据博客id删除博客", notes = "author：aChai")
//    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping
    public Result deleteBlog(Long id) {
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Blog::getId, id);
        Blog blog = blogService.getOne(queryWrapper);
        Assert.notNull(blog, "该博客不存在！");
        blog.setIsDelete(1);
        boolean flag = blogService.saveOrUpdate(blog);

//        if (count > 0) {
//            return Result.succ();
//        } else if (count == -1) {
//            return Result.fail("error：权限不够");
//        } else {
//            return Result.fail("修改失败");
//        }
        if (flag) {
            return Result.successMessage("删除成功");
        }
        return Result.errorMessage("删除失败");

    }



}
