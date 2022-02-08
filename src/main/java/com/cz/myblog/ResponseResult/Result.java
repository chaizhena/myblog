package com.cz.myblog.ResponseResult;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private Boolean flag;

    private Integer code;

    private String message;

    private T data;

    public Result() {
    }

    private Result(Boolean success, Integer code) {
        this.flag = success;
        this.code = code;
    }

    private Result(Boolean success, Integer code, T data) {
        this.flag = success;
        this.code = code;
        this.data = data;
    }

    private Result(Boolean success, Integer code, String message, T data) {
        this.flag = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private Result(Boolean success, Integer code, String message) {
        this.flag = success;
        this.code = code;
        this.message = message;
    }


    public static <T> Result<T> success() {
        return new Result<T>(true, 200,"成功");
    }

    public static <T> Result<T> successMessage(String message) {
        return new Result<T>(true, 200, message);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(true, 200,"成功",data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<T>(true, 200, message, data);
    }

    public static <T> Result<T> error() {
        return new Result<T>(false, 400, "失败");
    }

    public static <T> Result<T> errorMessage(String errorMessage) {
        return new Result<T>(false, 404, errorMessage);
    }

    public static <T> Result<T> errorCodeMessage(Integer errorCode, String errorMessage) {
        return new Result<T>(false, errorCode, errorMessage);
    }



}