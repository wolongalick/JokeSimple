package com.yyhd.joke.bean;

import com.yyhd.joke.db.entity.UserInfo;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/12/29
 * 备注:
 */
public class ReplyBean {

    /**
     * id : af767de6-ab60-4324-b098-ce8c8f52445f
     * articleId : ffaaa797-eeb0-4824-82e7-81977967fad9
     * content : 不不不
     * userId : 70880881-56f1-44a5-8b4d-32057f09edad
     * commentUser : {"id":"70880881-56f1-44a5-8b4d-32057f09edad","timeCreated":1514712295000,"timeLastUpdated":1514712295000,"mobile":"13910741091","nickName":"呵呵哒","headPic":null,"registerTime":1514712295000,"lastLoginTime":1514862823000,"passphrase":"40452726639bef666875c4f8a564b78bc1e9cbdf","salt":"MjAxNy0xMi0zMSDkuIvljYg1OjI0MTM5MTA3NDEwOTE=","uuid":"ffffffff-8979-0c2c-0000-00002161b991"}
     * replyUser : {"id":"c7bdf7a0-678c-438b-a926-b72ab9aed769","timeCreated":1513945949000,"timeLastUpdated":1513945949000,"mobile":"15001181390","nickName":"盟友118139","headPic":"http://assets.yyuehd.com/FqNtPjypPqT3Cww1hU0e0m1Zl3d3","registerTime":1513945949000,"lastLoginTime":1514862863000,"passphrase":"23c05885ff0ebc7743e0fbb198a6405885fb9c70","salt":"MjAxNy0xMi0yMiDPws7nODozMjE1MDAxMTgxMzkw","uuid":"ffffffff-8979-0c2c-0000-00002161b991"}
     * upVoteNum : 0
     * downVoteNum : 0
     * deleted : false
     * timeCreated : 1514862838000
     */

    private String id;
    private String articleId;
    private String content;
    private String userId;
    private UserInfo commentUser;  //回复的发布者
    private UserInfo replyUser;    //被回复者
    private int upVoteNum;
    private int downVoteNum;
    private boolean deleted;
    private long timeCreated;

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

    public UserInfo getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(UserInfo commentUser) {
        this.commentUser = commentUser;
    }

    public UserInfo getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(UserInfo replyUser) {
        this.replyUser = replyUser;
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


}
