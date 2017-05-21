package com.linxcool.wechoice.data.entity;

import com.linxcool.andbase.util.TextUtil;

import java.io.Serializable;

/**
 * Created by linxcool on 17/4/9.
 */

public class ImageItem implements Serializable {

    private String id;
    private String date;

    private String abs;
    private String desc;

    private String imageUrl;
    private int imageWidth;
    private int imageHeight;

    private String downloadUrl;

    private String thumbnailUrl;
    private int thumbnailWidth;
    private int thumbnailHeight;

    private String thumbLargeUrl;
    private int thumbLargeWidth;
    private int thumbLargeHeight;

    private String hostname;
    private String siteUrl;
    private String fromUrl;
    private String objUrl;
    private String shareUrl;

    private int fixWidth;
    private int fixHeight;

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        if (TextUtil.notEmpty(imageUrl))
            return imageUrl;
        if (TextUtil.notEmpty(downloadUrl))
            return downloadUrl;
        return getThumbLargeUrl();
    }

    public String getThumbLargeUrl() {
        if (TextUtil.notEmpty(thumbLargeUrl))
            return thumbLargeUrl;
        return thumbnailUrl;
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
        return TextUtil.notEmpty(imageUrl);
    }

    public void fixSize(int fixWidth) {
        if (this.fixWidth > 0) {
            return;
        }
        this.fixWidth = fixWidth;
        if (thumbLargeWidth > 0)
            fixHeight = fixWidth * thumbLargeHeight / thumbLargeWidth;
        else if (thumbnailWidth > 0)
            fixHeight = fixWidth * thumbnailHeight / thumbnailWidth;
        else if (imageWidth > 0)
            fixHeight = fixWidth * imageHeight / imageWidth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageItem imageItem = (ImageItem) o;

        return imageUrl != null ? imageUrl.equals(imageItem.imageUrl) : imageItem.imageUrl == null;

    }

    @Override
    public int hashCode() {
        return imageUrl != null ? imageUrl.hashCode() : 0;
    }
}
