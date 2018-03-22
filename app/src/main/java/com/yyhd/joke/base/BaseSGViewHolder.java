package com.yyhd.joke.base;

import android.view.View;

import butterknife.ButterKnife;
import common.base.viewholder.BaseViewHolder;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/11/11
 * 备注:
 */
public class BaseSGViewHolder extends BaseViewHolder {

    public BaseSGViewHolder(View itemView, boolean isFoot) {
        super(itemView);
        if(!isFoot){
            ButterKnife.bind(this, itemView);
        }
    }


}
