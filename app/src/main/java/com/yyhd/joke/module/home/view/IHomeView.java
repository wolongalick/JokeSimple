package com.yyhd.joke.module.home.view;

import com.yyhd.joke.db.entity.JokeType;

import java.util.List;

import common.base.MvpView;
import common.bean.ErrorMsg;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/8/4
 * 备注:
 */
public interface IHomeView extends MvpView {
    /**
     * 当获取段子标签成功的回调函数
     * @param jokeTypes
     */
    void onGetJokeTabsSuccess(List<JokeType> jokeTypes);

    /**
     * 当获取段子标签失败的回调函数
     * @param errorMsg
     */
    void onGetJokeTabsFail(ErrorMsg errorMsg);




}
