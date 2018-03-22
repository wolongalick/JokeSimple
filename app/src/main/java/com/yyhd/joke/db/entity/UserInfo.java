package com.yyhd.joke.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * 功能: 用户信息表,从登录接口中返回
 * 作者: 崔兴旺
 * 日期: 2017/8/30
 * 备注:
 */
@Entity
public class UserInfo implements Serializable{

    public static final long serialVersionUID=1231232L;

    /**
     * id : c7bdf7a0-678c-438b-a926-b72ab9aed769
     * mobile : 15001181390
     * nickName : 盟友118139
     * uuid : ffffffff-8979-0c2c-0000-00002161b991
     * headPic : http://assets.cdlianmeng.com/Fu3BkvnU3awsqBCXrymlAqFMFn7P
     * registerTime : 1513945949000
     * lastLoginTime : 1515144212000
     * cumulativeLoginTimes : 1
     * activeStar : false
     * kingStar : false
     * livelyStar : false
     */
    @Id
    private String id;
    private String mobile;
    private String nickName;
    private String uuid;
    private String headPic;
    private long registerTime;
    private long lastLoginTime;
    private int cumulativeLoginTimes;
    private boolean activeStar;     //活跃之星
    private boolean livelyStar;     //热门之秀
    private boolean kingStar;       //神评之王
    private String inviteCode;      //我的邀请码
    private String weChat;          //微信号
    private boolean hasMaster;      //是否有师傅
    private boolean hasNewNotify;   //是否有新消息
    private String systemTime;      //这是客户端自定义的时间,用来排序的


    @Generated(hash = 1711175576)
    public UserInfo(String id, String mobile, String nickName, String uuid,
                    String headPic, long registerTime, long lastLoginTime,
                    int cumulativeLoginTimes, boolean activeStar, boolean livelyStar,
                    boolean kingStar, String inviteCode, String weChat, boolean hasMaster,
                    boolean hasNewNotify, String systemTime) {
        this.id = id;
        this.mobile = mobile;
        this.nickName = nickName;
        this.uuid = uuid;
        this.headPic = headPic;
        this.registerTime = registerTime;
        this.lastLoginTime = lastLoginTime;
        this.cumulativeLoginTimes = cumulativeLoginTimes;
        this.activeStar = activeStar;
        this.livelyStar = livelyStar;
        this.kingStar = kingStar;
        this.inviteCode = inviteCode;
        this.weChat = weChat;
        this.hasMaster = hasMaster;
        this.hasNewNotify = hasNewNotify;
        this.systemTime = systemTime;
    }

    @Generated(hash = 1279772520)
    public UserInfo() {
    }










    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getCumulativeLoginTimes() {
        return cumulativeLoginTimes;
    }

    public void setCumulativeLoginTimes(int cumulativeLoginTimes) {
        this.cumulativeLoginTimes = cumulativeLoginTimes;
    }

    public boolean isActiveStar() {
        return activeStar;
    }

    public void setActiveStar(boolean activeStar) {
        this.activeStar = activeStar;
    }

    public boolean isKingStar() {
        return kingStar;
    }

    public void setKingStar(boolean kingStar) {
        this.kingStar = kingStar;
    }

    public boolean isLivelyStar() {
        return livelyStar;
    }

    public void setLivelyStar(boolean livelyStar) {
        this.livelyStar = livelyStar;
    }

    public String getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    public boolean getActiveStar() {
        return this.activeStar;
    }

    public boolean getKingStar() {
        return this.kingStar;
    }

    public boolean getLivelyStar() {
        return this.livelyStar;
    }

    public String getInviteCode() {
        return this.inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public boolean getHasMaster() {
        return this.hasMaster;
    }

    public void setHasMaster(boolean hasMaster) {
        this.hasMaster = hasMaster;
    }

    public String getWeChat() {
        return this.weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public boolean getHasNewNotify() {
        return this.hasNewNotify;
    }

    public void setHasNewNotify(boolean hasNewNotify) {
        this.hasNewNotify = hasNewNotify;
    }
}
