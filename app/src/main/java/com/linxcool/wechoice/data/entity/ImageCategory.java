package com.linxcool.wechoice.data.entity;

/**
 * Created by linxcool on 17/4/8.
 */

public class ImageCategory {

    private String cid;
    private String name;

    public String getCid() {
        return cid;
    }

    public String getName() {
        return name;
    }

    public ImageCategory() {
    }

    public ImageCategory(String name) {
        this.cid = name;
        this.name = name;
    }
}
