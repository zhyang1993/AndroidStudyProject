package com.example.myapplication.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.List;

public class Bactivity extends AppCompatActivity {
    private TextView tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        tv = findViewById(R.id.tv);
        Intent intent = getIntent();
//        String dateFromA = intent.getStringExtra("fromA");
//        User user = (User) intent.getSerializableExtra("user");
//        List<User> users = (List<User>) intent.getSerializableExtra("user");
//        Book book = intent.getParcelableExtra("parcelable");
        List<Book> books =intent.getParcelableArrayListExtra("parcelable");
        tv.setText("从A传递过来的数据："+ books.size());
    }
    public void  getDataBySharedPreferenc(){
        SharedPreferences sharedPreferences = getSharedPreferences("zhyang", Context.MODE_PRIVATE);
        String str = sharedPreferences.getString("user","0");

    }

}
