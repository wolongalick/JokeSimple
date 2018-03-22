package com.yyhd.joke.utils;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.View;

import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.yyhd.joke.bean.PictureDetail;

import java.io.IOException;

import common.utils.BLog;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/12/6
 * 备注:
 */
public class JokePhotoUtils {
    private final String TAG = "JokePhotoUtils";
    private SimpleDraweeView lastIvImage;
    private PictureDetail lastPictureDetail;
    private View lastIvPlayGif;

    public void showGif(final Context context, final SimpleDraweeView ivImage, final View ivPlayGif, final PictureDetail pictureDetail, final GifImageView gifImageView) {
        if (lastIvImage == ivImage) {
            return;
        }

        //上一个播放的图片显示gif第一帧
        if (lastIvImage != null && lastPictureDetail != null) {
            showFirstFrame(context, lastIvImage, lastPictureDetail);
        }

        //上一个播放的图片显示出GIF图标
        if (lastIvPlayGif != null) {
            lastIvPlayGif.setVisibility(View.VISIBLE);
        }

//        CustomProgressBarDrawable progressBarDrawable = new CustomProgressBarDrawable();
//        progressBarDrawable.setBackgroundColor(context.getResources().getColor(R.color.white_dc_translucence));
//        progressBarDrawable.setColor(context.getResources().getColor(R.color.main_yellow_translucence));
//        int barWidth = DensityUtils.dp2px(context, 3);
//        progressBarDrawable.setBarWidth(barWidth);
//        progressBarDrawable.getBounds().bottom = barWidth;

        if (ivImage == null) {
//            T.show(context,"ivImage=null");
            BLog.e("ivImage=null--->showGif()");
            return;
        }

//        GenericDraweeHierarchyBuilder hierarchyBuilder = GenericDraweeHierarchyBuilder.newInstance(context.getResources());
//
//        //设置圆角半径
//        hierarchyBuilder.setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP);
//        hierarchyBuilder.setActualImageFocusPoint(new PointF(0.5f, 0f));
//        hierarchyBuilder.setFailureImage(R.drawable.error_reloading);
//        hierarchyBuilder.setFailureImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);
//        hierarchyBuilder.setRetryImage(R.drawable.error_reloading);
//        hierarchyBuilder.setRetryImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);
//
//        FileBinaryResource resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainFileCache().getResource(new SimpleCacheKey(pictureDetail.getFirstFrame()));
//        if (resource!=null && resource.getFile() != null && resource.getFile().exists()) {
//            hierarchyBuilder.setPlaceholderImage(Drawable.createFromPath(resource.getFile().toString()));
//            hierarchyBuilder.setPlaceholderImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP);
//        } else {
//            hierarchyBuilder.setPlaceholderImage(context.getResources().getDrawable(R.drawable.place_holder));
//            hierarchyBuilder.setPlaceholderImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);
//        }
//
//        hierarchyBuilder.setProgressBarImage(progressBarDrawable);
//        hierarchyBuilder.setOverlay(context.getResources().getDrawable(R.drawable.joke_photo_frame));
//        GenericDraweeHierarchy hierarchy = hierarchyBuilder.build();
//
//        //设置Hierarchy
//        ivImage.setHierarchy(hierarchy);

        ControllerListener<ImageInfo> controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onSubmit(String id, Object callerContext) {
                super.onSubmit(id, callerContext);
            }

            @Override
            public void onFinalImageSet(
                    String id,
                    @Nullable ImageInfo imageInfo,
                    @Nullable Animatable animatable) {
                if (imageInfo == null) {
                    return;
                }

                pictureDetail.setLoad_status(BizContant.LoadStatus.LOAD_SUCCES);
                ivPlayGif.setVisibility(View.GONE);

                FileBinaryResource resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainFileCache().getResource(new SimpleCacheKey(pictureDetail.getQiniuUrl()));
                if (resource != null && resource.getFile() != null && resource.getFile().exists()) {
                    ivImage.setVisibility(View.GONE);
                    gifImageView.setVisibility(View.VISIBLE);
                    try {
                        GifDrawable gifDrawable=new GifDrawable(resource.getFile());
                        gifImageView.setImageDrawable(gifDrawable);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
//                BLog.d("Intermediate image received");
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                BLog.e("Error loading %s", throwable.toString());
                pictureDetail.setLoad_status(BizContant.LoadStatus.LOAD_FAIL);
                ivImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reloadGif(ivImage, pictureDetail, ivPlayGif);
                    }
                });

            }
        };

        String qiniuUrl4Webp = pictureDetail.getQiniuUrl();
