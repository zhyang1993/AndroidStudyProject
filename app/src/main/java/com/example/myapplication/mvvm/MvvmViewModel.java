package com.example.myapplication.mvvm;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.bean.Account;
import com.example.myapplication.callback.UserInfoCallBack;

public class MvvmViewModel extends ViewModel {
    private MutableLiveData<String> result;
    private MvvmModel mvvmModel;
    private MutableLiveData<String> userInput;
    public void getData(View view){
        mvvmModel = new MvvmModel();
        mvvmModel.getAccount(getUserInput().getValue(), new UserInfoCallBack() {
            @Override
            public void onSuccess(Account account) {
                setResult(account.getUserName()+"|"+account.getUserLevel());
            }

            @Override
            public void onFailure() {
                setResult("获取用户信息失败");
            }
        });
    }

    public MutableLiveData<String> getResult() {
        if(result == null){
            result = new MutableLiveData<>();
        }
        return result;
    }

    public void setResult(String str) {
        result.setValue(str);
    }

    public MutableLiveData<String> getUserInput() {
        if(userInput == null){
            userInput = new MutableLiveData<>();
        }
        return userInput;
    }

    public void setUserInput(String userInputText) {
        userInput.setValue(userInputText);
    }
}
