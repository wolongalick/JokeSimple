package com.yyhd.joke.module.joke_detail.view.adapter;

import android.graphics.PointF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.yyhd.joke.simple.R;
import com.yyhd.joke.bean.PictureDetail;
import com.yyhd.joke.module.home.view.adapter.holder.PhotoViewHolder;
import com.yyhd.joke.module.home.view.adapter.holder.photo.PhotoViewHolder4GifMulti;
import com.yyhd.joke.module.home.view.adapter.holder.photo.PhotoViewHolder4GifSingle;
import com.yyhd.joke.module.home.view.adapter.holder.photo.PhotoViewHolder4LongMulti;
import com.yyhd.joke.module.home.view.adapter.holder.photo.PhotoViewHolder4Normal;
import com.yyhd.joke.utils.BizContant;
import com.yyhd.joke.weiget.CustomProgressBarDrawable;

import java.util.List;

import common.base.adapter.BasicRecyclerAdapter;
import common.utils.BLog;
import common.utils.DensityUtils;
import common.utils.ScreenUtils;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/8/4
 * 备注:
 */
public class CommentPhotoAdapter extends BasicRecyclerAdapter<PictureDetail, PhotoViewHolder> {

    private final int viewTypeGifMulti = 1;
    private final int viewTypeLongMulti = 5;
    private final int viewTypeNormal = 6;
    private int width4MultiPhoto;       //多图情况下,每个图片宽度

    private boolean isAtGodCommentView; //是否处于神评论列表中

    public CommentPhotoAdapter(List<PictureDetail> pictureDetails, String joke_id, String jokeCode) {
        super(pictureDetails);
    }

    /**
     * 延迟创建ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public PhotoViewHolder onDelayCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case viewTypeGifMulti:
                return new PhotoViewHolder4GifMulti(LayoutInflater.from(getContext()).inflate(R.layout.item_photo_gif_multi, parent, false));
            case viewTypeLongMulti:
                return new PhotoViewHolder4LongMulti(LayoutInflater.from(getContext()).inflate(R.layout.item_photo_long_multi, parent, false));
            default:
                return new PhotoViewHolder4Normal(LayoutInflater.from(getContext()).inflate(R.layout.item_photo_normal, parent, false));
        }
    }

    @Override
    protected int getItemViewTypeDelay(int position) {
        PictureDetail pictureDetail = getData().get(position);
        if (pictureDetail.isGif()) {
            return viewTypeGifMulti;
        } else if (pictureDetail.isLongPic()) {
            return viewTypeLongMulti;
        } else {
            return viewTypeNormal;
        }
    }

    /**
     * 通过数据模型填充视图
     *
     * @param holder
     * @param pictureDetail
     * @param position
     */
    @Override
    public void onFillViewByModel(final PhotoViewHolder holder, final PictureDetail pictureDetail, final int position) {
        onFillViewByModel4Manual(holder, pictureDetail, position, false);
    }

