package com.example.myapplication.mvp;

import com.example.myapplication.bean.Account;

public interface IMvpView {
    String getUserInput();
    void showSuccessUser(Account account);
    void showFailureUser();
}
