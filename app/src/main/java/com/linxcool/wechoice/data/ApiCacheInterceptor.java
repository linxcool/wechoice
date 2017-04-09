package com.linxcool.wechoice.data;

import android.content.Context;

import com.linxcool.andbase.util.NetWorkUtil;
import com.linxcool.andbase.util.TextUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by linxcool on 17/4/9.
 */

public class ApiCacheInterceptor implements Interceptor {

    private Context context;
    private boolean network;

    public ApiCacheInterceptor(Context context, boolean network) {
        this.context = context;
        this.network = network;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        if (network) return interceptOnline(chain);
        else return interceptOffline(chain);
    }

    private Response interceptOffline(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        String cacheForce = request.header("Cache-Force");
        if (TextUtil.notEmpty(cacheForce) || !NetWorkUtil.isNetConnected(context)) {
            // 离线时缓存保存4周,单位:秒
            int maxStale = 60 * 60 * 24 * 28;
            CacheControl tempCacheControl = new CacheControl.Builder()
                    .onlyIfCached()
                    .maxStale(maxStale, TimeUnit.SECONDS)
                    .build();
            request = request.newBuilder()
                    .cacheControl(tempCacheControl)
                    .build();
        }
        return chain.proceed(request);
    }

    private Response interceptOnline(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        // 在线缓存在1分钟内可读取 单位:秒
        int maxAge = 60 * 1;
        return response.newBuilder()
                // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, max-age=" + maxAge)
                .build();
    }
}
