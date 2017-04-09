package com.linxcool.wechoice.data.entity;

import java.util.List;

/**
 * Created by linxcool on 17/4/9.
 */

public class ImageList {

    public static final int PAGE_SIZE = 10;

    private int totalNum;
    private int start_index;
    private int return_number;
    private List<ImageItem> data;

    public int getReturnNumber() {
        return return_number;
    }

    public List<ImageItem> getData() {
        return data;
    }
}
