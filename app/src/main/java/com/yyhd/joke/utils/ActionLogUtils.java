package com.yyhd.joke.utils;

import android.content.Context;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2018/1/14
 * 备注:
 */
public class ActionLogUtils {
    private static ActionLogUtils instance = null;

    private ActionLogUtils(){
    }

    public static ActionLogUtils getInstance() {
        if (instance == null) {
            synchronized (ActionLogUtils.class) {
                if (instance == null) {
                    instance = new ActionLogUtils();
                }
            }
        }
        return instance;
    }

    public void initConfig(final Context context) {
    }
}