    public void onFillViewByModel4Manual(final PhotoViewHolder holder, final PictureDetail pictureDetail, final int position, boolean isReloadSingleGif) {
        int viewType = getItemViewTypeDelay(position);
        adjust(holder);
        switch (viewType) {
            case viewTypeGifMulti:
                showImage4GifMulti((PhotoViewHolder4GifMulti) holder, pictureDetail);
                break;
            case viewTypeLongMulti:
                showImage4LongMulti((PhotoViewHolder4LongMulti) holder, pictureDetail);
                break;
            default:
                showImage4Normal((PhotoViewHolder4Normal) holder, pictureDetail,isReloadSingleGif);

        }
        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(holder, pictureDetail, position);
            }
        });
    }

    /**
     * 调整图片尺寸
     * @param holder
     */
    private void adjust(PhotoViewHolder holder) {
        calculatePhotoWidth();
        ViewGroup.LayoutParams rootLayoutParams = holder.itemView.getLayoutParams();
        rootLayoutParams.width = width4MultiPhoto;
        rootLayoutParams.height = width4MultiPhoto;
    }

    private void calculatePhotoWidth() {
        if(width4MultiPhoto==0){
            int screenWidth = ScreenUtils.getScreenWidth(getContext());
            width4MultiPhoto = (screenWidth
                    - DensityUtils.dp2px(getContext(), 54)
                    - getContext().getResources().getDimensionPixelOffset(R.dimen.detail_paddingLeftRight)
                    - getContext().getResources().getDimensionPixelOffset(R.dimen.published_comment_photo_space) * 2)
                    / 3;
        }
    }

    /**
     * 显示多图模式下的gif图片
     *
     * @param holder
     * @param model
     */
    private void showImage4GifMulti(PhotoViewHolder4GifMulti holder, final PictureDetail model) {
        showImage(holder, model, model.getFirstFrame());
    }

    /**
     * 显示长图片
     *
     * @param holder
     * @param model
     */
    private void showImage4LongMulti(PhotoViewHolder4LongMulti holder, final PictureDetail model) {
        showImage(holder, model, model.getCutUrl());
    }

    /**
     * 显示普通图片
     *
     * @param holder
     * @param model
     */
    private void showImage4Normal(PhotoViewHolder4Normal holder, final PictureDetail model, boolean isReloadSingleGif) {
        showImage(holder, model, model.isPiiic() ? model.getThumbnail() : model.getQiniuUrl(),isReloadSingleGif);
    }

    private void showImage(final PhotoViewHolder holder, final PictureDetail model, String firstUrl) {
        showImage(holder, model, firstUrl, false);
    }

    private void showImage(final PhotoViewHolder holder, final PictureDetail pictureDetail, String firstUrl, final boolean isReloadSingleGif) {
        CustomProgressBarDrawable progressBarDrawable = new CustomProgressBarDrawable();
        progressBarDrawable.setBackgroundColor(getContext().getResources().getColor(R.color.white_dc_translucence));
        progressBarDrawable.setColor(getContext().getResources().getColor(R.color.main_yellow_translucence));
        int barWidth = DensityUtils.dp2px(getContext(), 3);
        progressBarDrawable.setBarWidth(barWidth);
        progressBarDrawable.getBounds().bottom = barWidth;

        //获取GenericDraweeHierarchy对象
        GenericDraweeHierarchyBuilder hierarchyBuilder = GenericDraweeHierarchyBuilder.newInstance(getContext().getResources());

        //设置圆角半径
        hierarchyBuilder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
        hierarchyBuilder.setActualImageFocusPoint(new PointF(0.5f, 0f));
        hierarchyBuilder.setFailureImage(R.drawable.error_reloading);
        hierarchyBuilder.setFailureImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);
        hierarchyBuilder.setRetryImage(R.drawable.error_reloading);
        hierarchyBuilder.setRetryImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);

        if(isReloadSingleGif){
            FileBinaryResource resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainFileCache().getResource(new SimpleCacheKey(pictureDetail.getFirstFrame()));
            if (resource!=null && resource.getFile() != null && resource.getFile().exists()) {
                hierarchyBuilder.setPlaceholderImage(Drawable.createFromPath(resource.getFile().toString()));
                hierarchyBuilder.setPlaceholderImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP);
            }else {
                hierarchyBuilder.setPlaceholderImage(getContext().getResources().getDrawable(R.drawable.place_holder));
                hierarchyBuilder.setPlaceholderImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);
            }
        }else {
            hierarchyBuilder.setPlaceholderImage(getContext().getResources().getDrawable(R.drawable.place_holder));
            hierarchyBuilder.setPlaceholderImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);
        }

        hierarchyBuilder.setProgressBarImage(progressBarDrawable);
        hierarchyBuilder.setOverlay(getContext().getResources().getDrawable(R.drawable.joke_photo_frame));

        GenericDraweeHierarchy hierarchy = hierarchyBuilder.build();

        //设置Hierarchy
        holder.ivImage.setHierarchy(hierarchy);

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

                if (!isReloadSingleGif && (holder instanceof PhotoViewHolder4GifSingle)) {

                } else {
                    pictureDetail.setLoad_status(BizContant.LoadStatus.LOAD_SUCCES);
                }

                try {
                    if (isReloadSingleGif && holder instanceof PhotoViewHolder4GifSingle) {
                        ((PhotoViewHolder4GifSingle) holder).ivPlayGif.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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



        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(UriUtil.parseUriOrNull(isReloadSingleGif ? pictureDetail.getQiniuUrl() : firstUrl))
                .setResizeOptions(new ResizeOptions(width4MultiPhoto,width4MultiPhoto))
                //缩放,在解码前修改内存中的图片大小, 配合Downsampling可以处理所有图片,否则只能处理jpg,
                // 开启Downsampling:在初始化时设置.setDownsampleEnabled(true)
                .build();

        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setControllerListener(controllerListener)
                .setImageRequest(request)
                .setOldController(holder.ivImage.getController())
                .setAutoPlayAnimations(true)
                .build();



        holder.ivImage.setController(controller);
    }

    public void setAtGodCommentView(boolean atGodCommentView) {
        isAtGodCommentView = atGodCommentView;
    }
}
