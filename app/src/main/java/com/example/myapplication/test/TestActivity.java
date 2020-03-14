package com.example.myapplication.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class TestActivity extends Activity {
    private String TAG = "zhyang";
    private int REQUEST_CODE = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        Log.e(TAG,"Activity is onCreate");

        findViewById(R.id.btn_finsh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // finish();
                Intent intent =new Intent(TestActivity.this,TestBActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TestActivity.this);
//                alertDialogBuilder.setMessage("ActivityA失去焦点");
//                alertDialogBuilder.setTitle("Title");
//                alertDialogBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                  Intent intent =new Intent(TestActivity.this,TestBActivity.class);
//                  startActivity(intent);
//                    }
//                });
//                alertDialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                alertDialogBuilder.create().show();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG,"Activity is onSaveInstanceState");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,"Activity is onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG,"Activity is onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"Activity is onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG,"Activity is onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG,"Activity is onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"Activity is onDestroy");
    }

    @Override
    public void finish() {
        super.finish();
        Log.e(TAG,"Activity is finish");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG,"requestCode:"+requestCode+" / resultCode:"+resultCode);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
           String str = data.getStringExtra("result");
            Log.e(TAG,"result:"+str);
        }
    }
}
