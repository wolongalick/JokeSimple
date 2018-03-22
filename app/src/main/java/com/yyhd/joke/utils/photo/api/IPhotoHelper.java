package com.yyhd.joke.utils.photo.api;

import android.content.Intent;

import java.io.File;

public interface IPhotoHelper {
    void openCamera(OnSelectCameraPhotoCallback onSelectCameraPhotoCallback);

    void openGallery(OnSelectCameraPhotoCallback onSelectCameraPhotoCallback);

    void openCamera(int ratioX, int ratioY, OnSelectCameraPhotoCallback onSelectCameraPhotoCallback);

    void openGallery(int ratioX, int ratioY, OnSelectCameraPhotoCallback onSelectCameraPhotoCallback);

    void compress(File file, CompressListener compressListener);

    void onGetStoragePerm(boolean isSuccess);

    void onGetCameraPerm(boolean isSuccess);

    void onActivityResult(int requestCode, int resultCode, Intent intent);
}
