package com.yyhd.joke.module.home.view;

import com.yyhd.joke.bean.DataAllBean;

import java.util.List;

import common.base.MvpView;
import common.bean.ErrorMsg;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/8/4
 * 备注:
 */
public interface IJokeView extends MvpView {



    /**
     * 获取段子集合成功的回调函数
     * @param data
     * @param loadDataOperate
     * @param diffTime
     */
    void onGetJokesSuccess(List<DataAllBean> data, String loadDataOperate, long diffTime);

    /**
     * 获取段子集合失败的回调函数
     * @param errorMsg
     * @param loadDataOperate
     * @param cacheList
     */
    void onGetJokesFail(ErrorMsg errorMsg, String loadDataOperate, long diffTime, List<DataAllBean> cacheList);

}
