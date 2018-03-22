package com.yyhd.joke.module.home.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.ObjectAnimator;
import com.yyhd.joke.base.BaseSGFragment_v4;
import com.yyhd.joke.bean.DataAllBean;
import com.yyhd.joke.db.entity.JokeType;
import com.yyhd.joke.module.home.presenter.HomePresenter;
import com.yyhd.joke.module.home.view.adapter.HomeTabAdapter;
import com.yyhd.joke.module.home.view.fragment.BaseHomeFragment;
import com.yyhd.joke.module.home.view.fragment.ImageHomeFragment;
import com.yyhd.joke.module.home.view.fragment.PreferTextImageHomeFragment;
import com.yyhd.joke.module.home.view.fragment.TextHomeFragment;
import com.yyhd.joke.module.home.view.fragment.TextImageHomeFragment;
import com.yyhd.joke.module.main.view.MainActivity;
import com.yyhd.joke.simple.R;
import com.yyhd.joke.utils.BizContant;
import com.yyhd.joke.utils.BizUtils;
import com.yyhd.joke.utils.IntentKey;
import com.yyhd.joke.weiget.ColorFlipPagerTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.bean.ErrorMsg;
import common.ui.datacontent.GlobalFrameLayout;
import common.ui.datacontent.IFailLayout;
import common.utils.BLog;
import common.utils.DataUtils;
import common.utils.DensityUtils;
import common.utils.ScreenUtils;
import common.utils.T;


public class HomeFragment extends BaseSGFragment_v4<IHomeView,HomePresenter> implements IHomeView {

    private final String TAG = "HomeFragment";

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.iv_refresh)
    ImageView ivRefresh;
    @BindView(R.id.fl_circle)
    FrameLayout flCircle;
    @BindView(R.id.ll_rootView)
    LinearLayout llRootView;
    @BindView(R.id.viewholder)
    View viewholder;
    @BindView(R.id.toast_award)
    FrameLayout toastAward;

    private List<BaseHomeFragment> fragments;

    private List<JokeType> jokeTabRows;
    private HomeTabAdapter homeTabAdapter;
    private List<String> tabArrarys;


    private ObjectAnimator refreshAnim;
    private String lastJokeCode;
    private int yOffset;
    private boolean isYOffsetCalculated;
    private boolean isShowingNewbieGuide;   //是否正在展示新手引导层


    /**
     * 初始化变量
     */
    @Override
    public void initParmers() {
        fragments = new ArrayList<>();
        tabArrarys = new ArrayList<>();

    }

    /**
     * 初始化页面控件
     */
    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
    }

    /**
     * 初始化页面控件的值以及设置控件监听
     */
    @Override
    public void initValues() {

        GlobalFrameLayout globalFrameLayout = initGlobalFrameLayout(new IFailLayout.OnClickReloadButtonListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getJokeTypes();
            }
        });
        ((FrameLayout.LayoutParams) globalFrameLayout.getLoadingContent().getLayoutParams()).setMargins(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.topbar_height));

        viewholder.getLayoutParams().height = ScreenUtils.getStatusHeight(getContext());
        getPresenter().getJokeTypes();
        showLoadingView();


        toastAward.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(!isYOffsetCalculated){
                    //保证只测量一次yOffset
                    yOffset = toastAward.getTop() - DensityUtils.dp2px(getHostActivity(), 79.5f);
                }
                isYOffsetCalculated = true;
                toastAward.setVisibility(View.GONE);//测量完毕后立马隐藏
                toastAward.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void gotoUserInfoActivity() {
        T.show(getContext(),"跳转到我的");
//        startActivity(new Intent(getHostActivity(), UserInfoFragment.class));
        ((MainActivity)getHostActivity()).setTabIndex(2);

    }

    /**
     * 在onDestory()手动释放资源
     */
    @Override
    public void releaseOnDestory() {

    }

    /**
     * 当获取段子标签成功的回调函数
     *
     * @param jokeTypes
     */
    @Override
    public void onGetJokeTabsSuccess(List<JokeType> jokeTypes) {
        if (!DataUtils.isEmpty(jokeTypes)) {
            lastJokeCode = jokeTypes.get(0).getCode();
        }
        jokeTabRows = jokeTypes;

        fullData();


        homeTabAdapter = new HomeTabAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(homeTabAdapter);
        initMagicIndicator();
        showRealView();
    }


    /**
     * 当获取段子标签失败的回调函数
     *
     * @param errorMsg
     */
    @Override
    public void onGetJokeTabsFail(ErrorMsg errorMsg) {
        showFailView();
    }



    private void fullData() {
        for (JokeType rowsBean : jokeTabRows) {
            tabArrarys.add(rowsBean.getCode());

            int layoutType = BizUtils.jokeCode2LayoutType(rowsBean.getCode());

            Bundle bundle = new Bundle();
            bundle.putString(IntentKey.JOKE_CODE, rowsBean.getCode());
            switch (layoutType) {
                case BizContant.JokeLayoutType.ITEM_TYPE_TEXT:
                    TextHomeFragment textHomeFragment = new TextHomeFragment();
                    textHomeFragment.setArguments(bundle);
                    fragments.add(textHomeFragment);
                    break;
                case BizContant.JokeLayoutType.ITEM_TYPE_IMAGE:
                    ImageHomeFragment imageHomeFragment = new ImageHomeFragment();
                    imageHomeFragment.setArguments(bundle);
                    fragments.add(imageHomeFragment);
                    break;
                case BizContant.JokeLayoutType.ITEM_TYPE_TEXT_IMAGE:
                    TextImageHomeFragment textImageHomeFragment = new TextImageHomeFragment();
                    textImageHomeFragment.setArguments(bundle);
                    fragments.add(textImageHomeFragment);
                    break;
                case BizContant.JokeLayoutType.ITEM_TYPE_PREFER_TEXT_IMAGE:
                    PreferTextImageHomeFragment preferTextImageHomeFragment = new PreferTextImageHomeFragment();
                    preferTextImageHomeFragment.setArguments(bundle);
                    fragments.add(preferTextImageHomeFragment);
                    break;
            }
        }
    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setEnablePivotScroll(true);//多指示器模式，可以滑动
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return jokeTabRows.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.setText(jokeTabRows.get(index).getDesc());
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.main_text_color));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.main_text_color));
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });

                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {

                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 3));
                indicator.setLineWidth(UIUtil.dip2px(context, 20));
                indicator.setRoundRadius(UIUtil.dip2px(context, 5));
                indicator.setYOffset(DensityUtils.dp2px(getContext(), 0));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(getResources().getColor(R.color.main_text_color));

                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        //ViewPagerHelper.bind(magicIndicator, viewPager);
