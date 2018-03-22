package com.yyhd.joke.module.home.view.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.yyhd.joke.bean.DataAllBean;
import com.yyhd.joke.bean.PictureDetail;
import com.yyhd.joke.module.home.view.adapter.holder.MultiPhotoViewHolder;
import com.yyhd.joke.module.home.view.adapter.holder.PhotoViewHolder;
import com.yyhd.joke.utils.BizContant;

import java.util.List;

import common.ui.ICommonOnItemClickListener;
import common.utils.DataUtils;
import common.utils.T;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/8/4
 * 备注:
 */
public abstract class MultiPhotoAdapter<Model extends DataAllBean, Holder extends MultiPhotoViewHolder> extends BaseHomeAdapter<Model, Holder> {
    private static final String TAG = "PhotoAdapter";

    public MultiPhotoAdapter(List<Model> data) {
        super(data);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    /**
     * 通过数据模型填充视图
     *
     * @param holder
     * @param model
     * @param position
     */
    @Override
    public void onFillViewByModel(final Holder holder, final Model model, final int position) {
        super.onFillViewByModel(holder, model, position);

        /*有图片url则显示,否则隐藏*/
        if (!DataUtils.isEmpty(model.getPictureDetails())) {
            holder.rvImage.setVisibility(View.VISIBLE);
            showImage(holder, model,position);
        } else {
            holder.rvImage.setVisibility(View.GONE);
        }
    }

    private void showImage(final Holder holder, final Model model, final int groupPosition) {
        int spanCount;
        final int size = model.getPictureDetails().size();
        switch (size) {
            case 1:
                spanCount= BizContant.PhotoGridSpanCount.SINGLE;     //单张图片
                break;
            /*case 4:
                spanCount= BizContant.PhotoGridSpanCount.MATTS;       //四张图片(田字格,两列)
                break;*/
            default:
                spanCount= BizContant.PhotoGridSpanCount.SUDOKU;      //默认为九宫格,三列
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), spanCount) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        holder.rvImage.setLayoutManager(gridLayoutManager);

//        ItemDecorationUtils.addItemDecoration(holder.rvImage,new NormalVerGLRVDecoration(getContext().getResources().getDimensionPixelOffset(R.dimen.home_spacing),getContext().getResources().getDrawable(R.drawable.divider_list_home_multi_photo_8dp)));

        final PhotoAdapter photoAdapter = new PhotoAdapter(model.getPictureDetails(),model.getId(),model.getCategoryCode());
        photoAdapter.setPhotoGridSpanCount(spanCount);
        holder.rvImage.setFocusableInTouchMode(false);
        holder.rvImage.setAdapter(photoAdapter);
        photoAdapter.setiCommonOnItemClickListener(new ICommonOnItemClickListener<PhotoViewHolder, PictureDetail>() {
            @Override
            public void onItemClick(PhotoViewHolder photoViewHolder, PictureDetail pictureDetail, int position) {
                if(pictureDetail.getLoad_status()== BizContant.LoadStatus.LOAD_SUCCES){
                    if(!(size== BizContant.PhotoGridSpanCount.SINGLE && pictureDetail.isGif())){
                        gotosBrowsePhotoActivity(position, model);
                    }
                }else {
                    photoAdapter.onFillViewByModel4Manual(photoViewHolder,pictureDetail,position,true);
                }
            }
        });
    }

    private void gotosBrowsePhotoActivity(int position, Model model) {
        T.show(getContext(),"禁止放大图");
    }



}
