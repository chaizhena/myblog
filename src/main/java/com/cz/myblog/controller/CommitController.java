package com.cz.myblog.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cz.myblog.ResponseResult.Result;
import com.cz.myblog.entity.Commit;
import com.cz.myblog.service.CommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Author: @achai
 * @since 2022-01-24
 */
@RestController
@RequestMapping("/")
public class CommitController {
    @Autowired
    CommitService commitService;

    @PostMapping("/commit")
    public Result saveCommit(@RequestBody Commit commit) {
        commit.setCreateTime(LocalDateTime.now());
        boolean result = commitService.save(commit);
        if (result) {
            return Result.success("发布成功");
        }
        return Result.errorMessage("发布失败");
    }

    @GetMapping("/getCommit")
    public Result getCommit() {
        LambdaQueryWrapper<Commit> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Commit::getCreateTime);
        List<Commit> commits = commitService.list(queryWrapper);
        return Result.success(commits);

    }

}
