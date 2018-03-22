package com.yyhd.joke.module.home.view.adapter.holder;

import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yyhd.joke.simple.R;
import com.yyhd.joke.base.BaseSGViewHolder;

import butterknife.BindView;

/**
 * 纯图片ViewHolder
 */
public class PhotoViewHolder extends BaseSGViewHolder {
    @BindView(R.id.iv_image)
    public SimpleDraweeView ivImage;

    public PhotoViewHolder(View itemView,boolean isFoot) {
        super(itemView,isFoot);
    }


}