package com.yyhd.joke.api.response;

import com.yyhd.joke.utils.BizContant;

/**
 * 功能: 收入明细响应类
 * 作者: 崔兴旺
 * 日期: 2017/10/22
 * 备注:
 */
public class MyIncomeResponse {

    /**
     * userId : c7bdf7a0-678c-438b-a926-b72ab9aed769
     * category : GOLD
     * type : ReadingReward
     * cashAmount : null
     * goldAmount : 10
     * description : 阅读收益
     * id : 51ff5943-7fe2-4716-988a-da7df03bd71b
     * timeCreated : 1516851982000
     * timeLastUpdated : 1516851982000
     */

    private String userId;
    private String category;
    private String type;
    private double cashAmount;
    private int goldAmount;
    private String description;
    private String id;
    private long timeCreated;
    private long timeLastUpdated;
    private String status= BizContant.IncomeStatus.APPLY_SUCCESS;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(double cashAmount) {
        this.cashAmount = cashAmount;
    }

    public int getGoldAmount() {
        return goldAmount;
    }

    public void setGoldAmount(int goldAmount) {
        this.goldAmount = goldAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public long getTimeLastUpdated() {
        return timeLastUpdated;
    }

    public void setTimeLastUpdated(long timeLastUpdated) {
        this.timeLastUpdated = timeLastUpdated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
