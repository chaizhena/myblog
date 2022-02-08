package com.cz.myblog.exception;

import com.cz.myblog.ResponseResult.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@ControllerAdvice
@RestController
public class BasicExceptionAdvice {

    //捕获自定义异常
    @ExceptionHandler(NcException.class)
    public Result ncException(NcException exception){
        exception.printStackTrace();
        return Result.errorCodeMessage(exception.getCode(),exception.getMessage());
    }


    //捕获其他异常
    @ExceptionHandler(Exception.class)
    public Result exception(Exception e) {
        e.printStackTrace();
        return Result.errorCodeMessage(404, "请联系管理员");
    }
    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public Result handle401(ShiroException e) {
        log.error("捕捉shiro的异常:-------------->{}",e.getMessage());
        return Result.errorCodeMessage(403, "抱歉，您没有权限操作");
    }
    /**
     * 处理Assert的异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e) throws IOException {
        log.error("Assert异常:-------------->{}",e.getMessage());
        return Result.errorMessage(e.getMessage());
    }
    /**
     * @Validated 校验错误异常处理
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e) throws IOException {
        log.error("运行时异常:-------------->",e);
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return Result.errorMessage(objectError.getDefaultMessage());
    }


}
