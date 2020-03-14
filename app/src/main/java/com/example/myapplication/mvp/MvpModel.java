package com.example.myapplication.mvp;

import com.example.myapplication.bean.Account;
import com.example.myapplication.callback.UserInfoCallBack;

import java.util.Random;

public class MvpModel {
    public void getAccount(String userName, UserInfoCallBack userInfoCallBack) {
        Random random = new Random();
        Account account = new Account(userName,"200");
        boolean result = random.nextBoolean();
        if(result){
            userInfoCallBack.onSuccess(account);
        }else {
            userInfoCallBack.onFailure();
        }
    }
}
