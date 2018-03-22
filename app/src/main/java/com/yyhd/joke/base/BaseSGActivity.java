package com.yyhd.joke.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.yyhd.joke.utils.photo.ImagePipelineConfigFactoryTwo;
import com.yyhd.joke.utils.photo.UcropPhotoHelper;
import com.yyhd.joke.utils.photo.api.IPhotoHelper;

import common.base.BaseActivity;
import common.base.MvpPresenter;
import common.base.MvpView;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/8/5
 * 备注:
 */
public abstract class BaseSGActivity<V extends MvpView,P extends MvpPresenter<V>> extends BaseActivity<V,P> {
    private final String TAG="BaseSGActivity";
    private IPhotoHelper iPhotoHelper;
//    private MyBroadcastReceiver broadcastReceiver;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.remove("android:support:fragments");   //注意：基类是Activity时参数为android:fragments， 一定要在super.onCreate函数前执行！！！
        }
        reInitOnCreate();
        super.onCreate(savedInstanceState);
    }


    public IPhotoHelper getPhotoHelper(){
        if(iPhotoHelper==null){
            iPhotoHelper = new UcropPhotoHelper(this);
        }
        return iPhotoHelper;
    }

    /**
     * 重新初始化(如果需要)
     */
    private void reInitOnCreate() {

        if(!Fresco.hasBeenInitialized()){
            Fresco.initialize(this, ImagePipelineConfigFactoryTwo.getOkHttpImagePipelineConfig(this, (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)));
        }
    }


    @Override
    public void onGetCameraPerm(boolean isSuccessed) {
        super.onGetCameraPerm(isSuccessed);
        if(iPhotoHelper!=null){
            iPhotoHelper.onGetCameraPerm(isSuccessed);
        }
    }

    @Override
    public void onGetStoragePerm(boolean isSuccessed) {
        super.onGetStoragePerm(isSuccessed);
        if(iPhotoHelper!=null){
            iPhotoHelper.onGetStoragePerm(isSuccessed);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(iPhotoHelper!=null){
            iPhotoHelper.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        broadcastReceiver.endObserver();
    }
}
