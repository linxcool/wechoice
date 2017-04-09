package com.linxcool.wechoice.data.entity;

/**
 * Created by linxcool on 17/4/8.
 */

public class VideoCategory {

    private String cid;
    private String name;

    public String getCid() {
        return cid;
    }

    public String getName() {
        return name;
    }

    public VideoCategory() {
    }

    public VideoCategory(String cid, String name) {
        this.cid = cid;
        this.name = name;
    }
}