//        MyViewPagerHelper.bind(magicIndicator,viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            public void onPageSelected(int position) {
                magicIndicator.onPageSelected(position);
                onPageSelected4ActionLog();
            }

            public void onPageScrollStateChanged(int state) {
                magicIndicator.onPageScrollStateChanged(state);
            }
        });
    }

    private void onPageSelected4ActionLog() {
        BaseHomeFragment baseHomeFragment = fragments.get(viewPager.getCurrentItem());
        if (baseHomeFragment.isLoaded()) {
            //只有之前加在过,才展示列表
            List<DataAllBean> lastDataList = baseHomeFragment.getLastDataList();
            baseHomeFragment.loadData4log(lastDataList, BizContant.MIN_LOAD_DATA_DURATION);
            baseHomeFragment.onSwitchCurrent();
        }
    }

    @OnClick({R.id.fl_circle})
    public void onClickCircle() {
        BaseHomeFragment baseHomeFragment = null;
        try {
            if (viewPager.getChildCount() > 0) {
                baseHomeFragment = fragments.get(viewPager.getCurrentItem());
                baseHomeFragment.getSwipeRefreshLayout().setRefreshing(true);
                baseHomeFragment.getPullLoadRecyclerView().getLayoutManager().scrollToPosition(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (baseHomeFragment != null) {
                baseHomeFragment.refreshByCircleBtn();
            }
        }
    }

    public void startAnim() {
        // 动画的持续时间，执行多久？
        flCircle.setEnabled(false);
        if (refreshAnim == null ) {
            refreshAnim = ObjectAnimator.ofFloat(ivRefresh, "rotation", 0f, 360f);
            refreshAnim.setRepeatCount(Integer.MAX_VALUE);
            refreshAnim.setDuration(500);
        }
        refreshAnim.start();
    }

    public void stopAnim() {
        flCircle.setEnabled(true);
        refreshAnim.end();
    }

    public LinearLayout getRootView() {
        return llRootView;
    }


    /**
     * 创建presenter(卡榫函数)
     *
     * @return
     */
    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        BLog.i(TAG, "HomeFragment->onResume()");
        refreshUI();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            refreshUI();
        }else {
            if (!DataUtils.isEmpty(fragments)) {
                BaseHomeFragment baseHomeFragment = fragments.get(viewPager.getCurrentItem());
                if (baseHomeFragment != null) {
                    baseHomeFragment.stopGif();
                }
            }
        }
    }

    /**
       * 刷新UI
     */
    private void refreshUI() {

        if (!DataUtils.isEmpty(fragments)) {
            BaseHomeFragment baseHomeFragment = fragments.get(viewPager.getCurrentItem());
            if (baseHomeFragment != null) {
                baseHomeFragment.prePlayGif();
            }
        }
    }


    public String getCurrentJokeType() {
        if (jokeTabRows == null) {
            BLog.w("jokeTabRows为null");
            return BizContant.JokeCode.COMMEND;
        }
        return jokeTabRows.get(viewPager.getCurrentItem()).getCode();
    }

    private void showRed() {
        final Dialog dialog = new Dialog(getHostActivity(), R.style.dialog);
        Window window = getHostActivity().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 1f;    // 设置透明度为0.5
        window.setAttributes(lp);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_red_coupon, null);

        if(!view.hasOnClickListeners()){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    showRed4login();
                }
            });
        }

        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.show();
    }

    private void showRed4login() {
        final Dialog dialog = new Dialog(getContext(), R.style.dialog);
        Window window = getHostActivity().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 1f;    // 设置透明度为0.5
        window.setAttributes(lp);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_red_coupon_login, null);

        if(!view.hasOnClickListeners()){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    T.show(getContext(),"禁止登录");
                    dialog.dismiss();
                }
            });
        }
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.show();
    }


    private void showRedPacketGif() {

    }

    public String getLastJokeCode() {
        return lastJokeCode;
    }

    public void setLastJokeCode(String lastJokeCode) {
        this.lastJokeCode = lastJokeCode;
    }

    public boolean isShowingNewbieGuide() {
        return isShowingNewbieGuide;
    }

    /**`
     * 获取布局ID
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }
}
