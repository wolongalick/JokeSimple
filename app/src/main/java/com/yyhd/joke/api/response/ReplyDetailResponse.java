package com.yyhd.joke.api.response;

import com.yyhd.joke.bean.ReplyBean;

import java.util.List;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/12/29
 * 备注:
 */
public class ReplyDetailResponse {
    /**
     * hotReplysNum : 0
     * freshReplys : []
     * hotReplys : []
     * freshReplysNum : 0
     */
    private int hotReplysNum;
    private int freshReplysNum;
    private List<ReplyBean> freshReplys;
    private List<ReplyBean> hotReplys;

    public int getHotReplysNum() {
        return hotReplysNum;
    }

    public void setHotReplysNum(int hotReplysNum) {
        this.hotReplysNum = hotReplysNum;
    }

    public int getFreshReplysNum() {
        return freshReplysNum;
    }

    public void setFreshReplysNum(int freshReplysNum) {
        this.freshReplysNum = freshReplysNum;
    }

    public List<ReplyBean> getFreshReplys() {
        return freshReplys;
    }

    public void setFreshReplys(List<ReplyBean> freshReplys) {
        this.freshReplys = freshReplys;
    }

    public List<ReplyBean> getHotReplys() {
        return hotReplys;
    }

    public void setHotReplys(List<ReplyBean> hotReplys) {
        this.hotReplys = hotReplys;
    }
}
