package com.yyhd.joke.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/9/10
 * 备注:
 */
public class DiggWrap implements Serializable{
    private int diggCount;    //顶的数量
    private int buryCount;    //踩的数量
    private int commentCount;   //分享的数量
    private int shareCount;   //分享的数量


    private boolean digged;   //是否已顶
    private boolean buryed;   //是否已踩
    private int adapterPosition;
    private List<CommentsBean> godCommentsBeans;


    public DiggWrap(){

    }

    public DiggWrap(int diggCount, int buryCount, int shareCount, boolean digged, boolean buryed, int adapterPosition) {
        this.diggCount = diggCount;
        this.buryCount = buryCount;
        this.shareCount = shareCount;
        this.digged = digged;
        this.buryed = buryed;
        this.adapterPosition=adapterPosition;
    }

    public int getDiggCount() {
        return diggCount;
    }

    public void setDiggCount(int diggCount) {
        this.diggCount = diggCount;
    }

    public int getBuryCount() {
        return buryCount;
    }

    public void setBuryCount(int buryCount) {
        this.buryCount = buryCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public boolean isDigged() {
        return digged;
    }

    public void setDigged(boolean digged) {
        this.digged = digged;
    }

    public boolean isBuryed() {
        return buryed;
    }

    public void setBuryed(boolean buryed) {
        this.buryed = buryed;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getAdapterPosition() {
        return adapterPosition;
    }

    public void setAdapterPosition(int adapterPosition) {
        this.adapterPosition = adapterPosition;
    }

    public List<CommentsBean> getGodCommentsBeans() {
        return godCommentsBeans;
    }

    public void setGodCommentsBeans(List<CommentsBean> godCommentsBeans) {
        this.godCommentsBeans = godCommentsBeans;
    }
}
