package com.yyhd.joke.base;

import common.base.MvpPresenter;
import common.base.MvpView;
import common.base.adapter.BasicRecyclerAdapter;
import common.listdata.api2.BaseListFragment_v4_2;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/8/3
 * 备注:
 */
public abstract class BaseSGListFragment_v4<Model,Holder extends BaseSGViewHolder, Adapter extends BasicRecyclerAdapter<Model,Holder>,V extends MvpView,P extends MvpPresenter<V>> extends BaseListFragment_v4_2<Model,Holder,Adapter,V,P> {

}
