package com.linxcool.wechoice.data.entity;

import java.io.Serializable;

/**
 * Created by linxcool on 17/3/16.
 */

public class ArticleCategory implements Serializable {

    private String cid;
    private String name;

    public String getCid() {
        return cid;
    }

    public String getName() {
        return name;
    }

    public ArticleCategory() {
    }

    public ArticleCategory(String cid, String name) {
        this.cid = cid;
        this.name = name;
    }
}
