package com.example.myapplication.okhttpdemo.request;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestParams {
    public ConcurrentHashMap<String,String> urlParams = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String,Object> fileParams = new ConcurrentHashMap<>();
    public RequestParams(Map<String,String> sourceUrl){
        if(sourceUrl != null){
            for (Map.Entry<String,String> entry :sourceUrl.entrySet()){
                putUrl(entry.getKey(),entry.getValue());
            }

        }
    }
//    public RequestParams(Map<String,Object> source){
//        if(source != null){
//            for (Map.Entry<String,Object> entry :source.entrySet()){
//                putFile(entry.getKey(),entry.getValue());
//            }
//
//        }
//    }


    public void putUrl(String key,String value){
        urlParams.put(key,value);
    }
    public void putFile(String key,Object value){
        fileParams.put(key,value);
    }
}
