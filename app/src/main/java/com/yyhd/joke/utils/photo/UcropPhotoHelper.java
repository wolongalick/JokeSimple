package com.yyhd.joke.utils.photo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.common.R;
import com.yalantis.ucrop.UCrop;
import com.yyhd.joke.base.BaseSGFragment_v4;
import com.yyhd.joke.utils.photo.base.BasePhotoHelper;

import java.io.File;

import common.utils.UUIDUtil;
import common.utils.UriUtils;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/7/1
 * 备注:
 */
public class UcropPhotoHelper extends BasePhotoHelper {
    public UcropPhotoHelper(Context context) {
        super(context);
    }

    public UcropPhotoHelper(BaseSGFragment_v4 fragment) {
        super(fragment);
    }

    /**
     * 卡榫函数:获取用于跳转到裁剪界面的intent
     *
     * @param filePath
     * @param ratio_x
     * @param ratio_y
     * @return
     */
    @Override
    public Intent getCropIntent(String filePath, int ratio_x, int ratio_y) {
        UCrop.Options options = new UCrop.Options();
        options.setHideBottomControls(true);
        options.setCircleDimmedLayer(true);
        options.setToolbarColor(getContext().getResources().getColor(R.color.hei_33));
        options.setStatusBarColor(getContext().getResources().getColor(R.color.hei_33));
        return UCrop.of(
                UriUtils.getUriCompatibleN(getContext(), filePath),
                Uri.fromFile(new File(getContext().getCacheDir(), UUIDUtil.getUUid() + ".png")))
                .withAspectRatio(ratio_x, ratio_y)
                .withOptions(options)
                .getIntent(getContext());
    }

    /**
     * 卡榫函数:通过intent取出裁剪后的文件地址
     *
     * @param intent
     * @return
     */
    @Override
    public String getCropedFilePathByIntent(Intent intent) {
        return UCrop.getOutput(intent).getPath();
    }
}
