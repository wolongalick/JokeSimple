package com.yyhd.joke.module.home.view.adapter.holder.photo;

import android.view.View;
import android.widget.ImageView;

import com.yyhd.joke.simple.R;
import com.yyhd.joke.module.home.view.adapter.holder.PhotoViewHolder;

import butterknife.BindView;

/**
 * 纯图片ViewHolder
 */
public class PhotoViewHolder4GifSingle_Detail extends PhotoViewHolder {
    @BindView(R.id.iv_playGif)
    public ImageView ivPlayGif;
    @BindView(R.id.iv_downloadGif)
    public ImageView ivDownloadGif;

    public PhotoViewHolder4GifSingle_Detail(View itemView) {
        super(itemView,false);
    }


}