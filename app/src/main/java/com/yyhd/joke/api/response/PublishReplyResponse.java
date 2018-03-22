package com.yyhd.joke.api.response;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2018/1/2
 * 备注:
 */
public class PublishReplyResponse {

    /**
     * articleId : ffaaa797-eeb0-4824-82e7-81977967fad9
     * content : 滚滚滚
     * userId : c7bdf7a0-678c-438b-a926-b72ab9aed769
     * pictures :
     * timeCreated : 1514863445504
     * parentCommentId : 155a6245-7c3c-4493-a769-7094bfa50015
     * firstLevelCommentId : 155a6245-7c3c-4493-a769-7094bfa50015
     * upVoteNum : 0
     * downVoteNum : 0
     * deleted : false
     * id : 9aca2df3-b4b8-4f94-822f-880269372d76
     * timeLastUpdated : 1514863445504
     */

    private String articleId;
    private String content;
    private String userId;
    private String pictures;
    private long timeCreated;
    private String parentCommentId;
    private String firstLevelCommentId;
    private int upVoteNum;
    private int downVoteNum;
    private boolean deleted;
    private String id;
    private long timeLastUpdated;

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

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(String parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getFirstLevelCommentId() {
        return firstLevelCommentId;
    }

    public void setFirstLevelCommentId(String firstLevelCommentId) {
        this.firstLevelCommentId = firstLevelCommentId;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimeLastUpdated() {
        return timeLastUpdated;
    }

    public void setTimeLastUpdated(long timeLastUpdated) {
        this.timeLastUpdated = timeLastUpdated;
    }
}
