package com.yyhd.joke.module.home.view.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yyhd.joke.simple.R;

import butterknife.BindView;

/**
 * 纯图片ViewHolder
 */
public class MultiPhotoViewHolder extends BaseHomeViewHolder {

    @BindView(R.id.rv_image)
    public RecyclerView rvImage;

    public MultiPhotoViewHolder(View inflate, boolean b) {
        super(inflate,b);
    }
}