package com.linxcool.wechoice;

import com.linxcool.wechoice.data.ApiFactory;

/**
 * Created by linxcool on 17/3/15.
 */

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApiFactory.regist(this);
    }

}
