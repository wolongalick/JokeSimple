package com.yyhd.joke.bean;

import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import pl.droidsonroids.gif.GifImageView;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/12/7
 * 备注:
 */
public class GifWrapData {
    private float visiblePercent;
    private SimpleDraweeView iv_image;
    private GifImageView gifImageView;
    private View ivPlayGif;
    private PictureDetail pictureDetail;


    public GifWrapData(float visiblePercent, SimpleDraweeView iv_image, View ivPlayGif, PictureDetail pictureDetail,GifImageView gifImageView) {
        this.visiblePercent = visiblePercent;
        this.iv_image = iv_image;
        this.ivPlayGif = ivPlayGif;
        this.pictureDetail = pictureDetail;
        this.gifImageView = gifImageView;
    }

    public float getVisiblePercent() {
        return visiblePercent;
    }

    public void setVisiblePercent(float visiblePercent) {
        this.visiblePercent = visiblePercent;
    }

    public SimpleDraweeView getIv_image() {
        return iv_image;
    }

    public void setIv_image(SimpleDraweeView iv_image) {
        this.iv_image = iv_image;
    }

    public View getIvPlayGif() {
        return ivPlayGif;
    }

    public void setIvPlayGif(View ivPlayGif) {
        this.ivPlayGif = ivPlayGif;
    }

    public PictureDetail getPictureDetail() {
        return pictureDetail;
    }

    public void setPictureDetail(PictureDetail pictureDetail) {
        this.pictureDetail = pictureDetail;
    }

    public GifImageView getGifImageView() {
        return gifImageView;
    }

    public void setGifImageView(GifImageView gifImageView) {
        this.gifImageView = gifImageView;
    }
}
