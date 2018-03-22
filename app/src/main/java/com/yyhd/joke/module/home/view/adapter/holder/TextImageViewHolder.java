package com.yyhd.joke.module.home.view.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.yyhd.joke.simple.R;

import butterknife.BindView;

/**
 * 图文结合ViewHolder
 */
public class TextImageViewHolder extends MultiPhotoViewHolder {
    @BindView(R.id.text_content)
    public TextView textContent;

    public TextImageViewHolder(View inflate, boolean b) {
        super(inflate, b);
    }
}