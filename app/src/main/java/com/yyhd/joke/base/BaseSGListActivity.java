package com.yyhd.joke.base;

import common.base.MvpPresenter;
import common.base.MvpView;
import common.base.adapter.BasicRecyclerAdapter;
import common.listdata.api2.BaseListActivity2;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/10/22
 * 备注:
 */
public abstract class BaseSGListActivity<Model,Holder extends BaseSGViewHolder, Adapter extends BasicRecyclerAdapter<Model,Holder>
        ,V extends MvpView,P extends MvpPresenter<V>> extends BaseListActivity2<Model,Holder,Adapter,V,P> {

}
