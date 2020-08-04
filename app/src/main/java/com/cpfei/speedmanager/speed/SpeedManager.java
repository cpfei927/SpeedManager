package com.cpfei.speedmanager.speed;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @Author: cpfei
 * @CreateDate: 2020-07-30
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020-07-30
 * @description:
 */
public class SpeedManager {

    private OkHttpClient client;
    private Call call;

    Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //拦截
            Response originalResponse = chain.proceed(chain.request());
            //包装响应体并返回
            return originalResponse;
        }
    };

    public SpeedManager() {

        client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS) //设置超时，不设置可能会报异常
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .eventListenerFactory(HttpEventListener.FACTORY)
                .build();
    }


    public SpeedLog getSpeedParams(String url) {
        try {

            SpeedLog speedLog = new SpeedLog();
            speedLog.startTime = System.currentTimeMillis() + "";

            Request request = new Request.Builder()
                    .url(url)
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();

            call = client.newCall(request);
            Response response = call.execute();

            speedLog.endTime = System.currentTimeMillis() + "";

            ResponseBody body = response.body();


            speedLog.pageSize = (body.contentLength() / 1024) + "";
            speedLog.httpcode = response.code() + "";


//            call.enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    readBytesFromStream(response.body().byteStream());
//                }
//            });






        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }




}