//        BLog.i(TAG,"自动播放gif的url:"+qiniuUrl4Webp);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(controllerListener)
                .setOldController(ivImage.getController())
                .setLowResImageRequest(ImageRequest.fromUri(Uri.parse(pictureDetail.getFirstFrame())))
                .setUri(UriUtil.parseUriOrNull(qiniuUrl4Webp))
                .setAutoPlayAnimations(false)
                .build();
        ivImage.setController(controller);

        lastIvImage = ivImage;
        lastPictureDetail = pictureDetail;
        lastIvPlayGif = ivPlayGif;
    }


    public void stopGif(Context context) {
        if (lastIvImage != null && lastPictureDetail != null) {
            showFirstFrame(context, lastIvImage, lastPictureDetail);
            lastIvImage = null;
            lastPictureDetail = null;
        }
        if (lastIvPlayGif != null) {
            lastIvPlayGif.setVisibility(View.VISIBLE);
            lastIvPlayGif = null;
        }
    }

    private void showFirstFrame(final Context context, final SimpleDraweeView ivImage, final PictureDetail pictureDetail) {
        /*CustomProgressBarDrawable progressBarDrawable = new CustomProgressBarDrawable();
        progressBarDrawable.setBackgroundColor(context.getResources().getColor(R.color.white_dc_translucence));
        progressBarDrawable.setColor(context.getResources().getColor(R.color.main_yellow_translucence));
        int barWidth = DensityUtils.dp2px(context, 3);
        progressBarDrawable.setBarWidth(barWidth);
        progressBarDrawable.getBounds().bottom = barWidth;

        //获取GenericDraweeHierarchy对象
        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                .setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP)
                .setActualImageFocusPoint(new PointF(0.5f, 0f))
                .setFailureImage(R.drawable.error_reloading)
                .setFailureImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE)
                .setRetryImage(R.drawable.error_reloading)
                .setRetryImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE)
                .setPlaceholderImage(R.drawable.place_holder)
                .setPlaceholderImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE)
                .setProgressBarImage(progressBarDrawable)
                .setOverlay(context.getResources().getDrawable(R.drawable.joke_photo_frame))
                .build();


        //设置Hierarchy
        ivImage.setHierarchy(hierarchy);*/

        ControllerListener<ImageInfo> controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onSubmit(String id, Object callerContext) {
                super.onSubmit(id, callerContext);
            }

            @Override
            public void onFinalImageSet(
                    String id,
                    @Nullable ImageInfo imageInfo,
                    @Nullable Animatable animatable) {

                if (imageInfo == null) {
                    return;
                }

            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
//                BLog.d("Intermediate image received");
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                BLog.e("Error loading %s", throwable.toString());
                pictureDetail.setLoad_status(BizContant.LoadStatus.LOAD_FAIL);
            }
        };


        /*RetainingDataSourceSupplier<CloseableReference<CloseableImage>> retainingSupplier = new RetainingDataSourceSupplier<>();
        retainingSupplier.setSupplier(Fresco.getImagePipeline().getDataSourceSupplier(ImageRequest.fromUri(UriUtil.parseUriOrNull(pictureDetail.getFirstFrame())), null, ImageRequest.RequestLevel.FULL_FETCH));
        PipelineDraweeControllerBuilder builder = Fresco.newDraweeControllerBuilder();
        builder.setControllerListener(controllerListener);
        builder.setOldController(ivImage.getController());
        builder.setDataSourceSupplier(retainingSupplier);
        builder.setAutoPlayAnimations(true);
        ivImage.setController(builder.build());*/

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(controllerListener)
                .setOldController(ivImage.getController())
                .setUri(UriUtil.parseUriOrNull(pictureDetail.getFirstFrame()))
                .setAutoPlayAnimations(true)
                .build();
        ivImage.setController(controller);

    }

    private void reloadGif(SimpleDraweeView ivImage, PictureDetail pictureDetail, View ivPlayGif) {

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(ivImage.getController())
                .setUri(UriUtil.parseUriOrNull(pictureDetail.getOriginImgurl()))
                .setAutoPlayAnimations(true)
                .build();
        ivImage.setController(controller);
        ivPlayGif.setVisibility(View.GONE);

    }
}
