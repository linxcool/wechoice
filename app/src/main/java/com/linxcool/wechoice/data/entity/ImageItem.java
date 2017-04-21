package com.linxcool.wechoice.data.entity;

import com.linxcool.andbase.util.TextUtil;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by linxcool on 17/4/9.
 */

public class ImageItem implements Serializable {

    private String id;
    private String date;

    private String abs;
    private String desc;

    private String image_url;
    private int image_width;
    private int image_height;

    private String download_url;

    private String thumbnail_url;
    private int thumbnail_width;
    private int thumbnail_height;

    private String thumb_large_url;
    private int thumb_large_width;
    private int thumb_large_height;

    private String hostname;
    private String site_url;
    private String from_url;
    private String obj_url;
    private String share_url;

    private int fixWidth;
    private int fixHeight;

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return image_url;
    }

    public String getThumbnailUrl() {
        return thumbnail_url;
    }

    public String getThumbLargeUrl() {
        return thumb_large_url;
    }

    public String getTitle() {
        if (TextUtil.notEmpty(abs))
            return abs;
        if (TextUtil.notEmpty(desc))
            return desc;
        return "嘿嘿嘿嘿";
    }

    public int getFixWidth() {
        return fixWidth;
    }

    public int getFixHeight() {
        return fixHeight;
    }

    public boolean isLegal() {
        return TextUtil.notEmpty(image_url);
    }

    public void fixSize(int fixWidth) {
        if (this.fixWidth > 0) {
            return;
        }
        this.fixWidth = fixWidth;
        if (thumb_large_width > 0)
            fixHeight = fixWidth * thumb_large_height / thumb_large_width;
        else if (thumbnail_width > 0)
            fixHeight = fixWidth * thumbnail_height / thumbnail_width;
        else if (image_width > 0)
            fixHeight = fixWidth * image_height / image_width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageItem imageItem = (ImageItem) o;

        return id != null ? id.equals(imageItem.id) : imageItem.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
