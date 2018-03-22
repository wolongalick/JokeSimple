package com.yyhd.joke.utils;

import android.app.ActivityManager;
import android.os.Build;

import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.util.ByteConstants;
import com.facebook.imagepipeline.cache.MemoryCacheParams;

/**
 * 功能:
 * 作者: 赵贺
 * 日期: 2017/10/25
 * 备注:
 */
public class LolipopBitmapMemoryCacheSupplier implements Supplier<MemoryCacheParams> {

    private ActivityManager activityManager;

    public LolipopBitmapMemoryCacheSupplier(ActivityManager activityManager) {
        this.activityManager = activityManager;
    }
    public class  Hh implements MemoryTrimmableRegistry{

        /**
         * Register an object.
         *
         * @param trimmable
         */
        @Override
        public void registerMemoryTrimmable(MemoryTrimmable trimmable) {

        }

        /**
         * Unregister an object.
         *
         * @param trimmable
         */
        @Override
        public void unregisterMemoryTrimmable(MemoryTrimmable trimmable) {

        }
    }


    public MemoryCacheParams get() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new MemoryCacheParams(getMaxCacheSize(),
                    56,
                    Integer.MAX_VALUE,
                    Integer.MAX_VALUE,
                    Integer.MAX_VALUE);
        } else {
            return new MemoryCacheParams(
                    getMaxCacheSize(),
                    256,
                    Integer.MAX_VALUE,
                    Integer.MAX_VALUE,
                    Integer.MAX_VALUE);
        }
    }

    private int getMaxCacheSize() {
        final int maxMemory = Math.min(activityManager.getMemoryClass() * ByteConstants.MB, Integer.MAX_VALUE);

        if (maxMemory < 32 * ByteConstants.MB) {
            return 4 * ByteConstants.MB;
        } else if (maxMemory < 64 * ByteConstants.MB) {
            return 6 * ByteConstants.MB;
        } else {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD) {
                return 8 * ByteConstants.MB;
            } else {
                return maxMemory / 4;
            }
        }
    }
}

