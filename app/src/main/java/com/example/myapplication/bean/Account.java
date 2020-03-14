package com.example.myapplication.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.myapplication.BR;


public class Account extends BaseObservable {
    private String UserName;
    private String UserLevel;
    public Account(){};
    public Account(String userName,String userLevel){
        this.UserName = userName;
        this.UserLevel = userLevel;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
    @Bindable
    public String getUserLevel() {
        return UserLevel;
    }

    public void setUserLevel(String userLevel) {
        UserLevel = userLevel;
        //必须加他才能在页面上更新数据
        notifyPropertyChanged(BR._all);
    }
}
