package com.yyhd.joke.module.home.view.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.yyhd.joke.simple.R;

import butterknife.BindView;

/**
 * 纯文字ViewHolder
 */
public class TextViewHolder extends BaseHomeViewHolder {
    @BindView(R.id.text_content)
    public TextView textContent;
    public TextViewHolder(View inflate, boolean b) {
        super(inflate, b);
    }
}