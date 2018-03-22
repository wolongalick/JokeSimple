package com.yyhd.joke.api.response;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/9/4
 * 备注:
 */
public class PublishCommentResponse {

    /**
     * id : 691f0255-e447-4c4e-94da-852c119b610e
     * timeCreated : 1512721594701
     * timeLastUpdated : 1512721594701
     * articleId : 803f507c-73eb-4956-b617-841d0f1f3bbd
     * content : 哈哈哈
     * userId : null
     */

    private String id;
    private long timeCreated;
    private long timeLastUpdated;
    private String articleId;
    private String content;
    private String userId;

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
}
