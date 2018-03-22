package com.yyhd.joke.api.request;

import java.util.List;

/**
 * 功能:
 * 作者: 赵贺
 * 日期: 2017/12/17
 * 备注:
 */
public class LvPiRequest {

     /**
      * uuid : ffffffff-8979-0c2c-0000-00002161b991
      * userId : c7bdf7a0-678c-438b-a926-b72ab9aed769
      * articleId : COMMEND
      * title : 测试标题1
      * duration : 5000
      * systemTime : 1423423423
      * actionType : read
      * upVoteNum : 100
      * downVoteNum : 200
      * commentNum : 300
      * shareNum : 400
      * images : [{"gif":true,"longPic":false,"qiniuUrl":"http://p1.pstatp.com/large/4e93000dd4edfb55987c.gif"}]
      */

     private String category;
     private String uuid;
     private String userId;
     private String articleId;
     private String title;
     private long duration;
     private int upVoteNum;
     private int downVoteNum;
     private int commentNum;
     private int shareNum;
     private String actionType;
     private List<Images> images;
     private long publishTime;
     private long systemTime;

     public String getUuid() {
          return uuid;
     }

     public void setUuid(String uuid) {
          this.uuid = uuid;
     }

     public String getUserId() {
          return userId;
     }

     public void setUserId(String userId) {
          this.userId = userId;
     }

     public String getArticleId() {
          return articleId;
     }

     public void setArticleId(String articleId) {
          this.articleId = articleId;
     }

     public String getTitle() {
          return title;
     }

     public void setTitle(String title) {
          this.title = title;
     }

     public long getDuration() {
          return duration;
     }

     public void setDuration(long duration) {
          this.duration = duration;
     }

     public long getSystemTime() {
          return systemTime;
     }

     public void setSystemTime(long systemTime) {
          this.systemTime = systemTime;
     }

     public String getActionType() {
          return actionType;
     }

     public void setActionType(String actionType) {
          this.actionType = actionType;
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

     public int getCommentNum() {
          return commentNum;
     }

     public void setCommentNum(int commentNum) {
          this.commentNum = commentNum;
     }

     public int getShareNum() {
          return shareNum;
     }

     public void setShareNum(int shareNum) {
          this.shareNum = shareNum;
     }

     public List<Images> getImages() {
          return images;
     }

     public void setImages(List<Images> images) {
          this.images = images;
     }

     public static class Images {
          /**
           * gif : true
           * longPic : false
           * qiniuUrl : http://p1.pstatp.com/large/4e93000dd4edfb55987c.gif
           */

          private boolean gif;
          private boolean longPic;
          private String qiniuUrl;

          public Images() {
          }

          public Images(boolean gif, boolean longPic, String qiniuUrl) {
               this.gif = gif;
               this.longPic = longPic;
               this.qiniuUrl = qiniuUrl;
          }

          public boolean isGif() {
               return gif;
          }

          public void setGif(boolean gif) {
               this.gif = gif;
          }

          public boolean isLongPic() {
               return longPic;
          }

          public void setLongPic(boolean longPic) {
               this.longPic = longPic;
          }

          public String getQiniuUrl() {
               return qiniuUrl;
          }

          public void setQiniuUrl(String qiniuUrl) {
               this.qiniuUrl = qiniuUrl;
          }
     }

     public String getCategory() {
          return category;
     }

     public void setCategory(String category) {
          this.category = category;
     }

     public long getPublishTime() {
          return publishTime;
     }

     public void setPublishTime(long publishTime) {
          this.publishTime = publishTime;
     }
}
