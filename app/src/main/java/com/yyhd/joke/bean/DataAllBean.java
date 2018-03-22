package com.yyhd.joke.bean;


import com.yyhd.joke.db.entity.UserInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */

public class DataAllBean implements Serializable{

    /*额外属性-begin*/
    private transient int diggCount;    //顶的数量
    private transient int buryCount;    //踩的数量
    private transient int shareCount;    //踩的数量
    private transient boolean digged;   //是否已顶
    private transient boolean buryed;   //是否已踩
    /*额外属性-end*/

    /**
     * id : bda7b19f-c735-4bb7-8ae1-ad81939b6260
     * title : 猝不及防的狗粮
     * content : null
     * categoryCode : ODDPHOTO
     * pictureDetails : [{"originImgurl":"http://p3.pstatp.com/large/4e91000efb149f9e557f.gif","qiniuUrl":"http://assets.yyuehd.com/FmGfrUQy6BTiImVDwBfbOJFFZEG2","firstFrame":"http://assets.yyuehd.com/Fl9wHQP5GRLAXTzcRjFxh22WtIDz","thumbnail":" ","gif":true,"hasThumbnail":false,"longPic":false,"height":221,"width":375,"cutUrl":" "}]
     * userId : null
     * authorId : 162
     * timeCreated : 1513581596000
     * deleted : false
     * commentNum : 1
     * upVoteNum : 0
     * downVoteNum : 0
     * shareNum : 0
     * authorDTO : {"id":"162","timeCreated":null,"timeLastUpdated":null,"nickName":"丑脸谱","headPic":"http://assets.yyuehd.com/162.jpg"}
     * verify : true
     * godComment : [{"id":"90df2fd1-2cac-4836-8efa-b19d168c3725","articleId":"bda7b19f-c735-4bb7-8ae1-ad81939b6260","content":"vvv刚刚","userId":"c7bdf7a0-678c-438b-a926-b72ab9aed769","userVO":{"id":"c7bdf7a0-678c-438b-a926-b72ab9aed769","mobile":"15001181390","nickName":"盟友118139","uuid":"ffffffff-8979-0c2c-0000-00002161b991","headPic":"http://assets.cdlianmeng.com/Fu3BkvnU3awsqBCXrymlAqFMFn7P","registerTime":1513945949000,"lastLoginTime":1515326432000,"cumulativeLoginTimes":1,"activeStar":false,"kingStar":true,"livelyStar":true},"replyNum":0,"upVoteNum":89,"downVoteNum":0,"deleted":false,"timeCreated":1515326623000,"pictureDetails":[]}]
     */

    private String id;
    private String title;
    private String content;
    private String categoryCode;
    private String userId;
    private String authorId;
    private long timeCreated;
    private boolean deleted;
    private int commentNum;
    private int upVoteNum;
    private int downVoteNum;
    private int shareNum;
    private UserInfo authorDTO;
    private boolean verify;
    private List<PictureDetail> pictureDetails;
    private List<CommentsBean> godComment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
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

    public int getShareNum() {
        return shareNum;
    }

    public void setShareNum(int shareNum) {
        this.shareNum = shareNum;
    }

    public UserInfo getAuthorDTO() {
        return authorDTO;
    }

    public void setAuthorDTO(UserInfo userInfo) {
        this.authorDTO = userInfo;
    }

    public boolean isVerify() {
        return verify;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
    }

    public List<PictureDetail> getPictureDetails() {
        return pictureDetails;
    }

    public void setPictureDetails(List<PictureDetail> pictureDetails) {
        this.pictureDetails = pictureDetails;
    }

    public List<CommentsBean> getGodComment() {
        return godComment;
    }

    public void setGodComment(List<CommentsBean> godComment) {
        this.godComment = godComment;
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

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }
}
