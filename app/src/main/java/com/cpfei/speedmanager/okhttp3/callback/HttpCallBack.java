package com.cpfei.speedmanager.okhttp3.callback;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @Author: cpfei
 * @CreateDate: 2020-07-31
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020-07-31
 * @description:
 */
public class HttpCallBack implements Callback {


    @Override
    public void onFailure(Call call, IOException e) {
        Log.d("Http", "onFailure = " + e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

        if (response.code() >= 200 && response.code() <= 300) {

            String responseStr = response.body().toString();
            Log.d("Http", "responseCode = " + response.code() + ", json = " + responseStr);

        } else {
            Log.d("Http", "responseCode = " + response.code());
        }

    }



}
