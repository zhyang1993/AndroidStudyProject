package com.example.myapplication.okhttpdemo.request;

import java.util.Map;

import okhttp3.Request;

public class CommonRequest {

    public static Request createGetRequest(String url,RequestParams params){
        StringBuilder stringBuilder = new StringBuilder(url).append("?");
        if(params != null){
            for (Map.Entry<String,String> entry : params.urlParams.entrySet()){
                stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }

        }
      return new Request.Builder().url(stringBuilder.substring(0,stringBuilder.length()-1)).get().build();
    }
}
