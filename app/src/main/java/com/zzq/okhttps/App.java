package com.zzq.okhttps;

import android.app.Application;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpUtils.init();
        InputStream inputStream = null;
        try {
            inputStream = getAssets().open("root.crt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(new InputStream[]{inputStream}, null, null);
        OkHttpUtils.setCertificate(inputStream, sslParams);

    }
}
