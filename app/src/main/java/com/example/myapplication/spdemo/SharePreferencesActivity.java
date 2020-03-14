package com.example.myapplication.spdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class SharePreferencesActivity extends AppCompatActivity {
    //commit() 有返回值，成功返回 true，失败返回 false。而 apply() 没有返回值。
    //commit() 方法是同步提交到硬件磁盘，因此，在多个并发的提交 commit 的时候，他们会等待正在处理的 commit 保存到磁盘后在操作，从而降低了效率。
    //apply() 是将修改的数据提交到内存，而后异步真正的提交到硬件磁盘。
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = getSharedPreferences("mydata",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("NUMBER",100);
        editor.apply();
        String  json ="{"+"name"+":"+"1"+","+"number"+":"+"2"+"}";
        try {
            JSONObject jsonObject = new JSONObject(json);
            jsonObject.get("name");
            jsonObject.opt("name");
            jsonObject.optString("name");
            jsonObject.optInt("number");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
