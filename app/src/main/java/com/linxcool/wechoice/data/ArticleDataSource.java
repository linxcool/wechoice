package com.linxcool.wechoice.data;

import android.content.Context;

import com.linxcool.andbase.db.DbHelper;
import com.linxcool.andbase.retrofit.Reply;
import com.linxcool.wechoice.data.entity.ArticleItem;
import com.linxcool.wechoice.data.entity.ArticleList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

/**
 * Created by linxcool on 17/4/3.
 */
public class ArticleDataSource {

    private static Map<String, DbHelper<ArticleItem>> articleDbHelpers = new HashMap<>();

    private static Context context;

    public static void regist(Context context) {
        ArticleDataSource.context = context;
    }

    private static DbHelper<ArticleItem> getDbHelper(String cid) {
        DbHelper<ArticleItem> dbHelper = articleDbHelpers.get(cid);
        if (dbHelper == null) {
            dbHelper = new DbHelper(context, ArticleItem.instance4Db(cid));
            articleDbHelpers.put(cid, dbHelper);
        }
        return dbHelper;
    }

    public static Observable<ArticleList> mapToDbCache(
            final String cid, Observable<ArticleList> fromNetwork) {
        final DbHelper<ArticleItem> dbHelper = getDbHelper(cid);
        return fromNetwork.map(new Function<ArticleList, ArticleList>() {
            @Override
            public ArticleList apply(ArticleList t) throws Exception {
                if (t.getReply() != null && t.getReply().getData() != null) {
                    List<ArticleItem> list = t.getReply().getData();
                    List<ArticleItem> dels = new ArrayList<>();
                    for (int i = list.size() - 1; i >= 0; i--) {
                        ArticleItem item = list.get(i);
                        item.setCategory(cid);
                        ArticleItem cache = dbHelper.selectByKey("key", item.getUniqueKey());
                        if (cache == null) {
                            dbHelper.insert(item);
                        } else {
                            dels.add(item);
                        }
                    }
                    for (ArticleItem item : dels) {
                        list.remove(item);
                    }
                }
                return t;
            }
        });
    }

    public static int getMaxId(String cid) {
        DbHelper<ArticleItem> dbHelper = getDbHelper(cid);
        return dbHelper.selectMaxId();
    }

    public static Observable<ArticleList> loadAfters(String cid, int curMaxId) {
        int nextMaxId = curMaxId + ArticleList.PAGE_SIZE;
        String filter = String.format("WHERE id > %d AND id <= %d ORDER BY id DESC", curMaxId, nextMaxId);
        return loadDbCache(cid, filter);
    }

    public static Observable<ArticleList> loadPrevious(String cid, int curMinId) {
        int preMinId = curMinId - ArticleList.PAGE_SIZE;
        String filter = String.format("WHERE id >= %d AND id < %d ORDER BY id DESC", preMinId, curMinId);
        return loadDbCache(cid, filter);
    }

    private static Observable<ArticleList> loadDbCache(String cid, final String filter) {
        final DbHelper<ArticleItem> dbHelper = getDbHelper(cid);
        return Observable.create(new ObservableOnSubscribe<ArticleList>() {
            @Override
            public void subscribe(ObservableEmitter<ArticleList> subscriber) throws Exception {
                List<ArticleItem> list = dbHelper.selectInCase(filter);
                ArticleList cache = new ArticleList();
                cache.setReply(Reply.success(list));
                if (cache != null) {
                    subscriber.onNext(cache);
                } else {
                    subscriber.onComplete();
                }
            }
        });
    }


}
