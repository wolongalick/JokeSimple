package com.yyhd.joke.module.home.view.adapter;

import android.graphics.drawable.Animatable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.yyhd.joke.bean.PictureDetail;
import com.yyhd.joke.event.DownloadGifEvent;
import com.yyhd.joke.module.home.view.adapter.holder.PhotoViewHolder;
import com.yyhd.joke.module.home.view.adapter.holder.photo.PhotoViewHolder4GifMulti;
import com.yyhd.joke.module.home.view.adapter.holder.photo.PhotoViewHolder4GifSingle;
import com.yyhd.joke.module.home.view.adapter.holder.photo.PhotoViewHolder4GifSingle_Detail;
import com.yyhd.joke.module.home.view.adapter.holder.photo.PhotoViewHolder4LongMulti;
import com.yyhd.joke.module.home.view.adapter.holder.photo.PhotoViewHolder4LongSingle;
import com.yyhd.joke.module.home.view.adapter.holder.photo.PhotoViewHolder4Normal;
import com.yyhd.joke.simple.R;
import com.yyhd.joke.utils.BizContant;

import java.util.List;

import common.base.adapter.BasicRecyclerAdapter;
import common.utils.BLog;
import common.utils.DialogUtils;
import common.utils.NetConnectionUtils;
import common.utils.RxBus;
import common.utils.ScreenUtils;
import common.utils.T;
import io.reactivex.disposables.Disposable;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/8/4
 * 备注:
 */
public class PhotoAdapter extends BasicRecyclerAdapter<PictureDetail, PhotoViewHolder> {
    private static final String TAG = "PhotoAdapter";
    private int screenWidth;
    private int photoGridSpanCount;
    private String joke_id;
    private String jokeCode;
    private boolean isJokeDetail;

    private final int viewTypeGifMulti = 1;
    private final int viewTypeGifSingle = 2;
    private final int viewTypeGifSingle_detail = 3;
    private final int viewTypeLongSingle = 4;
    private final int viewTypeLongMulti = 5;
    private final int viewTypeNormal = 6;
    private int width4MultiPhoto;       //多图情况下,每个图片宽度

    private OnSavePhotoListener onSavePhotoListener;

    private int home_paddingLeftRight;
    private int home_spacing;
    private int progressBarBackgroundColor;
    private int progressBarColor;

    public interface OnSavePhotoListener {
        void onSavePhotoSuccess();
    }

    public PhotoAdapter(List<PictureDetail> pictureDetails, String joke_id, String jokeCode) {
        super(pictureDetails);
        this.joke_id = joke_id;
        this.jokeCode = jokeCode;
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
        initParams();


        switch (viewType) {
            case viewTypeGifSingle:
                return new PhotoViewHolder4GifSingle(LayoutInflater.from(getContext()).inflate(R.layout.item_photo_gif_single, parent, false));
            case viewTypeGifSingle_detail:
                return new PhotoViewHolder4GifSingle_Detail(LayoutInflater.from(getContext()).inflate(R.layout.item_photo_gif_single_detail, parent, false));
            case viewTypeGifMulti:
                return new PhotoViewHolder4GifMulti(LayoutInflater.from(getContext()).inflate(R.layout.item_photo_gif_multi, parent, false));
            case viewTypeLongSingle:
                return new PhotoViewHolder4LongSingle(LayoutInflater.from(getContext()).inflate(R.layout.item_photo_long_single, parent, false));
            case viewTypeLongMulti:
                return new PhotoViewHolder4LongMulti(LayoutInflater.from(getContext()).inflate(R.layout.item_photo_long_multi, parent, false));
            default:
                return new PhotoViewHolder4Normal(LayoutInflater.from(getContext()).inflate(R.layout.item_photo_normal, parent, false));
        }
    }

