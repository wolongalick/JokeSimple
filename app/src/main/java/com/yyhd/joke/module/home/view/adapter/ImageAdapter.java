package com.yyhd.joke.module.home.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yyhd.joke.simple.R;
import com.yyhd.joke.bean.DataAllBean;
import com.yyhd.joke.module.home.view.adapter.holder.ImageViewHolder;

import java.util.List;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/8/4
 * 备注:
 */
public class ImageAdapter extends MultiPhotoAdapter<DataAllBean,ImageViewHolder> {

    public ImageAdapter(List<DataAllBean> data) {
        super(data);
    }

    /**
     * 是否有图片
     *
     * @param homeBean
     * @param holder
     * @return
     */
    @Override
    protected boolean isHasPhoto(DataAllBean homeBean, ImageViewHolder holder) {
        return true;
    }

    /**
     * 延迟创建ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ImageViewHolder onDelayCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_joke_image, parent,false),false);
    }

}
