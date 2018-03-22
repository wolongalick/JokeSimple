package com.yyhd.joke.annotation;

import android.support.annotation.IntDef;

import com.yyhd.joke.utils.BizContant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@IntDef ({BizContant.LoadStatus.LOAD_SUCCES, BizContant.LoadStatus.LOAD_FAIL})
@Retention(RetentionPolicy.SOURCE)
public @interface PhotoLoadStatus {

}