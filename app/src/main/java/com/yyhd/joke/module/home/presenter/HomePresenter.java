package com.yyhd.joke.module.home.presenter;

import com.yyhd.joke.api.SGApiUtils;
import com.yyhd.joke.db.entity.JokeType;
import com.yyhd.joke.module.home.view.IHomeView;
import com.yyhd.joke.utils.BizContant;

import java.util.List;

import common.base.BaseMvpPresenter;
import common.bean.ErrorMsg;
import common.utils.DataUtils;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/8/4
 * 备注:
 */
public class HomePresenter extends BaseMvpPresenter<IHomeView> {

    public HomePresenter() {

    }

    /**
     * 获取段子类型集合
     */
    public void getJokeTypes() {
        getJokeTypesFromNet();
    }

    /**
     * 从网络获取段子类型集合
     */
    private void getJokeTypesFromNet() {
        SGApiUtils.getInstance().request(SGApiUtils.getInstance().getApiService().getJokeTypes(), new SGApiUtils.NetCallback<List<JokeType>>() {
            @Override
            public void onFailed(ErrorMsg errorMsg) {

            }

            @Override
            public void onSuccessed(List<JokeType> jokeTypes) {
                //排序
                DataUtils.filterList(jokeTypes, new DataUtils.FilterCallback<JokeType>() {
                    @Override
                    public boolean isNeedRemove(JokeType model) {
                        return model.isDeleted() || !model.getCode().equals(BizContant.JokeCode.COMMEND);
                    }
                });
                getView().onGetJokeTabsSuccess(jokeTypes);
            }
        });
    }

}
