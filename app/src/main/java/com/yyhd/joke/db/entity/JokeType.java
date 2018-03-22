package com.yyhd.joke.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/8/5
 * 备注:
 */
@Entity
public class JokeType {

    /**
     * code : COMMEND
     * desc : 推荐
     * deleted : false
     */

    private String code;
    private String desc;
    private boolean deleted;

    @Generated(hash = 498326882)
    public JokeType(String code, String desc, boolean deleted) {
        this.code = code;
        this.desc = desc;
        this.deleted = deleted;
    }

    @Generated(hash = 243032280)
    public JokeType() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean getDeleted() {
        return this.deleted;
    }
}
