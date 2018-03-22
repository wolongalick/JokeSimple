package com.yyhd.joke.api.response;

import com.yyhd.joke.bean.CommentsBean;
import com.yyhd.joke.db.entity.UserInfo;

import java.util.List;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/12/29
 * 备注:
 */
public class CommentResponse {
    /**
     * freshCommentsNum : 1
     * hotComments : []
     * hotCommentsNum : 0
     * freshComments : [{"id":"4c7415d2-e6d6-4b97-8619-f7dc45a3f677","articleId":"392f924e-2f6c-4543-bc05-fa2cb25506e0","content":"啥事","userId":"3c0ea3a2-39fc-4f10-98e9-350653fd52a8","userDTO":{"id":"3c0ea3a2-39fc-4f10-98e9-350653fd52a8","timeCreated":1514101037000,"timeLastUpdated":1514101037000,"mobile":"15210024486","nickName":"盟友002448","headPic":null,"registerTime":1514101037000,"lastLoginTime":1514362951000,"passphrase":"55538fc20849c98ab2e0de615e2387773dca8b3d","salt":"MjAxNy0xMi0yNCDPws7nMzozNzE1MjEwMDI0NDg2","uuid":"00000000-287b-eff4-ffff-fffff80fe76c"},"replyNum":0,"upVoteNum":0,"downVoteNum":0,"deleted":false,"timeCreated":1514275896000}]
     */

    private int freshCommentsNum;
    private int hotCommentsNum;
    private List<CommentsBean> hotComments;
    private List<CommentsBean> freshComments;

    public int getFreshCommentsNum() {
        return freshCommentsNum;
    }

    public void setFreshCommentsNum(int freshCommentsNum) {
        this.freshCommentsNum = freshCommentsNum;
    }

    public int getHotCommentsNum() {
        return hotCommentsNum;
    }

    public void setHotCommentsNum(int hotCommentsNum) {
        this.hotCommentsNum = hotCommentsNum;
    }

    public List<CommentsBean> getHotComments() {
        return hotComments;
    }

    public void setHotComments(List<CommentsBean> hotComments) {
        this.hotComments = hotComments;
    }

    public List<CommentsBean> getFreshComments() {
        return freshComments;
    }

    public void setFreshComments(List<CommentsBean> freshComments) {
        this.freshComments = freshComments;
    }

    public static class FreshComments {
        /**
         * id : 4c7415d2-e6d6-4b97-8619-f7dc45a3f677
         * articleId : 392f924e-2f6c-4543-bc05-fa2cb25506e0
         * content : 啥事
         * userId : 3c0ea3a2-39fc-4f10-98e9-350653fd52a8
         * userDTO : {"id":"3c0ea3a2-39fc-4f10-98e9-350653fd52a8","timeCreated":1514101037000,"timeLastUpdated":1514101037000,"mobile":"15210024486","nickName":"盟友002448","headPic":null,"registerTime":1514101037000,"lastLoginTime":1514362951000,"passphrase":"55538fc20849c98ab2e0de615e2387773dca8b3d","salt":"MjAxNy0xMi0yNCDPws7nMzozNzE1MjEwMDI0NDg2","uuid":"00000000-287b-eff4-ffff-fffff80fe76c"}
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
        private UserInfo userDTO;
        private int replyNum;
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

        public UserInfo getUserDTO() {
            return userDTO;
        }

        public void setUserDTO(UserInfo userDTO) {
            this.userDTO = userDTO;
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

    }
}
