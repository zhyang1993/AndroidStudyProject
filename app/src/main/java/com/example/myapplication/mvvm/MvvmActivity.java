package com.example.myapplication.mvvm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMvvmBinding;

public class MvvmActivity extends AppCompatActivity {
    private ActivityMvvmBinding activityMvvmBinding;
    private MvvmViewModel mvvmViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMvvmBinding = DataBindingUtil.setContentView(this,R.layout.activity_mvvm);
        activityMvvmBinding.setActivity(this);
        mvvmViewModel = ViewModelProviders.of(this).get(MvvmViewModel.class);
        activityMvvmBinding.setViewmodel(mvvmViewModel);
        activityMvvmBinding.setLifecycleOwner(this);
    }

}
