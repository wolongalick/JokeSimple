package com.yyhd.joke.bean;

import java.util.List;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/8/5
 * 备注:
 */
public class WrapData {
    private String loadDataOperate;
    private List<DataAllBean> homeBeanList;
    private long diffTime;

    public WrapData(String loadDataOperate, List<DataAllBean> homeBeanList, long diffTime) {
        this.loadDataOperate = loadDataOperate;
        this.homeBeanList = homeBeanList;
        this.diffTime=diffTime;
    }

    public String getLoadDataOperate() {
        return loadDataOperate;
    }

    public void setLoadDataOperate(String loadDataOperate) {
        this.loadDataOperate = loadDataOperate;
    }

    public List<DataAllBean> getHomeBeanList() {
        return homeBeanList;
    }

    public void setHomeBeanList(List<DataAllBean> homeBeanList) {
        this.homeBeanList = homeBeanList;
    }

    public long getDiffTime() {
        return diffTime;
    }

    public void setDiffTime(long diffTime) {
        this.diffTime = diffTime;
    }
}
