package com.example.myapplication.mvp;

import com.example.myapplication.bean.Account;
import com.example.myapplication.callback.UserInfoCallBack;

public class MvpPresenter {
    private IMvpView iMvpView;
    private MvpModel mvpModel;

    public MvpPresenter (IMvpView iMvpView){
        this.iMvpView = iMvpView;
        mvpModel = new MvpModel();
    }

    public void getData(String userName){
        mvpModel.getAccount(userName, new UserInfoCallBack() {
            @Override
            public void onSuccess(Account account) {
                iMvpView.showSuccessUser(account);
            }

            @Override
            public void onFailure() {
              iMvpView.showFailureUser();
            }
        });
    }
}
