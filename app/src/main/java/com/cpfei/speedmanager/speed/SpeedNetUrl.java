package com.cpfei.speedmanager.speed;

import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: cpfei
 * @CreateDate: 2020-07-29
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020-07-29
 * @description:
 */
public class SpeedNetUrl {


    public static List<UrlItem> getSpeedUrlItemList(){
        // 此处网络请求获取测速地址

        List<UrlItem> urlItems = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            UrlItem urlItem = new UrlItem();
            urlItem.id = "url_index = " + i;
            urlItem.url = "";
            urlItems.add(urlItem);
        }

        return urlItems;
    }


    public static class UrlItem implements Serializable {

        /**
         * url id
         */
        public String id;

        /**
         * url地址
         */
        public String url;
    }


}
