package com.example.myapplication.mvc;

import com.example.myapplication.bean.Account;
import com.example.myapplication.callback.UserInfoCallBack;

import java.util.Random;

public class McvModel {

    public void getAccount(String userName, UserInfoCallBack userInfoCallBack) {
        Random random = new Random();
        Account account = new Account(userName,"100");
        boolean result = random.nextBoolean();
        if(result){
            userInfoCallBack.onSuccess(account);
        }else {
            userInfoCallBack.onFailure();
        }
    }
}
