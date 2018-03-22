package com.yyhd.joke.module.home.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;
import com.yyhd.joke.base.BaseSGListFragment_v4;
import com.yyhd.joke.bean.DataAllBean;
import com.yyhd.joke.bean.GifWrapData;
import com.yyhd.joke.bean.WrapData;
import com.yyhd.joke.module.home.presenter.JokePresenter;
import com.yyhd.joke.module.home.view.HomeFragment;
import com.yyhd.joke.module.home.view.IJokeView;
import com.yyhd.joke.module.home.view.adapter.BaseHomeAdapter;
import com.yyhd.joke.module.home.view.adapter.holder.BaseHomeViewHolder;
import com.yyhd.joke.simple.R;
import com.yyhd.joke.utils.BizContant;
import com.yyhd.joke.utils.BizUtils;
import com.yyhd.joke.utils.IntentKey;
import com.yyhd.joke.utils.JokePhotoUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.bean.ErrorMsg;
import common.ui.PullLoadRecyclerView;
import common.ui.datacontent.LoadingFrameLayout;
import common.utils.BLog;
import common.utils.DataUtils;
import common.utils.DensityUtils;
import common.utils.ScreenUtils;
import common.utils.T;


/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/8/4
 * 备注:
 */
public class BaseHomeFragment
        <Model extends DataAllBean
                , Holder extends BaseHomeViewHolder
                , Adapter extends BaseHomeAdapter<Model, Holder>> extends BaseSGListFragment_v4<Model, Holder, Adapter,IJokeView,JokePresenter>
        implements IJokeView {
    private static final String TAG = "BaseHomeFragment";
    @BindView(R.id.tvTips)
    TextView tvTips;
    private String jokeCode;

    private final int msg_type_wrapData = 1;
    private final int msg_type_tips = 2;
    private final int msg_type_play_gif = 3;
    private final int msg_type_lp = 4;

//    private Subscription updateCommentNumSubscription;
    private List<DataAllBean> lastDataList = new ArrayList<>();
    private boolean isLoaded;//是否已经预加在过

    private JokePhotoUtils jokePhotoUtils;

    private int centerLine;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case msg_type_wrapData:
                    WrapData wrapData = (WrapData) msg.obj;
                    updateData(wrapData.getLoadDataOperate(), wrapData.getHomeBeanList(), wrapData.getDiffTime());
                    break;
                case msg_type_tips:
                    tvTips.setVisibility(View.GONE);
                    break;
                case msg_type_play_gif:
                    prePlayGif();
                    break;
                case msg_type_lp:
                    break;
            }
        }
    };

    public BaseHomeFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
        BLog.i(TAG, "BaseHomeFragment->onResume()");

        if(isAtCurrentPage(getHomeFragment())){
        }
    }

    /**
     * 初始化变量
     */
    @Override
    public void initParmers() {
        Bundle bundle = getArguments();
        jokeCode = bundle.getString(IntentKey.JOKE_CODE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_content;
    }

    /**
     * 初始化页面控件
     */
    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        ((FrameLayout.LayoutParams) tvTips.getLayoutParams()).setMargins(0, DensityUtils.dp2px(getHostActivity(), 10), 0, 0);
    }

    /**
     * 初始化页面控件的值以及设置控件监听
     */
    @Override
    public void initValues() {

        getAdapter().setSupportLoadmore(true);

        LoadingFrameLayout loadingContent = getSimpleGlobalFrameLayout2().getLoadingContent();
        loadingContent.setPadding(0, 0, 0, DensityUtils.dp2px(getContext(), 45));

        showLoadingView();
        getSwipeRefreshLayout().setRefreshing(true);

        getAdapter().setOnClickLastRefreshListener(new BaseHomeAdapter.OnClickLastRefreshListener() {
            @Override
            public void onClickLastRefresh() {
                getSwipeRefreshLayout().setRefreshing(true);
                getPullLoadRecyclerView().getLayoutManager().scrollToPosition(0);
                refreshByLastRefresh();
            }
        });

        getAdapter().setOnClickJokeListener(new BaseHomeAdapter.OnClickJokeListener<DataAllBean>() {
            /**
             * 点击段子的监听
             * @param homeBean
             * @param position         点击的索引
             * @param isToCommentPlace 是否滚动到评论区域
             */
            @Override
            public void onClickJoke(DataAllBean homeBean, int position, boolean isToCommentPlace) {
                gotoJokeDetailActivity(homeBean, position, isToCommentPlace);
            }

            /**
             * 点赞
             *
             * @param dataAllBean
             */
            @Override
            public void onClickDigg(DataAllBean dataAllBean) {
                getPresenter().diggJoke(dataAllBean.getId());
            }

            /**
             * 点踩
             *
             * @param dataAllBean
             */
            @Override
            public void onClickBury(DataAllBean dataAllBean) {
                getPresenter().buryJoke(dataAllBean.getId());
            }

            /**
             * 分享
             *
             * @param dataAllBean
             */
            @Override
            public void onClickShare(DataAllBean dataAllBean) {
                getPresenter().shareJoke(dataAllBean.getId());
            }

            /**
             * 对评论点赞
             *
             * @param commentId
             */
            @Override
            public void onClickDiggComment(String commentId) {
                getPresenter().diggComment(commentId);
            }

            /**
             * 不喜欢某个段子
             *
             * @param dataAllBean
             */
            @Override
            public void onDislikeJoke(DataAllBean dataAllBean) {
                //在这里可以调接口告诉服务器:用户不喜欢哪个段子
                T.show(getContext(), "以后将减少推荐相似内容");
            }
        });

        getAdapter().setOnShareListener(new BaseHomeAdapter.OnShareModelListener() {
            @Override
            public void onStartShare(DataAllBean dataAllBean) {
            }
        });

//        getPullLoadRecyclerView().addItemDecoration(new NormalLLRVDecoration(getContext().getResources().getDimensionPixelOffset(R.dimen.home_divider_height),getContext().getResources().getDrawable(R.drawable.divider_list_home_12dp)));

//        getPullLoadRecyclerView().setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        getPullLoadRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        prePlayGif();
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }
        });

        doRefresh();

        centerLine = calculate();
        BLog.i("centerLine=" + centerLine);
    }

    /**
     * 准备播放gif
     */
    public void prePlayGif() {
        try {
            if (DataUtils.isEmpty(getAllDataList()) || (getHomeFragment()).isShowingNewbieGuide()) {
                return;
            }
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getPullLoadRecyclerView().getLayoutManager();

            int firstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
            //1.有完整显示的item,且该item只有一个单张gif图
            if (firstCompletelyVisibleItemPosition != -1) {
                if (firstCompletelyVisibleItemPosition >= getAllDataList().size()) {
                    return;
                }
                Model model = getAllDataList().get(firstCompletelyVisibleItemPosition);
                if (isSingleGif(model)) {
                    playGif(firstCompletelyVisibleItemPosition);
                    return;
                }
            }

            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();

            if (firstVisibleItemPosition == -1) {
                return;
            }

            //2.单张gif铺满并超出了整个RecyclerView的高度
            if (firstVisibleItemPosition == lastVisibleItemPosition) {
                //2.1.如果是gif就播放,
                if (firstVisibleItemPosition >= getAllDataList().size()) {
                    return;
                }
                Model model = getAllDataList().get(firstVisibleItemPosition);
                if (isSingleGif(model)) {
                    playGif(firstVisibleItemPosition);
                }
                //2.2.否则不做任何处理
                return;
            }

            List<GifWrapData> gifWrapDataList = new ArrayList<>();

            for (int index = firstVisibleItemPosition; index <= lastVisibleItemPosition; index++) {
                if (index >= getAllDataList().size()) {
                    return;
                }
                Model model = getAllDataList().get(index);
                if (isSingleGif(model)) {
                    //这个holder包含了作者、标题、内容和内层 的RecyclerView
                    RecyclerView.ViewHolder viewHolder = getPullLoadRecyclerView().findViewHolderForAdapterPosition(index);
                    RecyclerView childRecyclerView = viewHolder.itemView.findViewById(R.id.rv_image);
                    final RecyclerView.ViewHolder childViewHolder = childRecyclerView.findViewHolderForAdapterPosition(0);
                    SimpleDraweeView iv_image = childViewHolder.itemView.findViewById(R.id.iv_image);
                    View ivPlayGif = childViewHolder.itemView.findViewById(R.id.iv_playGif);
                    float visiblePercent = BizUtils.getVisiblePercent(iv_image);
                    gifWrapDataList.add(new GifWrapData(visiblePercent, iv_image, ivPlayGif, model.getPictureDetails().get(0)));
                }
            }

            if (DataUtils.isEmpty(gifWrapDataList)) {
                return;
            }

            GifWrapData gifWrapDatal4visiblePercent = null;
            //3.播放单张gif
            if (gifWrapDataList.size() == 1) {
                if (jokePhotoUtils == null) {
                    jokePhotoUtils = new JokePhotoUtils();
                }
                gifWrapDatal4visiblePercent = gifWrapDataList.get(0);
                jokePhotoUtils.showGif(getContext(), gifWrapDatal4visiblePercent.getIv_image(), gifWrapDatal4visiblePercent.getIvPlayGif(), gifWrapDatal4visiblePercent.getPictureDetail());
                return;
            }

            //4.比较出高度显示百分比最多的条目,然后播放
            for (GifWrapData gifWrapData : gifWrapDataList) {
                if (gifWrapDatal4visiblePercent == null) {
                    gifWrapDatal4visiblePercent = gifWrapData;
                }
                if (gifWrapData.getVisiblePercent() > gifWrapDatal4visiblePercent.getVisiblePercent()) {
                    gifWrapDatal4visiblePercent = gifWrapData;
                }
            }
            if (jokePhotoUtils == null) {
                jokePhotoUtils = new JokePhotoUtils();
            }
            jokePhotoUtils.showGif(getContext(), gifWrapDatal4visiblePercent.getIv_image(), gifWrapDatal4visiblePercent.getIvPlayGif(), gifWrapDatal4visiblePercent.getPictureDetail());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean isSingleGif(Model model) {
        return model != null && model.getPictureDetails() != null && model.getPictureDetails().size() == 1 && model.getPictureDetails().get(0).isGif();
    }

    private void playGif(int position) {
        //这个holder包含了作者、标题、内容和内层的RecyclerView
        RecyclerView.ViewHolder viewHolder = getPullLoadRecyclerView().findViewHolderForAdapterPosition(position);
        RecyclerView childRecyclerView = viewHolder.itemView.findViewById(R.id.rv_image);
        final RecyclerView.ViewHolder childViewHolder = childRecyclerView.findViewHolderForAdapterPosition(0);
        SimpleDraweeView iv_image = childViewHolder.itemView.findViewById(R.id.iv_image);
        View ivPlayGif = childViewHolder.itemView.findViewById(R.id.iv_playGif);
        if (jokePhotoUtils == null) {
            jokePhotoUtils = new JokePhotoUtils();
        }
        jokePhotoUtils.showGif(getContext(), iv_image, ivPlayGif, getAllDataList().get(position).getPictureDetails().get(0));
    }



    private void gotoJokeDetailActivity4Log_lp(final DataAllBean homeBean, final int position, boolean isToCommentPlace,String jokeId){

    }


    private void gotoJokeDetailActivity(final DataAllBean homeBean, final int position, boolean isToCommentPlace) {
        T.show(getContext(),"进不去");
    }

    /**
     * 在onDestory()手动释放资源
     */
    @Override
    public void releaseOnDestory() {

    }

    /**
     * 回调函数:通知client端刷新列表
     */
    @Override
    public void onRefresh() {
        doRefresh();
    }

    public void refreshByLastRefresh() {
        doRefresh();
    }

    public void refreshByCircleBtn() {
        doRefresh();
    }

    private void doRefresh() {
        loadData(BizContant.LoadDataOperate.REFRESH);
    }


    /**
     * 回调函数:通知client端加载更多数据
     *
     * @param pageNum
     */
    @Override
    public void onLoadMore(int pageNum) {
        loadData(BizContant.LoadDataOperate.LOADMORE);
    }

    /**
     * 获取段子集合成功的回调函数
     *
     * @param data
     * @param loadDataOperate
     */
    @Override
    public void onGetJokesSuccess(List<DataAllBean> data, String loadDataOperate, long diffTime) {
        //无论刷新还是加载更多结束,都立刻预加载
//        CacheUtils.loadData(jokeCode, getHostActivity());
        if (diffTime > BizContant.MIN_LOAD_DATA_DURATION) {
            updateData(loadDataOperate, data, diffTime);
        } else {
            Message message = handler.obtainMessage();
            message.what = msg_type_wrapData;
            message.obj = new WrapData(loadDataOperate, data, BizContant.MIN_LOAD_DATA_DURATION);
            handler.sendMessageDelayed(message, BizContant.MIN_LOAD_DATA_DURATION - diffTime);
        }
    }


    /**0
     * 获取段子集合失败的回调函数
     *
     * @param errorMsg
     */
    @Override
    public void onGetJokesFail(ErrorMsg errorMsg, String loadDataOperate, long diffTime, List<DataAllBean> cacheList) {
        resetView();
        showRefreshTips(errorMsg.getMsg());
        if (errorMsg.getMsg().contains("下拉刷新试试")) {
            getAdapter().noMoreData();
            getPullLoadRecyclerView().loadmoreFinish(PullLoadRecyclerView.NO_MORE_DATA_NEXT_PAGE);
        }
        if (loadDataOperate.equals(BizContant.LoadDataOperate.REFRESH)) {
            if (DataUtils.isEmpty(getAllDataList())) {
                if(!DataUtils.isEmpty(cacheList)){
                    //1.如果有缓存,则使用缓存的
//                    T.showLong(getContext(),"网络加载失败,缓存中有数据");//test
                    updateData(loadDataOperate, cacheList, diffTime);
                }else {
                    //2.否则再展示加载失败页面
//                    T.showLong(getContext(),"网络加载失败,缓存中也没数据");//test
                    getSimpleGlobalFrameLayout2().showFailView();
                }
            }
        } else {
            getPullLoadRecyclerView().setLoadMoreFinish();
        }
    }

    public void loadData4log(List<DataAllBean> data, long diffTime) {
        //此函数体中不需要写具体代码,因为该函数是给日志系统用的
    }

    public void loadData4award(Context context) {
        //此处不需要函数的具体代码
    }

    private void updateData(String loadDataOperate, List<DataAllBean> data, long diffTime) {
        if (((HomeFragment) getParentFragment()).getCurrentJokeType().equals(jokeCode)) {
            loadData4log(data, diffTime);
        }

        loadData4award(getContext());

        resetView();

        //1.刷新后
        if (loadDataOperate.equals(BizContant.LoadDataOperate.REFRESH)) {
            //1.1无数据
            if (DataUtils.isEmpty(data)) {
                if (!DataUtils.isEmpty(getAllDataList())) {
                    showRefreshTips("内容刷光啦，先去其他频道看看吧");
                    updateLastJokeId(data);
                    getAdapter().notifyDataSetChanged();
                    handler.sendEmptyMessageDelayed(msg_type_play_gif, 500);
                } else {
                    loadData(BizContant.LoadDataOperate.LOADMORE);
                }
                //1.2有数据
            } else {
                updateLastJokeId(data);
                addTop(data);
                getAdapter().notifyItemRangeChanged(0,data.size());
                handler.sendEmptyMessageDelayed(msg_type_play_gif, 500);
                showRealViewWithoutAnimation();
                showRefreshTips(data.size() + "条新内容");
            }
//            notifyShowGuide();
            //2.加载更多后
        } else {
            if (DataUtils.isEmpty(data)) {
                if (DataUtils.isEmpty(getAllDataList())) {
                    showEmptyView();
                } else {
//                    showRefreshTips("下拉刷新试试，还有更多精彩哦");//不需要提示了,因为刷新和加载更多执行同一个接口
                    getAdapter().noMoreData();
                    getPullLoadRecyclerView().loadmoreFinish(PullLoadRecyclerView.NO_MORE_DATA_NEXT_PAGE);
                }
            } else {
                addBottom(data);
                getAdapter().notifyItemRangeChanged(getAllDataList().size() - data.size(), data.size());
                showRealViewWithoutAnimation();
            }
            getPullLoadRecyclerView().setLoadMoreFinish();
        }

        isLoaded = true;
    }


    public void loadData(String loadDataOperate) {
        startAnim();
        switch (loadDataOperate) {
            case BizContant.LoadDataOperate.REFRESH:
                getPresenter().loadData(jokeCode, loadDataOperate);
                break;
            case BizContant.LoadDataOperate.LOADMORE:
                getPresenter().loadData(jokeCode, loadDataOperate);
                break;
        }
    }

    private void startAnim() {
        ((HomeFragment) getParentFragment()).startAnim();
    }

    private void stopAnim() {
        try {
            ((HomeFragment) getParentFragment()).stopAnim();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetView() {
        getSwipeRefreshLayout().setRefreshing(false);
        stopAnim();
    }

    private void showRefreshTips(String text) {
        tvTips.setText(text);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(tvTips, "scaleX", 0, 1);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(tvTips, "scaleY", 0, 1);
        tvTips.setVisibility(View.VISIBLE);
        objectAnimator1.setDuration(200);
        objectAnimator2.setDuration(200);

        objectAnimator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                handler.sendEmptyMessageDelayed(msg_type_tips, 1000);
            }
        });
        objectAnimator1.start();
        objectAnimator2.start();
    }


    private void addTop(Collection collection) {
        lastDataList.clear();
        lastDataList.addAll(collection);
        getAllDataList().addAll(0, collection);
    }

    private void addBottom(Collection collection) {
        lastDataList.clear();
        lastDataList.addAll(collection);
        getAllDataList().addAll(collection);
    }

    private void updateLastJokeId(List<DataAllBean> data) {
        if (!isLoaded) {
            //首次加载数据不显示"上一次看到的位置"
            return;
        }
        if (!DataUtils.isEmpty(data)) {
            getAdapter().setLastJokeId(data.get(data.size() - 1).getId());
        } else {
            getAdapter().setLastJokeId("-1");
        }
    }

    public String getJokeCode() {
        return jokeCode;
    }

    public List<DataAllBean> getLastDataList() {
        return lastDataList;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    @Override
    public boolean isCustomItemDecoration() {
        return true;
    }

    public void onSwitchCurrent() {
        handler.sendEmptyMessageDelayed(msg_type_play_gif, 300);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        HomeFragment homeFragment = (HomeFragment) getParentFragment();
        if (homeFragment == null) {
            return;
        }

        if (isVisibleToUser) {
            //Fragment可见
            homeFragment.setLastJokeCode(jokeCode);
        } else {
            //Fragment被隐藏
            if (isAtCurrentPage(homeFragment)) {
                stopGif();
            }
        }
    }

    /**
     * 是否在当前页
     * @param homeFragment
     * @return
     */
    private boolean isAtCurrentPage(HomeFragment homeFragment) {
        if(homeFragment==null){
            return false;
        }
        return jokeCode != null && jokeCode.equals(homeFragment.getLastJokeCode());
    }


    public void stopGif() {
        try {
            if (jokePhotoUtils != null) {
                jokePhotoUtils.stopGif(getActivity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        BLog.i(TAG, "BaseHomeFragment->onStart()");
    }



    @Override
    public void onPause() {
        super.onPause();
        BLog.i(TAG, "BaseHomeFragment->onPause()");
        stopGif();
    }


    /**
     * 创建presenter(卡榫函数)
     *
     * @return
     */
    @Override
    public JokePresenter createPresenter() {
        return new JokePresenter();
    }

    @Override
    public void onStop() {
        super.onStop();
        BLog.i(TAG, "BaseHomeFragment->onStop()");
    }

    /**
     * 计算列表中线位置
     *
     * @return
     */
    private int calculate() {
        int statusHeight = ScreenUtils.getStatusHeight(getContext());
        int topbarHeight = getContext().getResources().getDimensionPixelOffset(R.dimen.topbar_height);
        return statusHeight + topbarHeight + (ScreenUtils.getScreenHeight(getContext()) - statusHeight - topbarHeight) / 2;
    }

    private HomeFragment getHomeFragment(){
        Activity hostActivity = getHostActivity();
        if(hostActivity!=null){
            return (HomeFragment) getParentFragment();
        }
        return null;
    }
}