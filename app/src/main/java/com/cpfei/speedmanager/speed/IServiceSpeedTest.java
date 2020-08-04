package com.cpfei.speedmanager.speed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;


import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class IServiceSpeedTest implements IService {

    public final static String PREFS_KEY_LAST_TIME = "sp_speed_last_time_key";

    public final static int SPEED_TEST_INTERBAL = 86400000;

    public final static String SPEEDTEST_ACTION = "com.cpfei.speedmanager.SPEED";

    public static final String KEY_USER = "key_user";

    private Context mContext;

    private SharedPreferences mSpManager;

    private boolean onGoing = false;

    public IServiceSpeedTest(Context context) {
        mContext = context.getApplicationContext();
    }

    @Override
    public boolean checkBeforeStart(Intent intent, int startId) {
        if (intent == null) {
            return false;
        }
        if (onGoing == true) {
            return false;
        } else {
            onGoing = true;
        }
        long lastTime = mSpManager.getLong(PREFS_KEY_LAST_TIME, 0);
        Date now = new Date();
        if (now.getTime() - lastTime > SPEED_TEST_INTERBAL) {
            return true;
        }
        return false;
    }

    @Override
    public void doWhenCreate() {
        mSpManager = mContext.getSharedPreferences("sp_speed_file", Context.MODE_PRIVATE);
    }

    @Override
    public void doWhenStart(Intent intent, int startId) {
        if (checkBeforeStart(intent, startId)) {
            List<SpeedLog> logList = new ArrayList<SpeedLog>();
            String lineType = "";
            NetworkUtils.NetworkType networkType = NetworkUtils.getNetworkType(mContext);
            switch (networkType) {
                case NETWORK_WIFI:
                    lineType = "wifi";
                    break;
                case NETWORK_4G:
                case NETWORK_3G:
                    lineType = "mobile";
                default:
                    // do nothing;
            }
            // 没有正确的网络连接
            if (TextUtils.isEmpty(lineType)) {
                onGoing = false;
                return;
            }

            List<SpeedNetUrl.UrlItem> urlItems = SpeedNetUrl.getSpeedUrlItemList();

            if (urlItems == null) {
                onGoing = false;
                return;
            }

            //V4已经没有push字段，这段代码会发送广播，但没有地方接受，所以废弃
                    /*int local_value = mSettings.getInt( SharePrefManager.PREFS_KEY_IS_PUSH, 0 );
                    if (local_value != urlItemList.isPush) {
                        mContext.sendBroadcast( new Intent( Constants.CHANGE_GETMESSAGE_METHORD )
                                .putExtra( PushService.PUSH_OR_API, urlItemList.isPush ) );
                    }*/

            for (SpeedNetUrl.UrlItem urlItem : urlItems) {

                String url = urlItem.url;
                String urlId = urlItem.id;

                if (TextUtils.isEmpty(url) || TextUtils.isEmpty(urlId)) {
                    continue;
                } else {

                    // 访问url，获取部分参数：responseTime，linkTime，httpcode，pageSize
                    SpeedLog log = null;
//                                log = NetUtils.getSpeedParams(url, NetUtils.METHOD_GET, null,
//                                        mContext);


//                    new SpeedManager.Builder();



                    if (log == null) {
                        log = new SpeedLog();
                    }

                    // 解析url对应的ip并获取dns解析时间
                    String vip = "";
                    long dnsTime = 0;
                    try {
                        long dnsTimeBegin = System.currentTimeMillis();
                        vip = InetAddress.getByName(new URL(url).getHost())
                                .getHostAddress();
                        dnsTime = System.currentTimeMillis() - dnsTimeBegin;
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    // 设置日志参数
                    log.urlId = urlId;
                    log.lineType = String.valueOf(lineType);
                    log.dnsTime = String.valueOf(dnsTime);
                    log.vip = vip;
                    log.gwip = getGatewayIp();

                    logList.add(log);
                }
            }

            // 提交测试结果
            if (logList.size() > 0) {
                System.out.println();


            }
            SharedPreferences.Editor edit = mSpManager.edit();
            edit.putLong(PREFS_KEY_LAST_TIME, System.currentTimeMillis());
            edit.commit();
            onGoing = false;
        } else {
            onGoing = false;
        }
    }

    private String getGatewayIp() {
        WifiManager wifi_service = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifi_service.getDhcpInfo();
        return long2ip(dhcpInfo.gateway);
    }

    private static String long2ip(long ip) {
        int[] b = new int[4];
        b[0] = (int) ((ip >> 24) & 0xff);
        b[1] = (int) ((ip >> 16) & 0xff);
        b[2] = (int) ((ip >> 8) & 0xff);
        b[3] = (int) (ip & 0xff);
        String x;
        x = Integer.toString(b[3]) + "." + Integer.toString(b[2]) + "."
                + Integer.toString(b[1]) + "." + Integer.toString(b[0]);
        return x;
    }

    @Override
    public void doWhenDestroy() {
        onGoing = false;
    }


}
