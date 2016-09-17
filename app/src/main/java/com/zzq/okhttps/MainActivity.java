package com.zzq.okhttps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

    }

    private void initData() {

        String url = "https://101.201.114.67/anyware/app/platform/authentication/login";

        HashMap<String, String> params = new HashMap<>();
        params.put("username", "huangzhen");
        params.put("password", "admin1234");
        OkHttpUtils.post(url, params, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_content.setText(e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String content = response.body().string();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_content.setText(content);
                    }
                });
            }
        });

    }

    private void initView() {
        tv_content = (TextView) findViewById(R.id.tv_content);
    }
}
