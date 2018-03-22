package com.yyhd.joke.module.home.view.adapter.holder.photo;

import android.view.View;
import android.widget.ImageView;

import com.yyhd.joke.simple.R;
import com.yyhd.joke.module.home.view.adapter.holder.PhotoViewHolder;

import butterknife.BindView;
import pl.droidsonroids.gif.GifImageView;

/**
 * 纯图片ViewHolder
 */
public class PhotoViewHolder4GifSingle extends PhotoViewHolder {
    @BindView(R.id.iv_playGif)
    public ImageView ivPlayGif;
    @BindView(R.id.gifImageView)
    public GifImageView gifImageView;

    public PhotoViewHolder4GifSingle(View itemView) {
        super(itemView,false);
    }


}