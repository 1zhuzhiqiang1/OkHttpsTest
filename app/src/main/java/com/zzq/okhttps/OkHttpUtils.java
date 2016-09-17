package com.zzq.okhttps;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/7/22.
 */
public class OkHttpUtils {

    private static OkHttpClient okHttpClient = null;

    public static void init() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                    .readTimeout(10000L, TimeUnit.MILLISECONDS)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String s, SSLSession sslSession) {
                            return true;
                        }
                    })
                    .build();
        }
    }

    public static void setCertificate(InputStream is, HttpsUtils.SSLParams sslParams) {
        try {
            okHttpClient = okHttpClient.newBuilder()
                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * POST请求方式：在主线程中执行
     *
     * @param path
     * @param callback 回调函数在Okhttp的分线程执行，可以在onResponse()方法中做耗时操作，
     *                 如果要操作UI的haunted需要在main线程中执行
     */
    public static void get(String path, final Callback callback) {
        final Request request = new Request.Builder().url(path).get().build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * POST方式请求数据：在主线程中执行
     *
     * @param path
     * @param params
     * @param callBack
     */
    public static void post(String path, HashMap<String, String> params, Callback callBack) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.addFormDataPart(key, params.get(key));
            }
        }
        final Request request = new Request.Builder().url(path).post(builder.build()).build();
        okHttpClient.newCall(request).enqueue(callBack);
    }
}
