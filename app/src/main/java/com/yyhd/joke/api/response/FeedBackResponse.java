package com.yyhd.joke.api.response;

/**
 * 功能:
 * 作者: 赵贺
 * 日期: 2017/8/30
 * 备注:
 */
public class FeedBackResponse {


    /**
     * id : 69eb8487-069c-40c7-8374-400a2ad023cd
     * timeCreated : 1512979217780
     * timeLastUpdated : 1512979217780
     * content : 圣诞节大姐姐
     * userId :
     * uuid : ffffffff-8979-0c2c-0000-00002161b991
     */

    private String id;
    private long timeCreated;
    private long timeLastUpdated;
    private String content;
    private String userId;
    private String uuid;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
