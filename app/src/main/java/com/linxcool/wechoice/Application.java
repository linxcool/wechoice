package com.linxcool.wechoice;

import com.linxcool.wechoice.data.ApiFactory;
import com.linxcool.wechoice.data.CollectDataCache;

/**
 * Created by linxcool on 17/3/15.
 */

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApiFactory.regist(this);
        CollectDataCache.regist(this);
    }

}
