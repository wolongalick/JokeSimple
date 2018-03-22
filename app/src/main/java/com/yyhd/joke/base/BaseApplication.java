package com.yyhd.joke.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.yyhd.joke.simple.BuildConfig;
import com.yyhd.joke.utils.photo.ImagePipelineConfigFactoryTwo;

import common.utils.BLog;
import common.utils.SpUtils;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/8/5
 * 备注:
 */
public class BaseApplication extends Application {


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BLog.rootLogOn= BuildConfig.DEBUG;
        SpUtils.getInstance().init(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        try {
            ImagePipelineConfigFactoryTwo.TrimMemory(level);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        try {
            ImagePipelineConfigFactoryTwo.clearAllMemoryCaches();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
