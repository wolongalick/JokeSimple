package com.yyhd.joke.bean;

import com.yyhd.joke.db.entity.UserInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/12/29
 * 备注:
 */
public class CommentsBean implements Serializable{

    /**
     * id : 4c7415d2-e6d6-4b97-8619-f7dc45a3f677
     * articleId : 392f924e-2f6c-4543-bc05-fa2cb25506e0
     * content : 啥事
     * userId : 3c0ea3a2-39fc-4f10-98e9-350653fd52a8
     * userVO : {"id":"3c0ea3a2-39fc-4f10-98e9-350653fd52a8","timeCreated":1514101037000,"timeLastUpdated":1514101037000,"mobile":"15210024486","nickName":"盟友002448","headPic":null,"registerTime":1514101037000,"lastLoginTime":1514362951000,"passphrase":"55538fc20849c98ab2e0de615e2387773dca8b3d","salt":"MjAxNy0xMi0yNCDPws7nMzozNzE1MjEwMDI0NDg2","uuid":"00000000-287b-eff4-ffff-fffff80fe76c"}
     * replyNum : 0
     * upVoteNum : 0
     * downVoteNum : 0
     * deleted : false
     * timeCreated : 1514275896000
     */

    private String id;
    private String articleId;
    private String content;
    private String userId;
    private UserInfo userVO;
    private int replyNum;
    private int upVoteNum;
    private int downVoteNum;
    private boolean deleted;
    private long timeCreated;
    private boolean godComment; //是否为神评

    private boolean isDigged;//额外字段:是否被当前用户点赞了

    private List<PictureDetail> pictureDetails;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserInfo getUserVO() {
        return userVO;
    }

    public void setUserVO(UserInfo userVO) {
        this.userVO = userVO;
    }

    public int getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }

    public int getUpVoteNum() {
        return upVoteNum;
    }

    public void setUpVoteNum(int upVoteNum) {
        this.upVoteNum = upVoteNum;
    }

    public int getDownVoteNum() {
        return downVoteNum;
    }

    public void setDownVoteNum(int downVoteNum) {
        this.downVoteNum = downVoteNum;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public boolean isGodComment() {
        return godComment;
    }

    public void setGodComment(boolean godComment) {
        this.godComment = godComment;
    }

    public List<PictureDetail> getPictureDetails() {
        return pictureDetails;
    }

    public void setPictureDetails(List<PictureDetail> pictureDetails) {
        this.pictureDetails = pictureDetails;
    }

    public boolean isDigged() {
        return isDigged;
    }

    public void setDigged(boolean digged) {
        isDigged = digged;
    }
}
