package com.example.myapplication.viewmodelsavedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class ViewModelSaveDatas extends ViewModel {
    private MutableLiveData<Integer> teamA;
    private SavedStateHandle handle;

    public ViewModelSaveDatas(SavedStateHandle handle) {
        if (!handle.contains("NUMBER")) {
            handle.set("NUMBER", 0);
        }
        this.handle = handle;
    }

    public MutableLiveData<Integer> getTeamA() {
        return handle.getLiveData("NUMBER");
    }

    public void addNumber() {
        handle.set("NUMBER", (int) handle.get("NUMBER") + 1);
    }
}
