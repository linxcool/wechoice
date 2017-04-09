package com.linxcool.wechoice.data;

import android.content.Context;

import com.linxcool.andbase.util.NetWorkUtil;
import com.linxcool.andbase.util.TextUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by linxcool on 17/4/9.
 */

public class ApiCacheInterceptor implements Interceptor {

    private static final int TIMEOUT_DISCONNECT = 60 * 60 * 24 * 7; //7天

    private Context context;

    public ApiCacheInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        boolean forceCache = TextUtil.notEmpty(request.header("Cache-Force"))
                || !NetWorkUtil.isNetConnected(context);
        if (forceCache) {
            request = request.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached")
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            return chain.proceed(request);
        } else {
            return chain.proceed(request)
                    .newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + TIMEOUT_DISCONNECT)
                    .build();
        }

    }

}
