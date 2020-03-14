package com.example.myapplication.livedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModelLiveData extends ViewModel {
    private MutableLiveData<Integer> number;

    public MutableLiveData<Integer> getNumber(){
        if(number == null){
            number = new MutableLiveData<>();
            number.setValue(0);
        }
        return number;
    }

    public void setNumber(int num){
        if(number!=null){
            number.setValue(number.getValue()+num);
        }
    }
}
