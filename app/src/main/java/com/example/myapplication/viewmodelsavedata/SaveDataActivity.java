package com.example.myapplication.viewmodelsavedata;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivitySavedataBinding;
/*
*
* 当app被后台kill的时候，viewmodel无法保存，下面方法可以保存
* 1.build.grade导入依赖implementation 'androidx.lifecycle:lifecycle-viewmodel-savedstate:1.0.0-alpha05'
* 2.ViewModelProviders.of(this, new SavedStateViewModelFactory(getApplication(),this)).get(ViewModelSaveDatas.class);
* 3.viewmodel中的处理
* 4.手机调整到开发者模式，然后在后台限制中选择不允许后台运行
* */
public class SaveDataActivity extends AppCompatActivity {
    private ActivitySavedataBinding activitySavedataBinding;
    private ViewModelSaveDatas viewModelSaveDatas;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySavedataBinding =  DataBindingUtil.setContentView(this,R.layout.activity_savedata);
        viewModelSaveDatas = ViewModelProviders.of(this, new SavedStateViewModelFactory(getApplication(),this)).get(ViewModelSaveDatas.class);
        activitySavedataBinding.setData(viewModelSaveDatas);
        activitySavedataBinding.setLifecycleOwner(this);
    }
}
