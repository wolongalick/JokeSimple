package com.yyhd.joke.module.home.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yyhd.joke.bean.DataAllBean;
import com.yyhd.joke.module.home.view.adapter.holder.PreferViewHolder;
import com.yyhd.joke.simple.R;

import java.util.List;

import common.utils.DataUtils;

/**
 * 功能: (专门给推荐tab页使用的)图文结合类型的适配器
 * 作者: 崔兴旺
 * 日期: 2017/8/4
 * 备注:
 */
public class PreferTextImageAdapter extends MultiPhotoAdapter<DataAllBean, PreferViewHolder> {

    public PreferTextImageAdapter(List<DataAllBean> data) {
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
    public PreferViewHolder onDelayCreateViewHolder(ViewGroup parent, int viewType) {
        return new PreferViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_joke_prefer_text_image, parent, false),false);
    }

    /**
     * 通过数据模型填充视图
     *
     * @param holder
     * @param homeBean
     * @param position
     */
    @Override
    public void onFillViewByModel(final PreferViewHolder holder, final DataAllBean homeBean, final int position) {
        super.onFillViewByModel(holder, homeBean, position);

        /*//有图片
        if(!DataUtils.isEmpty(homeBean.getPictureDetails())){
            //隐藏内容,只展示图片和标题
            holder.textContent.setVisibility(View.GONE);
            if(!TextUtils.isEmpty(homeBean.getTitle())){
                holder.titleText.setVisibility(View.VISIBLE);
                holder.titleText.setText(homeBean.getTitle());
            }else {
                holder.titleText.setVisibility(View.GONE);
            }
            //无图片,则隐藏图片和标题,只展示内容
        }else {
            holder.rvImage.setVisibility(View.GONE);
            holder.titleText.setVisibility(View.GONE);
                /*//*有文字则显示,否则隐藏*//**//*
            if (TextUtils.isEmpty(homeBean.getContent())) {
                holder.textContent.setVisibility(View.GONE);
            } else {
                holder.textContent.setVisibility(View.VISIBLE);

                if(!holder.textContent.hasOnClickListeners()){
                    holder.textContent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            gotoJokeDetailActivity(homeBean, position);
                        }
                    });
                }
                holder.textContent.setText(homeBean.getContent());
            }
        }*/

    }

    /**
     * 是否有图片
     *
     * @param homeBean
     * @param holder
     * @return
     */
    @Override
    protected boolean isHasPhoto(DataAllBean homeBean, PreferViewHolder holder) {
        return !DataUtils.isEmpty(homeBean.getPictureDetails());
    }


}
