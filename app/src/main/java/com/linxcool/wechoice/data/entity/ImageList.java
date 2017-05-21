package com.linxcool.wechoice.data.entity;

import java.util.List;

/**
 * Created by linxcool on 17/4/9.
 */

public class ImageList {

    public static final int PAGE_SIZE = 20;

    private int totalNum;
    private int startIndex;
    private int returnNumber;
    private List<ImageItem> imgs;

    public int getReturnNumber() {
        return returnNumber;
    }

    public List<ImageItem> getData() {
        return imgs;
    }
}
