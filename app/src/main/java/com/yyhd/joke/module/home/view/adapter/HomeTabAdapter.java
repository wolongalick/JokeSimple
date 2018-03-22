package com.yyhd.joke.module.home.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

import common.ui.adapter.BaseFragmentPagerAdapter;

/**
 * Created by hackware on 2016/9/10.
 */

public class HomeTabAdapter<W extends Fragment> extends BaseFragmentPagerAdapter<W> {

    public HomeTabAdapter(FragmentManager fm, List<W> dataList) {
        super(fm,dataList);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getFragments().get(position).hashCode()+"";
    }

    @Override
    public Fragment getItem(int position) {
        return getFragments().get(position);
    }
}
