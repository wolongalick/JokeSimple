package com.yyhd.joke.api.response;

/**
 * 功能: 基础json实体类
 * 作者: 崔兴旺
 * 日期: 2017/8/3
 * 备注:
 */
public class BaseResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private String messageCode;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }
}
