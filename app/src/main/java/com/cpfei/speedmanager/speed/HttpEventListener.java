package com.cpfei.speedmanager.speed;

import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;

import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.HttpUrl;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author: cpfei
 * @CreateDate: 2020-07-30
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020-07-30
 * @description:
 */
public class HttpEventListener extends EventListener {

    /**
     * 每次请求的标识
     */
    private final long callId;

    /**
     * 每次请求的开始时间，单位纳秒
     */
    private final long callStartNanos;

    private StringBuilder sbLog;


    /**
     * 自定义EventListener工厂
     */
    public static final Factory FACTORY = new Factory() {
        final AtomicLong nextCallId = new AtomicLong(1L);

        @Override
        public EventListener create(Call call) {
            long callId = nextCallId.getAndIncrement();
            return new HttpEventListener(callId, call.request().url(), System.nanoTime());
        }
    };

    public HttpEventListener(long callId, HttpUrl url, long callStartNanos) {
        this.callId = callId;
        this.callStartNanos = callStartNanos;
        this.sbLog = new StringBuilder(" ").append(" ").append(callId).append(":");
    }

    private void recordEventLog(String name) {
        long elapseNanos = System.nanoTime() - callStartNanos;
        sbLog.append(String.format(Locale.CHINA, "%.3f-%s", elapseNanos / 1000000000d, name)).append(";");
        if (name.equalsIgnoreCase("callEnd") || name.equalsIgnoreCase("callFailed")) {
            //打印出每个步骤的时间点
            Log.d("HttpEventListener", "callId = " + callId + ", " +sbLog.toString());
        }
    }

    /**
     * 请求开始
     * 每一个callStart都对应着一个callFailed或callEnd。
     * @param call
     */
    @Override
    public void callStart(Call call) {
        super.callStart(call);
        recordEventLog("callStart");
    }


    /**
     * 请求结束
     * 第一种也是在关闭流时。
     * 第二种是在释放连接时。
     * @param call
     */
    @Override
    public void callEnd(Call call) {
        super.callEnd(call);
        recordEventLog("callEnd");
    }

    /**
     * 请求失败
     * 第一种是在请求执行的过程中发生异常时。
     * 第二种是在请求结束后，关闭输入流时产生异常时。
     * @param call
     * @param ioe
     */
    @Override
    public void callFailed(Call call, IOException ioe) {
        super.callFailed(call, ioe);
        recordEventLog("callFailed");
    }


    /**
     * dnsStart/dnsEnd dns解析开始/结束
     * @param call
     * @param domainName
     */
    @Override
    public void dnsStart(Call call, String domainName) {
        super.dnsStart(call, domainName);
        recordEventLog("dnsStart");
    }

    /**
     * dnsStart/dnsEnd dns解析开始/结束
     * @param call
     * @param domainName
     * @param inetAddressList
     */
    @Override
    public void dnsEnd(Call call, String domainName, List<InetAddress> inetAddressList) {
        super.dnsEnd(call, domainName, inetAddressList);
        recordEventLog("dnsEnd");
    }


    /**
     * connectStart/connectEnd 连接开始结束
     * 当连接被重用时，connectStart/connectEnd不会被调用。
     * 当请求被重定向到新的域名后，connectStart/connectEnd会被调用多次。
     * @param call
     * @param inetSocketAddress
     * @param proxy
     */
    @Override
    public void connectStart(Call call, InetSocketAddress inetSocketAddress, Proxy proxy) {
        super.connectStart(call, inetSocketAddress, proxy);
        recordEventLog("connectStart");
    }

    /**
     * connectStart/connectEnd 连接开始结束
     * @param call
     * @param inetSocketAddress
     * @param proxy
     * @param protocol
     */
    @Override
    public void connectEnd(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol) {
        super.connectEnd(call, inetSocketAddress, proxy, protocol);
        recordEventLog("connectEnd");
    }


    /**
     * secureConnectStart/secureConnectEnd TLS安全连接开始和结束
     * @param call
     */
    @Override
    public void secureConnectStart(Call call) {
        super.secureConnectStart(call);
        recordEventLog("secureConnectStart");
    }


    /**
     * secureConnectStart/secureConnectEnd TLS安全连接开始和结束
     * @param call
     * @param handshake
     */
    @Override
    public void secureConnectEnd(Call call,  Handshake handshake) {
        super.secureConnectEnd(call, handshake);
        recordEventLog("secureConnectEnd");
    }


    /**
     * connectionAcquired/connectReleased 连接绑定和释放
     * @param call
     * @param connection
     */
    @Override
    public void connectionAcquired(Call call, Connection connection) {
        super.connectionAcquired(call, connection);
        recordEventLog("connectionAcquired");
    }

    /**
     * connectionAcquired/connectReleased 连接绑定和释放
     * @param call
     * @param connection
     */
    @Override
    public void connectionReleased(Call call, Connection connection) {
        super.connectionReleased(call, connection);
        recordEventLog("connectionReleased");
    }


    @Override
    public void requestHeadersStart(Call call) {
        super.requestHeadersStart(call);
        recordEventLog("requestHeadersStart");
    }


    @Override
    public void requestHeadersEnd(Call call, Request request) {
        super.requestHeadersEnd(call, request);
        recordEventLog("requestHeadersEnd");
    }


    @Override
    public void responseHeadersStart(Call call) {
        super.responseHeadersStart(call);
        recordEventLog("responseHeadersStart");
    }

    @Override
    public void responseHeadersEnd(Call call, Response response) {
        super.responseHeadersEnd(call, response);
        recordEventLog("responseHeadersEnd");
    }

    @Override
    public void requestBodyStart(Call call) {
        super.requestBodyStart(call);
        recordEventLog("requestBodyStart");
    }

    @Override
    public void requestBodyEnd(Call call, long byteCount) {
        super.requestBodyEnd(call, byteCount);
        recordEventLog("requestBodyEnd");
    }



//    @Override
//    public void responseFailed(Call call, IOException ioe) {
//        super.responseFailed(call, ioe);
//        recordEventLog("responseFailed");
//    }

    @Override
    public void responseBodyStart(Call call) {
        super.responseBodyStart(call);
        recordEventLog("responseBodyStart");
    }


    @Override
    public void responseBodyEnd(Call call, long byteCount) {
        super.responseBodyEnd(call, byteCount);
        recordEventLog("responseBodyEnd");
    }



}
