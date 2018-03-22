package com.yyhd.joke.module.home.model;

import com.yyhd.joke.db.entity.LastWatchJoke;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/8/5
 * 备注:
 */
public interface IJokeModel {
    LastWatchJoke queryLastWatchJoke(String jokeCode);

    void saveLastWatchJoke(LastWatchJoke lastWatchJoke);
}
