package com.yyhd.joke.utils.photo.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;

import com.yyhd.joke.utils.photo.api.CompressListener;
import com.yyhd.joke.utils.photo.api.IPhotoHelper;
import com.yyhd.joke.utils.photo.api.OnSelectCameraPhotoCallback;

import java.io.File;
import java.io.IOException;

import common.base.BaseFragment_v4;
import common.permission.BasePermissionActivity;
import common.permission.BasePermissionFragment_v4;
import common.utils.BLog;
import common.utils.FileUtils;
import common.utils.ImageUtils;
import common.utils.RxJavaCreaterUtils;
import common.utils.T;
import common.utils.UUIDUtil;
import common.utils.UriUtils;
import io.reactivex.ObservableEmitter;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/7/1
 * 备注:
 */
public abstract class BasePhotoHelper implements IPhotoHelper {
    private Context context;
    private BaseFragment_v4 fragment;//v4 fragment

    private File cameraFile;

    private static final int CODE_GALLERY_REQUEST = 1000;
    private static final int CODE_CAMERA_REQUEST = 1001;
    private static final int CODE_CROP = 1002;

    private int ratio_x=1;
    private int ratio_y=1;

    private OnSelectCameraPhotoCallback onSelectCameraPhotoCallback;


    public BasePhotoHelper(Context context) {
        this.context = context;
    }

    public BasePhotoHelper(BaseFragment_v4 fragment) {
        this.fragment = fragment;
    }

    /**
     * 卡榫函数:获取用于跳转到裁剪界面的intent
     * @return
     * @param filePath
     * @param ratio_x
     * @param ratio_y
     */
    public abstract Intent getCropIntent(String filePath, int ratio_x, int ratio_y);

    /**
     * 卡榫函数:通过intent取出裁剪后的文件地址
     * @param intent
     * @return
     */
    public abstract String getCropedFilePathByIntent(Intent intent);


    @Override
    public void openCamera(OnSelectCameraPhotoCallback onSelectCameraPhotoCallback) {
        openCamera(ratio_x,ratio_y,onSelectCameraPhotoCallback);
    }

    @Override
    public void openGallery(OnSelectCameraPhotoCallback onSelectCameraPhotoCallback) {
        openGallery(ratio_x,ratio_y,onSelectCameraPhotoCallback);
    }

    @Override
    public void openCamera(int ratioX, int ratioY, OnSelectCameraPhotoCallback onSelectCameraPhotoCallback) {
        ratio_x=ratioX;
        ratio_y=ratioY;
        this.onSelectCameraPhotoCallback = onSelectCameraPhotoCallback;
        chooseContext(new GetContextListener() {
            @Override
            public void isActivity(BasePermissionActivity activity) {
                activity.requestCamera();
            }

            @Override
            public void isFragment(BasePermissionFragment_v4 fragment) {
                fragment.requestCamera();
            }
        });
    }

    @Override
    public void openGallery(int ratioX, int ratioY, OnSelectCameraPhotoCallback onSelectCameraPhotoCallback) {
        ratio_x=ratioX;
        ratio_y=ratioY;
        this.onSelectCameraPhotoCallback = onSelectCameraPhotoCallback;
        chooseContext(new GetContextListener() {
            @Override
            public void isActivity(BasePermissionActivity activity) {
                activity.requestStorage();
            }

            @Override
            public void isFragment(BasePermissionFragment_v4 fragment) {
                fragment.requestStorage();
            }
        });
    }

    @Override
    public void compress(File file, final CompressListener compressListener) {
        Luban.with(getContext())
                .load(file)                                             // 传人要压缩的图片列表
                .ignoreBy(100)                                          // 忽略不压缩图片的大小
                .setTargetDir(file.getParentFile().getAbsolutePath())   // 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        compressListener.complete(file);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }).launch();    //启动压缩
    }

    @Override
    public void onGetStoragePerm(boolean isSuccess) {
        if (!isSuccess) {
            T.show(getActivity(), "未获得打开相册权限");
            return;
        }

        final Intent intentFromGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intentFromGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        chooseContext(new GetContextListener() {
            @Override
            public void isActivity(BasePermissionActivity activity) {
                activity.startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
            }

            @Override
            public void isFragment(BasePermissionFragment_v4 fragment) {
                fragment.startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
            }
        });
    }

