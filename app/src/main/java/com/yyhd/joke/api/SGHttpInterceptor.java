package com.yyhd.joke.api;

import common.utils.AbstractHttpInterceptor;
import common.utils.DeviceUtils;
import okhttp3.Request;

/**
 * 智慧就业项目默认拦截器
 * Created by cxw on 2016/11/1.
 */

public class SGHttpInterceptor extends AbstractHttpInterceptor {

    public SGHttpInterceptor(boolean enableDebug) {
        super(enableDebug);
    }

    @Override
    public void addHeader(Request.Builder builder) {
        builder.addHeader("uuid", DeviceUtils.getUniquePsuedoID());
    }
}
