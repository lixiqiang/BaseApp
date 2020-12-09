package com.qiang.video.ui.module;

import java.util.ArrayList;

/**
 * @author lixiqiang
 * @date：2019/11/21 0021
 */
public class VideoDataManager {

    private ArrayList<VideoBean> mVideoList = new ArrayList<>();

    public void setVideoList(ArrayList<VideoBean> list) {
        this.mVideoList.clear();
        this.mVideoList.addAll(list);
    }

    public void addVideoList(ArrayList<VideoBean> list) {
        this.mVideoList.addAll(list);
    }

    public ArrayList<VideoBean> getVideoList() {
        return mVideoList;
    }

}