    @Override
    protected int getItemViewTypeDelay(int position) {
        PictureDetail pictureDetail = getData().get(position);
        if (photoGridSpanCount == BizContant.PhotoGridSpanCount.SINGLE) {
            if (pictureDetail.isGif()) {
                return isJokeDetail ? viewTypeGifSingle_detail : viewTypeGifSingle;
            } else if (pictureDetail.isLongPic()) {
                return viewTypeLongSingle;
            } else {
                return viewTypeNormal;
            }
        } else {
            if (pictureDetail.isGif()) {
                return viewTypeGifMulti;
            } else if (pictureDetail.isLongPic()) {
                return viewTypeLongMulti;
            } else {
                return viewTypeNormal;
            }
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
        final int viewType = getItemViewTypeDelay(position);
        adjust(holder, pictureDetail);
        switch (viewType) {
            case viewTypeGifSingle:
                showImage4GifSingle((PhotoViewHolder4GifSingle) holder, pictureDetail, isReloadSingleGif, position);
                break;
            case viewTypeGifSingle_detail:
                showImage4GifSingle_Detail((PhotoViewHolder4GifSingle_Detail) holder, pictureDetail, isReloadSingleGif, position);
                break;
            case viewTypeGifMulti:
                showImage4GifMulti((PhotoViewHolder4GifMulti) holder, pictureDetail, position);
                break;
            case viewTypeLongSingle:
                showImage4LongSingle((PhotoViewHolder4LongSingle) holder, pictureDetail, position);
                break;
            case viewTypeLongMulti:
                showImage4LongMulti((PhotoViewHolder4LongMulti) holder, pictureDetail, position);
                break;
            default:
                showImage4Normal((PhotoViewHolder4Normal) holder, pictureDetail, isReloadSingleGif, position);

        }
        if (viewType == viewTypeGifSingle) {
            return;
        }

        if (!holder.ivImage.hasOnClickListeners()) {
            holder.ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(holder, pictureDetail, position);
                }
            });
        }
    }

    private void adjust(PhotoViewHolder holder, PictureDetail pictureDetail) {
        if (screenWidth == 0) {
            screenWidth = ScreenUtils.getScreenWidth(getContext());
        }


        if (isMultiPhoto()) {
            //1.多图时,每个图片都是正方形
            if (width4MultiPhoto == 0) {
                width4MultiPhoto = (screenWidth
                        - home_paddingLeftRight * 2
                        - home_spacing * (photoGridSpanCount - 1))
                        / photoGridSpanCount;
            }
            ViewGroup.LayoutParams rootLayoutParams = holder.itemView.getLayoutParams();
            rootLayoutParams.width = width4MultiPhoto;
            rootLayoutParams.height = width4MultiPhoto;
//            ((GridLayoutManager.LayoutParams) rootLayoutParams).setMargins(0, 0, 0, DensityUtils.dp2px(getContext(), 6));
        } else if (pictureDetail.isLongPic()) {
            ViewGroup.LayoutParams layoutParams = holder.ivImage.getLayoutParams();
            //2.长图时,图片拉伸到屏幕宽度(需要留出两边边距),宽高比为5:4
            layoutParams.width = screenWidth - 2 * home_paddingLeftRight;
            layoutParams.height = layoutParams.width * 4 / 5;
        } else {
            ViewGroup.LayoutParams layoutParams = holder.ivImage.getLayoutParams();
            //3.默认情况,图片拉伸到屏幕宽度(需要留出两边边距),宽高比为图片原始比例
            layoutParams.width = screenWidth - 2 * home_paddingLeftRight;
            layoutParams.height = (int) ((float) (layoutParams.width * pictureDetail.getHeight()) / (float) pictureDetail.getWidth());
        }
    }

    /**
     * 显示单图模式下的gif图片(首页列表界面)
     *
     * @param holder
     * @param model
     * @param isReloadSingleGif
     */
    private void showImage4GifSingle(final PhotoViewHolder4GifSingle holder, final PictureDetail model, boolean isReloadSingleGif, final int position) {
        showImage(holder, model, model.getFirstFrame(), isReloadSingleGif, position);
    }

    /**
     * 显示单图模式下的gif图片(段子详情界面)
     *
     * @param holder
     * @param model
     * @param isReloadSingleGif
     */
    private void showImage4GifSingle_Detail(final PhotoViewHolder4GifSingle_Detail holder, final PictureDetail model, boolean isReloadSingleGif, int position) {

        //详情页可以单机单张gif图片右下角的按钮下载图片
        if (!holder.ivDownloadGif.hasOnClickListeners()) {
            holder.ivDownloadGif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prepareSaveGifPhoto(model.getOriginImgurl(), holder.ivDownloadGif);
                }
            });
        }
        showImage(holder, model, model.getFirstFrame(), isReloadSingleGif, position);
    }

    /**
     * 显示多图模式下的gif图片
     *
     * @param holder
     * @param model
     */
    private void showImage4GifMulti(PhotoViewHolder4GifMulti holder, final PictureDetail model, final int position) {
        showImage(holder, model, model.getFirstFrame(), position);
    }

    /**
     * 显示长图片
     *
     * @param holder
     * @param model
     */
    private void showImage4LongSingle(PhotoViewHolder4LongSingle holder, final PictureDetail model, final int position) {
        showImage(holder, model, model.getCutUrl(), position);
    }

    /**
     * 显示长图片
     *
     * @param holder
     * @param model
     */
    private void showImage4LongMulti(PhotoViewHolder4LongMulti holder, final PictureDetail model, final int position) {
        showImage(holder, model, model.getCutUrl(), position);
    }

    /**
     * 显示普通图片
     *
     * @param holder
     * @param model
     */
    private void showImage4Normal(PhotoViewHolder4Normal holder, final PictureDetail model, boolean isReloadSingleGif, int position) {
        showImage(holder, model, model.isPiiic() ? model.getThumbnail() : model.getQiniuUrl(), isReloadSingleGif, position);
    }

    private void showImage(final PhotoViewHolder holder, final PictureDetail model, String firstUrl, int position) {
        showImage(holder, model, firstUrl, false, position);
    }

    private void showImage(final PhotoViewHolder holder, final PictureDetail pictureDetail, String firstUrl, final boolean isReloadSingleGif, final int position) {

//        CustomProgressBarDrawable progressBarDrawable = new CustomProgressBarDrawable();
//
//        progressBarDrawable.setBackgroundColor(progressBarBackgroundColor);
//
//        progressBarDrawable.setColor(progressBarColor);
//        int barWidth = DensityUtils.dp2px(getContext(), 3);
//        progressBarDrawable.setBarWidth(barWidth);
//        progressBarDrawable.getBounds().bottom = barWidth;

//        //获取GenericDraweeHierarchy对象
//        GenericDraweeHierarchyBuilder hierarchyBuilder = GenericDraweeHierarchyBuilder.newInstance(getContext().getResources());
//
//        //设置圆角半径
//        hierarchyBuilder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
//        hierarchyBuilder.setActualImageFocusPoint(new PointF(0.5f, 0f));
//        hierarchyBuilder.setFailureImage(R.drawable.error_reloading);
//        hierarchyBuilder.setFailureImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);
//        hierarchyBuilder.setRetryImage(R.drawable.error_reloading);
//        hierarchyBuilder.setRetryImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);
//
//        if (isReloadSingleGif) {
//            FileBinaryResource resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainFileCache().getResource(new SimpleCacheKey(pictureDetail.getFirstFrame()));
//            if (resource != null && resource.getFile() != null && resource.getFile().exists()) {
//                hierarchyBuilder.setPlaceholderImage(Drawable.createFromPath(resource.getFile().toString()));
//                hierarchyBuilder.setPlaceholderImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP);
//            } else {
//                hierarchyBuilder.setPlaceholderImage(getContext().getResources().getDrawable(R.drawable.place_holder));
//                hierarchyBuilder.setPlaceholderImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);
//            }
//        } else {
//            hierarchyBuilder.setPlaceholderImage(getContext().getResources().getDrawable(R.drawable.place_holder));
//            hierarchyBuilder.setPlaceholderImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);
//        }
//
////        hierarchyBuilder.setProgressBarImage(progressBarDrawable);
//
//        hierarchyBuilder.setOverlay(getContext().getResources().getDrawable(R.drawable.joke_photo_frame));
//
//        //设置Hierarchy
//        holder.ivImage.setHierarchy(hierarchyBuilder.build());


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
                //不是重新加载单张gif,且是单张gif的holder
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
                holder.ivImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClick(holder, pictureDetail, position);
                    }
                });

            }
        };

        /*if (pictureDetail.isGif() && (!isMultiPhoto())) {
            RetainingDataSourceSupplier<CloseableReference<CloseableImage>> retainingSupplier = new RetainingDataSourceSupplier<>();
            retainingSupplier.setSupplier(Fresco.getImagePipeline().getDataSourceSupplier(ImageRequest.fromUri(UriUtil.parseUriOrNull(isReloadSingleGif ? pictureDetail.getQiniuUrl() : firstUrl)), null, ImageRequest.RequestLevel.FULL_FETCH));

            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setControllerListener(controllerListener)
                    .setDataSourceSupplier(retainingSupplier)
                    .setAutoPlayAnimations(true)
                    .build();

            holder.ivImage.setController(controller);
        } else {*/

            if (isMultiPhoto()) {

            }

            final int photoGridSpanCount=3;
            //3.默认情况,图片拉伸到屏幕宽度(需要留出两边边距),宽高比为图片原始比例
            int resizeWidth =  (screenWidth
                    - home_paddingLeftRight * 2
                    - home_spacing * (photoGridSpanCount - 1))
                    / photoGridSpanCount;
            int resizeHeight = resizeWidth;

            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(UriUtil.parseUriOrNull(isReloadSingleGif ? pictureDetail.getQiniuUrl() : firstUrl))
                    .setResizeOptions(new ResizeOptions(20,20))
                    //缩放,在解码前修改内存中的图片大小, 配合Downsampling可以处理所有图片,否则只能处理jpg,
                    // 开启Downsampling:在初始化时设置.setDownsampleEnabled(true)
                    .build();

            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setControllerListener(controllerListener)
