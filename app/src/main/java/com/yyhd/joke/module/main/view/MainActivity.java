package com.yyhd.joke.module.main.view;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.yyhd.joke.simple.R;
import com.yyhd.joke.base.BaseSGActivity;
import com.yyhd.joke.module.home.view.HomeFragment;
import com.yyhd.joke.module.main.presenter.MainPresenter;
import com.yyhd.joke.module.mine.view.UserInfoFragment;
import com.yyhd.joke.module.task.view.TaskFragment;
import com.yyhd.joke.utils.BizUtils;
import com.yyhd.joke.utils.IntentKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.ui.FragmentTabHost;
import common.utils.PhoneUtils;
import common.utils.T;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2018/3/6
 * 备注:
 */
public class MainActivity extends BaseSGActivity<IMainView,MainPresenter> implements IMainView{

    @BindView(R.id.fl_main_realcontent)
    FrameLayout flMainRealcontent;
    @BindView(R.id.view_divider)
    View viewDivider;
    @BindView(android.R.id.tabcontent)
    FrameLayout tabcontent;
    @BindView(android.R.id.tabhost)
    FragmentTabHost mTabHost;


    private long exitTime;


    private Class<?> fragmentArray[] = {HomeFragment.class, TaskFragment.class, UserInfoFragment.class};
    private String mTextviewArray[] = {"首页", "任务", "我的"};
    private int mImageViewArray[] = {R.drawable.selector_home, R.drawable.selector_task, R.drawable.selector_mine};


    private View iv_mine;


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent!=null){
            boolean isByLoginSuccess = intent.getBooleanExtra(IntentKey.IS_BY_LOGIN_SUCCESS, false);
            if(isByLoginSuccess){
                setTabIndex(0);
            }
        }
    }

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
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mTabHost.setup(this, getSupportFragmentManager(), R.id.fl_main_realcontent);
        for (int i = 0; i < fragmentArray.length; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            View tabItemView = getTabItemView(i);
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(fragmentArray[i].getSimpleName()).setIndicator(tabItemView);
            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
        }
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
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    private View getTabItemView(int index) {
        View view = getLayoutInflater().inflate(R.layout.item_home_tab, null);
        ImageView imageView = view.findViewById(R.id.item_tabhost_topImg);
        TextView textView = view.findViewById(R.id.item_tabhost_topText);

        imageView.setImageResource(mImageViewArray[index]);
        textView.setText(mTextviewArray[index]);

        if(index==2){
            iv_mine=imageView;
        }

        return view;
    }

    private void closeApp() {
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                T.show(getContext(), "再按一次退出");
                exitTime = System.currentTimeMillis();
            } else {
                closeApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setTabIndex(int index){
        mTabHost.setCurrentTab(index);
    }

    @Override
    public void onGetPhonePerm(boolean isSuccessed) {
        if(isSuccessed){
            BizUtils.saveImei(PhoneUtils.getImei(this));
        }else {
            T.show(this,"未获取电话信息权限");
        }
    }
}
