package com.example.myapplication.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapplication.IMyService;

public class RemoteService extends Service {
    private IMyService.Stub iMyServiceIBinder = new IMyService.Stub() {
        @Override
        public void connection() throws RemoteException {
            Log.e("zhyang","----链接成功----");
        }

        @Override
        public void stopConnection() throws RemoteException {
            Log.e("zhyang","----停止链接----");
        }
    };
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iMyServiceIBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

}