    @Override
    public void onGetCameraPerm(boolean isSuccess) {
        if (!isSuccess) {
            T.show(getActivity(), "未获得打开相机权限");
            return;
        }

        try {
            cameraFile = new File(getContext().getExternalCacheDir().getAbsoluteFile(), "avatar.png");
            if (!cameraFile.getParentFile().exists()) {
                cameraFile.getParentFile().mkdirs();
            }
            try {
                cameraFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            final Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            camera.putExtra("return-data", true);
            camera.putExtra(MediaStore.EXTRA_OUTPUT, UriUtils.getUriCompatibleN(getContext(), cameraFile.getPath()));
            chooseContext(new GetContextListener() {
                @Override
                public void isActivity(BasePermissionActivity activity) {
                    activity.startActivityForResult(camera, CODE_CAMERA_REQUEST);
                }

                @Override
                public void isFragment(BasePermissionFragment_v4 fragment) {
                    fragment.startActivityForResult(camera, CODE_CAMERA_REQUEST);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            T.show(getActivity(), "相机不存在");
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //1.相册选择完成后
        if (requestCode == CODE_GALLERY_REQUEST && resultCode == Activity.RESULT_OK && intent != null) {
            //保存选择的相册图片
            gotoCropPhotoActivity(ImageUtils.photoUri2FilePath(getContext(), intent.getData()));
            //2.拍照完成后
        } else if (requestCode == CODE_CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            //保存图片类型的消息
            BLog.i("拍照后保存到图片路径:" + cameraFile.getAbsolutePath() + ",文件长度:" + cameraFile.length());
            if (cameraFile.length() == 0) {
                //有些手机拍照后的得到图片大小为0,所以需要剪切一下文件
                RxJavaCreaterUtils.createObservable(new RxJavaCreaterUtils.SimpleRxJavaCallback<File>() {
                    @Override
                    public void subscribe(ObservableEmitter<File> emitter) throws Exception {
                        File desFile = new File(FileUtils.getCachePath(context), UUIDUtil.getUUid() + ".png");
                        FileUtils.cutFile(cameraFile, desFile);
                        emitter.onNext(desFile);
                    }

                    @Override
                    public void onNext(File file) {
                        gotoCropPhotoActivity(file.getAbsolutePath());
                    }
                });
            } else {
                gotoCropPhotoActivity(cameraFile.getAbsolutePath());
            }
        } else if (requestCode == CODE_CROP && resultCode == Activity.RESULT_OK) {
            onSelectCameraPhotoCallback.onSelected(getCropedFilePathByIntent(intent));
        }
    }

    /**
     * 跳转到裁剪图片界面
     *
     * @param filePath
     */
    private void gotoCropPhotoActivity(final String filePath) {
        chooseContext(new GetContextListener() {
            @Override
            public void isActivity(BasePermissionActivity activity) {
                activity.startActivityForResult(getCropIntent(filePath,ratio_x,ratio_y), CODE_CROP);
            }

            @Override
            public void isFragment(BasePermissionFragment_v4 fragment) {
                fragment.startActivityForResult(getCropIntent(filePath,ratio_x,ratio_y), CODE_CROP);
            }
        });
    }

    private interface GetContextListener {
        void isActivity(BasePermissionActivity activity);

        void isFragment(BasePermissionFragment_v4 fragment);
    }

    private void chooseContext(GetContextListener listener) {
        if (context != null && context instanceof BasePermissionActivity) {
            listener.isActivity(((BasePermissionActivity) context));
        } else if (fragment != null && fragment instanceof BasePermissionFragment_v4) {
            listener.isFragment(fragment);
        } else {
            throw new RuntimeException("Not found context");
        }
    }

    protected Context getContext() {
        return context == null ? fragment.getContext() : context;
    }

    protected Activity getActivity() {
        return fragment.getHostActivity();
    }
}
