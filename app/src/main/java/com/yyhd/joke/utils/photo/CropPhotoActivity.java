package com.yyhd.joke.utils.photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;
import com.isseiaoki.simplecropview.util.Logger;
import com.yyhd.joke.simple.R;
import com.yyhd.joke.base.BaseSGActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.base.MvpPresenter;
import common.ui.Topbar;
import common.utils.BLog;
import common.utils.FileUtils;
import common.utils.ProgressDialogUtils;
import common.utils.ScreenUtils;
import common.utils.T;


public class CropPhotoActivity extends BaseSGActivity {
    public static final String INTENT_KEY_SRC_FILE_PATH = "intent_key_src_file_path";
    public static final String INTENT_KEY_TARGET_FILE_PATH = "intent_key_target_file_path";

    public static final String INTENT_KEY_RATIO_X = "intent_key_ratio_x";
    public static final String INTENT_KEY_RATIO_Y = "intent_key_ratio_y";

    private final int DEFAULT_RATIO_X=1;
    private final int DEFAULT_RATIO_Y=1;

    @BindView(R.id.civ_photo)
    CropImageView civPhoto;
    private String srcFilePath; //源文件路径


    /**
     * 初始化变量
     */
    @Override
    public void initParmers() {

    }

    /**
     * 初始化页面控件
     */
    @Override
    public void initViews() {
        setContentView(R.layout.activity_crop_photo);
        ButterKnife.bind(this);

        civPhoto.setCustomRatio(getIntent().getIntExtra(INTENT_KEY_RATIO_X,DEFAULT_RATIO_X),getIntent().getIntExtra(INTENT_KEY_RATIO_Y,DEFAULT_RATIO_Y));

        Logger.enabled = true;
        srcFilePath = getIntent().getStringExtra(INTENT_KEY_SRC_FILE_PATH);
        Bitmap bitmap = BitmapFactory.decodeFile(srcFilePath);
        int screenWidth= ScreenUtils.getScreenWidth(this);
        if(bitmap.getWidth()<=screenWidth){
            civPhoto.setImageBitmap(bitmap);
        }else{
            Bitmap bmp= Bitmap.createScaledBitmap(bitmap, screenWidth, bitmap.getHeight()*screenWidth/bitmap.getWidth(), true);
            civPhoto.setImageBitmap(bmp);
        }
        BLog.i("srcFilePath:"+srcFilePath);

        getTopbar().setOnClickTopbarRightListener(new Topbar.OnClickTopbarRightListener() {
            @Override
            public void onClickTopbarRight() {
                ProgressDialogUtils.showProgressDialog(CropPhotoActivity.this,"请稍候...");          //MI4中    targetFilePath:/storage/emulated/0/Android/data/com.acewill.crmoa.demo/cache/send/image/fe46de0b-98ab-4b59-b9b4-b1eede54997a.jpg
                String targetFilePath = getIntent().getStringExtra(INTENT_KEY_TARGET_FILE_PATH);    //nexus5x中targetFilePath:/storage/emulated/0/Android/data/com.acewill.crmoa.demo/cache/send/image/6f7016a1-5791-4666-8b6d-4023e75de0ee.jpg
                FileUtils.createFile(targetFilePath);
                //注释:裁剪后保存的目录uri必须用Uri.fromFile,不能用FileProvider.getUriForFile
                civPhoto.startCrop(Uri.fromFile(new File(targetFilePath)),
                        new CropCallback() {
                            @Override
                            public void onSuccess(Bitmap cropped) {
//                                civPhoto.setImageBitmap(cropped);//不需要更新UI
                            }

                            @Override
                            public void onError() {

                            }
                        }, new SaveCallback() {
                            @Override
//outputUri的值形如:file:///storage/emulated/0/Android/data/com.acewill.crmoa.demo/cache/send/image/c7cb0bd0-7f74-45fa-9fac-32a189ca33dd.jpg
                            public void onSuccess(Uri outputUri) {
                                //裁剪完成
                                ProgressDialogUtils.dismissProgressDialog();
                                Intent intent = new Intent();
                                intent.setData(outputUri);
                                setResult(RESULT_OK, intent);
                                finish();
                            }

                            @Override
                            public void onError() {
                                ProgressDialogUtils.dismissProgressDialog();
                                T.show(CropPhotoActivity.this,"保存图片失败,请重试");
                            }
                        });
            }
        });
    }

    /**
     * 初始化页面控件的值以及设置控件监听
     */
    @Override
    public void initValues() {

    }



    /**
     * 在onDestory()手动释放资源
     */
    @Override
    public void releaseOnDestory() {

    }


    /**
     * 创建presenter(卡榫函数)
     *
     * @return
     */
    @Override
    public MvpPresenter createPresenter() {
        return null;
    }
}
