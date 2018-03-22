package com.yyhd.joke.api.response;

/**
 * 功能:
 * 作者: 赵贺
 * 日期: 2017/10/20
 * 备注:
 */
public class Coupon {
    /**
     * id : 1
     * type : LCOUPON
     * amount : 10
     */

    private String couponType;
    private int couponAmount;


    public String getType() {
        return couponType;
    }

    public void setType(String type) {
        this.couponType = type;
    }

    public int getAmount() {
        return couponAmount;
    }

    public void setAmount(int amount) {
        this.couponAmount = amount;
    }
}
