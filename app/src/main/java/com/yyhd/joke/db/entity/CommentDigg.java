package com.yyhd.joke.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/9/10
 * 备注:
 */
@Entity
public class CommentDigg {
    @Id
    private String id;
    private int diggCount;
    private boolean isDigged;
    private long commentPublicTime;
    @Generated(hash = 1619312032)
    public CommentDigg(String id, int diggCount, boolean isDigged,
                       long commentPublicTime) {
        this.id = id;
        this.diggCount = diggCount;
        this.isDigged = isDigged;
        this.commentPublicTime = commentPublicTime;
    }
    @Generated(hash = 1164033351)
    public CommentDigg() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getDiggCount() {
        return this.diggCount;
    }
    public void setDiggCount(int diggCount) {
        this.diggCount = diggCount;
    }
    public boolean getIsDigged() {
        return this.isDigged;
    }
    public void setIsDigged(boolean isDigged) {
        this.isDigged = isDigged;
    }
    public long getCommentPublicTime() {
        return this.commentPublicTime;
    }
    public void setCommentPublicTime(long commentPublicTime) {
        this.commentPublicTime = commentPublicTime;
    }




}
