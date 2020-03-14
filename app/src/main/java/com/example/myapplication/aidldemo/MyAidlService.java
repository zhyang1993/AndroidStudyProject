package com.example.myapplication.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyAidlService extends Service {

    //RemoteCallbackList是专门用于删除跨进程listener的接口，它是一个泛型，支持管理任意的AIDL接口
    private RemoteCallbackList<IOnNewBookListener> remoteCallbackList = new RemoteCallbackList<>();
    private List<Book> list = new ArrayList<>();
    private IMyAidlInterface.Stub iMyAidlInterface = new IMyAidlInterface.Stub() {
        @Override
        public List<Book> getAllBooks() throws RemoteException {
            return list;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            list.add(book);
            int n=remoteCallbackList.beginBroadcast();
            for(int i=0;i<n;i++){
                IOnNewBookListener l=remoteCallbackList.getBroadcastItem(i);
                if (l!=null){
                    try {
                        l.onNewBook(book);//服务端通过这个向客户端发送消息
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
            remoteCallbackList.finishBroadcast();
            Log.e("zhyang","---------服务端通过这个向客户端发送消息--------");
        }

        @Override
        public void resigerListener(IOnNewBookListener iOnNewBookListener) throws RemoteException {
            remoteCallbackList.register(iOnNewBookListener);
            Log.e("zhyang","---------注册成功--------");
        }

        @Override
        public void unresigerListener(IOnNewBookListener iOnNewBookListener) throws RemoteException {
            remoteCallbackList.unregister(iOnNewBookListener);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("zhyang","---------MyAidlService oncreate--------");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iMyAidlInterface;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
