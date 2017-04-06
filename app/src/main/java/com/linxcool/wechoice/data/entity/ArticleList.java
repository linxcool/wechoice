package com.linxcool.wechoice.data.entity;

import com.linxcool.andbase.retrofit.Reply;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linxcool on 17/4/2.
 */

public class ArticleList implements Serializable {

    public static final int PAGE_SIZE = 10;

    private int error_code;
    private String reason;
    private Reply<List<ArticleItem>> result;

    public int getErrorCode() {
        return error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReply(Reply<List<ArticleItem>> result) {
        this.result = result;
    }

    public Reply<List<ArticleItem>> getReply() {
        return result;
    }
}
