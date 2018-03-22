package com.yyhd.joke.module.home.view.adapter.holder.photo;

import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yyhd.joke.simple.R;
import com.yyhd.joke.module.home.view.adapter.holder.PhotoViewHolder;

import butterknife.BindView;

/**
 * 纯图片ViewHolder
 */
public class PhotoViewHolder4Normal extends PhotoViewHolder {
    @BindView(R.id.iv_image)
    public SimpleDraweeView ivImage;

    public PhotoViewHolder4Normal(View itemView) {
        super(itemView,false);
    }


}