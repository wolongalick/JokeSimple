package com.yyhd.joke.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/8/5
 * 备注:
 */
@Entity
public class LastWatchJoke {
    @Id
    private String jokeCode;
    private String publicTime;
    private String jokeId;


    @Generated(hash = 1830191458)
    public LastWatchJoke(String jokeCode, String publicTime, String jokeId) {
        this.jokeCode = jokeCode;
        this.publicTime = publicTime;
        this.jokeId = jokeId;
    }
    @Generated(hash = 1412635683)
    public LastWatchJoke() {
    }


    public String getPublicTime() {
        return this.publicTime;
    }
    public void setPublicTime(String publicTime) {
        this.publicTime = publicTime;
    }
    public String getJokeCode() {
        return this.jokeCode;
    }
    public void setJokeCode(String jokeCode) {
        this.jokeCode = jokeCode;
    }
    public String getJokeId() {
        return this.jokeId;
    }
    public void setJokeId(String jokeId) {
        this.jokeId = jokeId;
    }
}