//                    .setOldController(holder.ivImage.getController())
                    .setUri(UriUtil.parseUriOrNull(isReloadSingleGif ? pictureDetail.getQiniuUrl() : firstUrl))
                    .build();

            holder.ivImage.setController(controller);
//        }


    }


    private void playGif(Animatable animatable) {
        animatable.start();
    }

    private void stopGif(Animatable animatable) {
        animatable.stop();
    }

    public void setPhotoGridSpanCount(int photoGridSpanCount) {
        this.photoGridSpanCount = photoGridSpanCount;
    }

    public String getJoke_id() {
        return joke_id;
    }

    public String getJokeCode() {
        return jokeCode;
    }

    private void prepareSaveGifPhoto(final String imgUrl, final ImageView ivDownloadGif) {
        RxBus.getInstance().subscibe(new RxBus.OnReceivedListener<DownloadGifEvent>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onReceived(DownloadGifEvent downloadGifEvent) {
                saveGifPhoto(imgUrl, ivDownloadGif);
            }
        }, true);

    }

    public void saveGifPhoto(final String imgUrl, final ImageView ivDownloadGif) {
        switch (NetConnectionUtils.getNetworkName(getContext())) {
            case NetConnectionUtils.NetWorkType.NET_NONE:
                break;
            case NetConnectionUtils.NetWorkType.NET_2G:
            case NetConnectionUtils.NetWorkType.NET_3G:
            case NetConnectionUtils.NetWorkType.NET_4G:
                DialogUtils.showCustomMessageDialog(getContext(), "您当前处于移动网络，保存GIF图片消耗流量较多，是否保存？", "取消", "保存", new DialogUtils.OnButtonClickListener() {
                    @Override
                    public void onConfirmButtonClick() {
                        doSaveGifPhoto(imgUrl, ivDownloadGif);
                    }
                });
                break;
            default:
                doSaveGifPhoto(imgUrl, ivDownloadGif);
        }
    }

    public void doSaveGifPhoto(final String imgUrl, final ImageView ivDownloadGif) {
        ivDownloadGif.setClickable(false);
        T.show(getContext(), "禁止下载");
    }

    public void setJokeDetail(boolean jokeDetail) {
        isJokeDetail = jokeDetail;
    }

    /**
     * 是否为多张图片
     *
     * @return
     */
    private boolean isMultiPhoto() {
        return photoGridSpanCount != BizContant.PhotoGridSpanCount.SINGLE;
    }

    public void setOnSavePhotoListener(OnSavePhotoListener onSavePhotoListener) {
        this.onSavePhotoListener = onSavePhotoListener;
    }


    private void initParams() {
        if (home_paddingLeftRight == 0) {
            home_paddingLeftRight = getContext().getResources().getDimensionPixelOffset(R.dimen.home_paddingLeftRight);
        }

        if (home_spacing == 0) {
            home_spacing = getContext().getResources().getDimensionPixelOffset(R.dimen.home_spacing);
        }

        if (progressBarBackgroundColor == 0) {
            progressBarBackgroundColor = getContext().getResources().getColor(R.color.white_dc_translucence);
        }

        if (progressBarColor == 0) {
            progressBarColor = getContext().getResources().getColor(R.color.main_yellow_translucence);
        }
    }

}
