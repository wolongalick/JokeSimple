package com.yyhd.joke.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.yyhd.joke.bean.PictureDetail;
import com.yyhd.joke.db.entity.UserInfo;
import com.yyhd.joke.simple.BuildConfig;
import com.yyhd.joke.simple.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import common.base.OperationResultListener;
import common.bean.ErrorMsg;
import common.utils.AES;
import common.utils.BLog;
import common.utils.BitmapUtils;
import common.utils.FileUtils;
import common.utils.SpUtils;
import common.utils.UriUtils;

/**
 * 功能: 业务工具类
 * 作者: 崔兴旺
 * 日期: 2017/8/3
 * 备注:
 */
public class BizUtils {

    private static final java.lang.String TAG = "BizUtils";

    public static int jokeCode2LayoutType(String jokeCode) {
        switch (jokeCode) {
            case BizContant.JokeCode.JOKE:
                return BizContant.JokeLayoutType.ITEM_TYPE_TEXT;
            case BizContant.JokeCode.ODDPHOTO:
                return BizContant.JokeLayoutType.ITEM_TYPE_IMAGE;
            case BizContant.JokeCode.BEAUTY:
                return BizContant.JokeLayoutType.ITEM_TYPE_IMAGE;
            case BizContant.JokeCode.COMMEND:
                return BizContant.JokeLayoutType.ITEM_TYPE_PREFER_TEXT_IMAGE;
            case BizContant.JokeCode.ODDNEWS:
                return BizContant.JokeLayoutType.ITEM_TYPE_TEXT_IMAGE;
        }
        return BizContant.JokeLayoutType.ITEM_TYPE_PREFER_TEXT_IMAGE;
    }

    public static String parseJokeCode2String(String jokeCode) {
        // TODO: 2017/12/3 应该从缓存(如数据库)中获取频道名称
        switch (jokeCode) {
            case BizContant.JokeCode.JOKE:
                return "段子";
            case BizContant.JokeCode.ODDPHOTO:
                return "趣图";
            case BizContant.JokeCode.BEAUTY:
                return "美女";
            case BizContant.JokeCode.COMMEND:
                return "推荐";
            case BizContant.JokeCode.ODDNEWS:
                return "趣闻";
            default:
                return "未知频道";
        }
    }

    /**
     * 获取顶的随机数
     *
     * @return
     */
    public static int getRandomDigg() {
        double v = Math.random() * 1000;
        if (v < 200) {
            v = Math.random() * 1000;
        }
        return (int) v;
    }

    /**
     * 获取踩的随机数
     *
     * @return
     */
    public static int getRandomBury() {
        double v = Math.random() * 100;
        if (v == 0) {
            v = Math.random() * 100;
        }
        return (int) v;
    }


    /**
     * 获取顶的随机数
     *
     * @return
     */
    public static int getRandomCommentDigg(int minValue, int maxValue) {
        double v = Math.random() * 1000;
        if (v < minValue || v > maxValue) {
            v = minValue;
        }
        return (int) v;
    }

    /**
     * ImageUrl集合转成url字符串集合
     *
     * @param pictureDetails
     * @return
     */
    public static ArrayList<String> imageUrlList2UrlList(List<PictureDetail> pictureDetails) {
        ArrayList<String> urlList = new ArrayList<>();
        for (PictureDetail pictureDetail : pictureDetails) {
            urlList.add(pictureDetail.getQiniuUrl());
        }
        return urlList;
    }


/*
    public static String removeImageUrlSuffix(String url) {
        int i = url.lastIndexOf(BizContant.IMAGE_URL_SUFFIX);
        if (i < 0) {
            return url;
        }
        return url.substring(0, i);
    }

    public static String addImageUrlSuffix(String url) {
        int i = url.lastIndexOf(BizContant.IMAGE_URL_SUFFIX);
        if (i < 0) {
            return url+BizContant.IMAGE_URL_SUFFIX;
        }
        return url;
    }*/

    /**
     * 保存渠道
     *
     * @param channel
     */
    public static void saveChannel(String channel) {
        SpUtils.getInstance().put(BizSpKey.channel, channel);
    }

    /**
     * 获取渠道
     *
     * @return
     */
    public static String getChannel() {
        return SpUtils.getInstance().get(BizSpKey.channel, "");
    }

    public static void showAvatar(SimpleDraweeView sdvAvatar, String headPic) {
        if (!TextUtils.isEmpty(headPic)) {
            sdvAvatar.setImageURI(headPic);
        } else {
            sdvAvatar.setImageURI(UriUtils.parseRes2Uri(BuildConfig.APPLICATION_ID, R.drawable.default_account_avatar));
        }
    }

    public static void saveImei(String imei){
        SpUtils.getInstance().put(BizSpKey.imei,imei);
    }

    public static String getImei(){
        return SpUtils.getInstance().get(BizSpKey.imei,"");
    }


