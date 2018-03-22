package com.yyhd.joke.api.response;

/**
 * 功能: 打开宝箱的信息响应类
 * 作者: 崔兴旺
 * 日期: 2018/3/13
 * 备注:
 */
public class OpenBoxInfoResponse {
    private long lastOpenedTime;
    private long currentServerTime;

    public OpenBoxInfoResponse() {
    }

    public OpenBoxInfoResponse(long lastOpenedTime, long currentServerTime) {
        this.lastOpenedTime = lastOpenedTime;
        this.currentServerTime = currentServerTime;
    }

    public long getLastOpenedTime() {
        return lastOpenedTime;
    }

    public void setLastOpenedTime(long lastOpenedTime) {
        this.lastOpenedTime = lastOpenedTime;
    }

    public long getCurrentServerTime() {
        return currentServerTime;
    }

    public void setCurrentServerTime(long currentServerTime) {
        this.currentServerTime = currentServerTime;
    }
}
