package com.yyhd.joke.module.home.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yyhd.joke.simple.R;
import com.yyhd.joke.bean.DataAllBean;
import com.yyhd.joke.module.home.view.adapter.holder.TextImageViewHolder;

import java.util.List;

import common.utils.DataUtils;

/**
 * 功能: 图文结合类型的适配器
 * 作者: 崔兴旺
 * 日期: 2017/8/4
 * 备注:
 */
public class TextImageAdapter extends MultiPhotoAdapter<DataAllBean, TextImageViewHolder> {

    public TextImageAdapter(List<DataAllBean> data) {
        super(data);
    }

    /**
     * 延迟创建ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public TextImageViewHolder onDelayCreateViewHolder(ViewGroup parent, int viewType) {
        return new TextImageViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_joke_text_image, parent, false),false);
    }

    /**
     * 通过数据模型填充视图
     *
     * @param holder
     * @param homeBean
     * @param position
     */
    @Override
    public void onFillViewByModel(final TextImageViewHolder holder, final DataAllBean homeBean, final int position) {
        super.onFillViewByModel(holder,homeBean,position);
        holder.textContent.setText(homeBean.getContent());

        if(!holder.textContent.hasOnClickListeners()){
            holder.textContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoJokeDetailActivity(homeBean,position);
                }
            });
        }
    }

    /**
     * 是否有图片
     *
     * @param homeBean
     * @param holder
     * @return
     */
    @Override
    protected boolean isHasPhoto(DataAllBean homeBean, TextImageViewHolder holder) {
        return !DataUtils.isEmpty(homeBean.getPictureDetails());
    }

}
