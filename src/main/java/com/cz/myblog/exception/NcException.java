package com.cz.myblog.exception;


public class NcException extends RuntimeException {
    private Integer code;
    private String message;

    public NcException(String message){
        this.message = message;
        this.code = 400;
    }

    public NcException(Integer code,String message){
        this.message = message;
        this.code = code;
    }

    public NcException(Integer code,String message, Throwable throwable){
        super(throwable);
        this.message = message;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
