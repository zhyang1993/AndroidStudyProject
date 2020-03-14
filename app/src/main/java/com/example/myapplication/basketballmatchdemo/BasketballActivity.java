package com.example.myapplication.basketballmatchdemo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityBasketballBinding;

public class BasketballActivity extends AppCompatActivity {
    private BasketballViewModel basketballViewModel;
    private ActivityBasketballBinding basketballBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        basketballBinding = DataBindingUtil.setContentView(this, R.layout.activity_basketball);
        basketballViewModel = ViewModelProviders.of(this).get(BasketballViewModel.class);
        basketballBinding.setData(basketballViewModel);
        basketballBinding.setLifecycleOwner(this);
    }
}
