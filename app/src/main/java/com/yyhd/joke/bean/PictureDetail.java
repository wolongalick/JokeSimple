package com.yyhd.joke.bean;

import com.yyhd.joke.annotation.PhotoLoadStatus;

import java.io.Serializable;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/8/30
 * 备注:
 */
public class PictureDetail implements Serializable{
    /**
     * cutUrl : string
     * firstFrame : string
     * gif : true
     * height : 0
     * longPic : true
     * originImgurl : string
     * piiic : true
     * qiniuUrl : string
     * thumbnail : string
     * width : 0
     */

    private String cutUrl;
    private String firstFrame;
    private boolean gif;
    private int height;
    private boolean longPic;
    private String originImgurl;
    private boolean piiic;
    private String qiniuUrl;
    private String thumbnail;
    private int width;

    /*额外属性-begin*/
    private int load_status;
    /*额外属性-end*/

    public PictureDetail() {
    }

    public @PhotoLoadStatus int getLoad_status() {
        return load_status;
    }

    public void setLoad_status(@PhotoLoadStatus int load_status) {
        this.load_status = load_status;
    }

    public String getCutUrl() {
        return cutUrl;
    }

    public void setCutUrl(String cutUrl) {
        this.cutUrl = cutUrl;
    }

    public String getFirstFrame() {
        return firstFrame;
    }

    public void setFirstFrame(String firstFrame) {
        this.firstFrame = firstFrame;
    }

    public boolean isGif() {
        return gif;
    }

    public void setGif(boolean gif) {
        this.gif = gif;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isLongPic() {
        return longPic;
    }

    public void setLongPic(boolean longPic) {
        this.longPic = longPic;
    }

    public String getOriginImgurl() {
        return originImgurl;
    }

    public void setOriginImgurl(String originImgurl) {
        this.originImgurl = originImgurl;
    }

    public boolean isPiiic() {
        return piiic;
    }

    public void setPiiic(boolean piiic) {
        this.piiic = piiic;
    }

    public String getQiniuUrl() {
        return qiniuUrl;
    }

    public void setQiniuUrl(String qiniuUrl) {
        this.qiniuUrl = qiniuUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }


}
