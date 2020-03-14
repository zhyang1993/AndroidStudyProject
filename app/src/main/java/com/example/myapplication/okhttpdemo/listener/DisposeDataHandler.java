package com.example.myapplication.okhttpdemo.listener;
//处理真正的响应
public class DisposeDataHandler {
    public DisposeDataListener disposeDataListener = null;
    //字节码
    private Class<?> mClass = null;
    public DisposeDataHandler (DisposeDataListener disposeDataListener){
        this.disposeDataListener = disposeDataListener;
    }
    public DisposeDataHandler (DisposeDataListener disposeDataListener,Class<?> mClass){
        this.disposeDataListener = disposeDataListener;
        mClass = mClass;
    }
}
