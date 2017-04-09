package com.linxcool.wechoice;

import com.linxcool.wechoice.data.ApiFactory;

/**
 * Created by linxcool on 17/3/15.
 */

public class Application extends android.app.Application {

    static Application app;

    public static Application get() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        ApiFactory.regist(this);
    }

}
