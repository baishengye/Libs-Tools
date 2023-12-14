package com.xiaoyingbo.lib_network.interceptor;


import com.xiaoyingbo.lib_util.BSY.util.BSYNetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetWorkInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        //无网络时强制使用缓存
        if (!BSYNetworkUtils.isConnected()) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }

        Response response = chain.proceed(request);

        if (BSYNetworkUtils.isConnected()) {
            //有网络时，设置超时为0
            int maxStale = 0;
            response.newBuilder()
                    .header("Cache-Control", "public,max-age=" + maxStale)
                    .build();
        } else {
            //无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cache, max-stale=" + maxStale)
                    .build();
        }
        return response;
    }
}
