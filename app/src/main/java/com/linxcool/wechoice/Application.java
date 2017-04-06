package com.linxcool.wechoice;

import com.linxcool.andbase.retrofit.ParamsInterceptor;
import com.linxcool.wechoice.data.ArticleDataSource;

/**
 * Created by linxcool on 17/3/15.
 */

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ParamsInterceptor.setLogable(true);
        ArticleDataSource.regist(this);
    }

}
