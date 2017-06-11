package com.linxcool.wechoice.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.linxcool.andbase.util.CacheUtil;
import com.linxcool.andbase.util.TextUtil;
import com.linxcool.wechoice.data.entity.ArticleCategory;
import com.linxcool.wechoice.data.entity.ArticleItem;
import com.linxcool.wechoice.data.entity.ImageCategory;
import com.linxcool.wechoice.data.entity.ImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linxcool on 17/5/28.
 */

public class CollectDataCache {

    private static final String CACHE_IMAGES = "collect_images";
    private static final String CACHE_ARTICLE = "collect_articles";

    private static Gson gson = new Gson();
    private static CacheUtil cacheUtil;
    private static List<ImageItem> imageCache;
    private static List<ArticleItem> articleCache;

    public static List<ImageItem> getImages() {
        return imageCache;
    }

    public static List<ArticleItem> getArticles() {
        return articleCache;
    }

    public static void regist(Context context) {
        cacheUtil = CacheUtil.get(context);
        String jsonStr = cacheUtil.getAsString(CACHE_IMAGES);
        if (TextUtil.notEmpty(jsonStr)) {
            imageCache = gson.fromJson(jsonStr, new TypeToken<List<ImageItem>>() {
            }.getType());
        } else {
            imageCache = new ArrayList<>();
        }
        jsonStr = cacheUtil.getAsString(CACHE_ARTICLE);
        if (TextUtil.notEmpty(jsonStr)) {
            articleCache = gson.fromJson(jsonStr, new TypeToken<List<ArticleItem>>() {
            }.getType());
        } else {
            articleCache = new ArrayList<>();
        }
    }

    public static void save(ImageItem item) {
        if (!contains(item)) {
            imageCache.add(item);
            cacheUtil.put(CACHE_IMAGES, gson.toJson(imageCache));
        }
    }

    public static void remove(ImageItem item) {
        for (ImageItem recod : imageCache) {
            if (item.getImageUrl().equals(recod.getImageUrl())) {
                imageCache.remove(recod);
                cacheUtil.put(CACHE_IMAGES, gson.toJson(imageCache));
            }
        }
    }

    public static boolean contains(ImageItem item) {
        for (ImageItem recod : imageCache) {
            if (item.getImageUrl().equals(recod.getImageUrl()))
                return true;
        }
        return false;
    }

    public static ImageCategory asImageCollectCategory() {
        return new ImageCategory("collect", "收藏");
    }

    public static void save(ArticleItem item) {
        if (!contains(item)) {
            articleCache.add(item);
            cacheUtil.put(CACHE_ARTICLE, gson.toJson(articleCache));
        }
    }

    public static void remove(ArticleItem item) {
        for (ArticleItem recod : articleCache) {
            if (item.getUrl().equals(recod.getUrl())) {
                articleCache.remove(recod);
                cacheUtil.put(CACHE_ARTICLE, gson.toJson(articleCache));
            }
        }
    }

    public static boolean contains(ArticleItem item) {
        for (ArticleItem recod : articleCache) {
            if (item.getUrl().equals(recod.getUrl()))
                return true;
        }
        return false;
    }

    public static ArticleCategory asArticleCollectCategory() {
        return new ArticleCategory("collect", "收藏");
    }

    public static boolean isCollectCategory(String cid) {
        return "collect".equals(cid);
    }

}
