package com.example.myapplication.okhttpdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity {
    private Button btnRequest;
    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        initView();
        initClick();
    }

    public void initView(){
        btnRequest = findViewById(R.id.btn_request);
        textView = findViewById(R.id.tv_content);
    }

    public void initClick(){
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getRequest();
            }
        });

    }

    /*
    * 发送一个get请求
    * */

    public void getRequest(){
        Request request = new Request.Builder().url("https://www.baidu.com").build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            final String reposnStr = response.body().string();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText(reposnStr);
                }
            });
            }
        });
    }

    public void postRequest(){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBodyBuild = new FormBody.Builder();
        formBodyBuild.add("user","13072494008");
        formBodyBuild.add("pwd","dsdsaads");

        Request request = new Request.Builder().url("dsadsads").post(formBodyBuild.build()).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });
    }
}
