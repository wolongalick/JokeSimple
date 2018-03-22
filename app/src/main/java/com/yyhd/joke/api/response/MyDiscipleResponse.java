package com.yyhd.joke.api.response;

import java.util.List;

/**
 * 功能:
 * 作者: 赵贺
 * 日期: 2017/10/22
 * 备注:
 */
public class MyDiscipleResponse {


    /**
     * total : 2
     * list : [{"userId":"3c0ea3a2-39fc-4f10-98e9-350653fd52a8","nickName":"盟友002448","mobile":"15210024486","reward":0},{"userId":"c9230ded-6bab-4256-9a12-302b8375cc5e","nickName":"盟友7000","mobile":"13520727000","reward":0}]
     */

    private int total;
    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * userId : 3c0ea3a2-39fc-4f10-98e9-350653fd52a8
         * nickName : 盟友002448
         * mobile : 15210024486
         * reward : 0
         */

        private String userId;
        private String nickName;
        private String mobile;
        private int reward;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getReward() {
            return reward;
        }

        public void setReward(int reward) {
            this.reward = reward;
        }
    }
}
