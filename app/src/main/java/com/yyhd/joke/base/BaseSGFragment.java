package com.yyhd.joke.base;

import common.base.BaseFragment;
import common.base.MvpPresenter;
import common.base.MvpView;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/8/3
 * 备注:
 */
public abstract class BaseSGFragment<V extends MvpView,P extends MvpPresenter<V>> extends BaseFragment<V,P> {
}
