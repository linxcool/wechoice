package com.linxcool.wechoice.data.entity;

import java.io.Serializable;

/**
 * 由于服务接口不可空性，预计设计的一个ImageCategory下存在多个tags，现改为以tag作为仅有标签
 * Created by linxcool on 17/4/8.
 */
public class ImageCategory implements Serializable {

    private String col;
    private String name;
    private String[] tags;

    public String getCol() {
        return col;
    }

    public String getName() {
        return name;
    }

    public String[] getTags() {
        return tags;
    }

    public String getTag(int index) {
        return tags[index];
    }

    public ImageCategory(String name) {
        this.col = name;
        this.name = name;
    }

    public ImageCategory(String name, String... tags) {
        this(name);
        this.tags = tags;
    }

    @Override
    public String toString() {
        return tags[0];
    }
}
