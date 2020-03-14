package com.example.myapplication.callback;

import com.example.myapplication.bean.Account;

public interface UserInfoCallBack {
    void onSuccess(Account account);
    void onFailure();
}
