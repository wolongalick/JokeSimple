package com.yyhd.joke.api.response;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/10/19
 * 备注:
 */
public class BindingWeChatResponse {

    /**
     * id : 3
     * gender : 男
     * openid : oDDW10dx1NO650KxVZdvW9l1GLn8
     * iconurl : http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epxpJdiaIKwXg77WN6aczEHR6P73KX95Cm4YZRu3J2pfrYNkFc2bTk5empqc3x8Pds1oe2np0icictHg/0
     * name : 兴旺
     * uid : oII0mwzp48_d_LtrLsaTNQod-5Mw
     */

    private int id;
    private String gender;
    private String openid;
    private String iconurl;
    private String name;
    private String uid;
    private boolean hasWechat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isHasWechat() {
        return hasWechat;
    }

    public void setHasWechat(boolean hasWechat) {
        this.hasWechat = hasWechat;
    }
}
