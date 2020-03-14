package com.example.myapplication;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;


public class TestsActivity extends Activity {
    private NotificationManager notificationManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        showNotificaiton();
    }


    public void showNotificaiton(){
        //获取大图标
        Bitmap LargeBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        //定义一个PendingIntent点击Notification后启动一个Activity
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =PendingIntent.getActivity(this,0,intent,0);
        //设置通知内容
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("来自远方的通知")
                .setContentText("今早9：00你才起床")
                .setSubText("具体发生了什么谁知道呢？")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(LargeBitmap)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)    //设置默认的三色灯与振动器
                .setDefaults(Notification.DEFAULT_SOUND)    //设置系统的提示音
                .setAutoCancel(true)      //设置点击后取消Notification
                .setContentIntent(pendingIntent);
        Notification notification = builder.build();
        notificationManager.notify(1,notification);
    }
}
