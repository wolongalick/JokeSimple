package com.yyhd.joke.utils.photo;

import android.content.Context;
import android.content.Intent;

import com.yyhd.joke.utils.photo.base.BasePhotoHelper;

import java.io.File;

import common.base.BaseFragment_v4;
import common.utils.UUIDUtil;

public class DefaultPhotoHelper extends BasePhotoHelper {


    public DefaultPhotoHelper(Context context) {
        super(context);
    }

    public DefaultPhotoHelper(BaseFragment_v4 fragment) {
        super(fragment);
    }

    /**
     * 卡榫函数:获取用于跳转到裁剪界面的intent
     *
     * @return
     * @param filePath
     */
    @Override
    public Intent getCropIntent(String filePath, int ratio_x, int ratio_y) {
        final Intent intent = new Intent(getContext(), CropPhotoActivity.class);
        intent.putExtra(CropPhotoActivity.INTENT_KEY_SRC_FILE_PATH, filePath);
        intent.putExtra(CropPhotoActivity.INTENT_KEY_TARGET_FILE_PATH,
//                FileUtils.getCacheDir(getContext())+UUIDUtil.getUUid() + ".png");
                new File(getContext().getCacheDir(), UUIDUtil.getUUid() + ".png").getAbsolutePath());
        intent.putExtra(CropPhotoActivity.INTENT_KEY_RATIO_X,ratio_x);
        intent.putExtra(CropPhotoActivity.INTENT_KEY_RATIO_Y,ratio_y);
        return intent;
    }

    /**
     * 卡榫函数:通过intent取出裁剪后的文件地址
     *
     * @param intent
     * @return
     */
    @Override
    public String getCropedFilePathByIntent(Intent intent) {
        return intent.getData().getPath();
    }


}