    /**
     * 保存静态图片
     * @param activity
     * @param isFirstSave
     * @param operationResultListener
     */
    public static void saveStaticPhoto(final Activity activity, final PictureDetail pictureDetail, final boolean isFirstSave, final OperationResultListener operationResultListener) {
        if (savePhotoFromCache(activity,isFirstSave ? pictureDetail.getOriginImgurl() : pictureDetail.getQiniuUrl(),"jpg", operationResultListener)){
            return;
        }

        final DataSource<CloseableReference<CloseableImage>> dataSource = Fresco.getImagePipelineFactory().getImagePipeline().fetchDecodedImage(ImageRequest.fromUri(Uri.parse(isFirstSave ? pictureDetail.getOriginImgurl() : pictureDetail.getQiniuUrl())), activity);
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl(Bitmap bitmap) {
                //此处是子线程,下载图片成功后会执行到这里
                File photofile = createSavePhotoFile("jpg");
                try {
                    if(bitmap==null){
                        operationResultListener.onFail(new ErrorMsg("保存图片失败"));
                    }
                    BitmapUtils.saveBitmap2File(bitmap, photofile.getAbsolutePath());
                    BitmapUtils.scanFileAsync2(activity,photofile.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                    BLog.e(e.getMessage());
                    operationResultListener.onFail(new ErrorMsg("保存图片失败:"+e.getMessage()));
                }
                //此处是子线程
                operationResultListener.onSuccess(photofile);
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                //此处是子线程,下载图片失败后会执行到这里
                if(isFirstSave){
                    saveStaticPhoto(activity,pictureDetail,false,operationResultListener);
                }else {
                    operationResultListener.onFail(new ErrorMsg(dataSource.getFailureCause().getMessage()));
                }
            }
        }, CallerThreadExecutor.getInstance());
    }


    /**
     * 从本地Fresco缓存中取出图片并保存
     * @param activity
     * @param url
     * @param format
     * @param operationResultListener
     * @return
     */
    private static boolean savePhotoFromCache(Activity activity, String url, String format, OperationResultListener operationResultListener) {
        //此处是主线程
        FileBinaryResource resource = (FileBinaryResource) Fresco.getImagePipelineFactory()
                .getMainFileCache()
                .getResource(new SimpleCacheKey(url));
        if(resource!=null){
            File file = resource.getFile();
            if(file !=null && file.exists()){
                File targetFile = new File(BizUtils.getSavePhotoFolderPath(),System.currentTimeMillis() + "." + format);
                FileUtils.copyfile(file, targetFile);
                try {
                    BitmapUtils.scanFileAsync2(activity,targetFile.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    BLog.e(e.getMessage());
                }
                //此处是主线程
                operationResultListener.onSuccess(targetFile.toString());
                return true;
            }
        }
        /*if(flag){
            AlerterUtils.alerter(activity,"保存失败");
            operationResultListener.onFail(null);
        }*/
        return false;
    }

    /**
     * 创建保存图片的文件
     * @param format
     * @return
     */
    private static File createSavePhotoFile(String format){
        return new File(BizUtils.getSavePhotoFolderPath(), System.currentTimeMillis() + "." + format);
    }

    /**
     * 加密
     * @param content
     * @return
     */
    public static String ecryptContent(String content){
        return AES.encrypt(content, BizContant.AES_KEY);
    }

    /**
     * 显示解密内容
     * @param textView
     * @param content
     */
    public static void showDecryptContent(TextView textView, String content){
        showDecryptContent(textView,content,"");
    }

    /**
     * 显示解密内容
     * @param content
     * @return
     */
    public static void showDecryptContent(TextView textView, String content, String defaultStr){
        //如果传入的内容本身就是空的,则使用默认值
        textView.setText(!TextUtils.isEmpty(content) ? content : defaultStr);
    }


    /**
     * 获取view的可见高度半分比<br/>
     * 说明:
     * (1)view在recyclerView上部分超出屏幕时:Rect(30, -1714 - 1051, -114)
     *
     * @param childView
     * @return
     */
    public static float getVisiblePercent(View childView){
        Rect childRect=new Rect();
        childView.getLocalVisibleRect(childRect);
//        BLog.i("子布局高度:"+childView.getMeasuredHeight()+",四边:"+childRect.toShortString());

        int measuredHeight = childView.getMeasuredHeight();
        if(childRect.bottom<0 || childRect.top<0 || childRect.bottom>measuredHeight || childRect.top>measuredHeight){
            return 0;
        }
        int visibleHeight = childRect.bottom - childRect.top;

        return ((float)visibleHeight)/measuredHeight;
    }

    public static String getSavePhotoFolderPath(){
        return Environment.getExternalStorageDirectory()+ File.separator+ "joke_cdlm";
    }

    public static String getNickname(UserInfo userInfo){
        return (userInfo!=null && !TextUtils.isEmpty(userInfo.getNickName())) ? userInfo.getNickName() : BizContant.ANONYMITY_USER;
    }

    /**
     * 是否隐藏金币奖励提示
     * @return
     */
    public static boolean isHideAwardToast(){
        return SpUtils.getInstance().get(BizSpKey.isHideAwardToast,false);
    }

    /**
     * 保存金币奖励提示的开关状态
     * @param isHideAwardToast
     */
    public static void saveIsHideAwardToast(boolean isHideAwardToast){
        SpUtils.getInstance().put(BizSpKey.isHideAwardToast,isHideAwardToast);
    }

    public static void saveIsShowedAwardHint(boolean isHideAwardToast){
        SpUtils.getInstance().put(BizSpKey.isHideAwardToast,isHideAwardToast);
    }


}
