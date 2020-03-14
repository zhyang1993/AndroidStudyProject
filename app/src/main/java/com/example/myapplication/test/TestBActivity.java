package com.example.myapplication.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class TestBActivity extends AppCompatActivity {
    private Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testb);
        Intent intent = new Intent();
        intent.putExtra("result","1");
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("zhyang", "onPause: b");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("zhyang", "onStop: b");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("zhyang", "onDestroy: b");
    }
}
