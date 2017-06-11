package com.linxcool.wechoice.data.entity;

import android.database.Cursor;

import com.linxcool.andbase.db.DbInfo;
import com.linxcool.andbase.db.DbTag;
import com.linxcool.andbase.util.DateUtil;

import static com.linxcool.andbase.db.DbTag.CONSTRAINT_PRIMARY_KEY;
import static com.linxcool.andbase.db.DbTag.CONSTRAINT_UNIQUE;
import static com.linxcool.andbase.db.DbTag.TYPE_INTEGER;
import static com.linxcool.andbase.db.DbTag.TYPE_VARCHAR_512;

/**
 * Created by linxcool on 17/4/2.
 */

public class ArticleItem extends DbInfo implements Comparable<ArticleItem> {

    @DbTag(name = "id",
            type = TYPE_INTEGER,
            constraint = CONSTRAINT_PRIMARY_KEY,
            autoIncrease = true)
    private int id;

    @DbTag(name = "key", constraint = CONSTRAINT_UNIQUE)
    private String uniquekey;

    @DbTag(name = "category")
    private String category;

    @DbTag(name = "title", type = TYPE_VARCHAR_512)
    private String title;

    @DbTag(name = "author")
    private String author_name;

    @DbTag(name = "pic1", type = TYPE_VARCHAR_512)
    private String thumbnail_pic_s;

    @DbTag(name = "pic2", type = TYPE_VARCHAR_512)
    private String thumbnail_pic_s02;

    @DbTag(name = "pic3", type = TYPE_VARCHAR_512)
    private String thumbnail_pic_s03;

    @DbTag(name = "date")
    private String date;

    @DbTag(name = "url")
    private String url;

    @DbTag(name = "time", type = TYPE_INTEGER)
    private long createTime;

    @Override
    public String getTableName() {
        return "articles_" + Math.abs(category.hashCode());
    }

    public static ArticleItem instance4Db(String category) {
        ArticleItem item = new ArticleItem();
        item.category = category;
        return item;
    }

    public ArticleItem() {
        debug = true;
        this.createTime = System.currentTimeMillis();
    }

    public ArticleItem(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndex("id"));
        this.uniquekey = cursor.getString(cursor.getColumnIndex("key"));
        this.category = cursor.getString(cursor.getColumnIndex("category"));
        this.title = cursor.getString(cursor.getColumnIndex("title"));
        this.author_name = cursor.getString(cursor.getColumnIndex("author"));
        this.thumbnail_pic_s = cursor.getString(cursor.getColumnIndex("pic1"));
        this.thumbnail_pic_s02 = cursor.getString(cursor.getColumnIndex("pic2"));
        this.thumbnail_pic_s03 = cursor.getString(cursor.getColumnIndex("pic3"));
        this.date = cursor.getString(cursor.getColumnIndex("date"));
        this.url = cursor.getString(cursor.getColumnIndex("url"));
        this.createTime = cursor.getLong(cursor.getColumnIndex("time"));
    }

    public int getId() {
        return id;
    }

    public String getUniqueKey() {
        return uniquekey;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthory() {
        return author_name;
    }

    public String getThumbnails() {
        return thumbnail_pic_s;
    }

    public String getThumbnails2() {
        return thumbnail_pic_s02;
    }

    public String getThumbnails3() {
        return thumbnail_pic_s03;
    }

    public String getPubTime() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int compareTo(ArticleItem another) {
        if (another == null) return 1;
        String formatType = "yyyy-MM-dd HH:mm";
        long t = DateUtil.stringToLong(date, formatType);
        long a = DateUtil.stringToLong(another.date, formatType);
        return (int) (t - a);
    }

}
