package com.yyhd.joke.utils;

import android.app.ActivityManager;
import android.os.Build;

import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.imagepipeline.cache.MemoryCacheParams;

import common.utils.BLog;

/**
 * 功能:
 * 作者: 赵贺
 * 日期: 2017/11/10
 * 备注:
 */
public class MyBitmapMemoryCacheParamsSupplier implements Supplier<MemoryCacheParams> {
    private static final int MAX_CACHE_ENTRIES =  Integer.MAX_VALUE;
    private static final int MAX_CACHE_ASHM_ENTRIES =  256;
    private static final int MAX_CACHE_EVICTION_SIZE = Integer.MAX_VALUE;
    private static final int MAX_CACHE_EVICTION_ENTRIES = Integer.MAX_VALUE;
    private static final java.lang.String TAG = "MyBitmapMemoryCacheParamsSupplier";
    private final ActivityManager mActivityManager;

    public MyBitmapMemoryCacheParamsSupplier(ActivityManager activityManager) {
        mActivityManager = activityManager;
    }

    @Override
    public MemoryCacheParams get() {
        int maxCacheSize = getMaxCacheSize();

        BLog.i(TAG,"maxCacheSize="+maxCacheSize);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new MemoryCacheParams(
                    maxCacheSize,                           // 内存缓存中总图片的最大大小,以字节为单位。
                    20,                                     // 内存缓存中图片的最大数量。
                    Integer.MAX_VALUE,                      // 内存缓存中准备清除但尚未被删除的总图片的最大大小,以字节为单位。
                    Integer.MAX_VALUE,                      // 内存缓存中准备清除的总图片的最大数量。
                    Integer.MAX_VALUE);                     // 内存缓存中单个图片的最大大小。
        } else {
            return new MemoryCacheParams(
                    maxCacheSize,
                    MAX_CACHE_ASHM_ENTRIES,
                    Integer.MAX_VALUE,
                    Integer.MAX_VALUE,
                    Integer.MAX_VALUE);
        }
    }

    private int getMaxCacheSize() {
        final int maxMemory =
                Math.min(mActivityManager.getLargeMemoryClass() * ByteConstants.MB, Integer.MAX_VALUE);
        if (maxMemory < 32 * ByteConstants.MB) {
            return 4 * ByteConstants.MB;
        } else if (maxMemory < 64 * ByteConstants.MB) {
            return 6 * ByteConstants.MB;
        } else {
            return maxMemory / 5;
        }
    }
}