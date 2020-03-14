package com.example.myapplication.databinding;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.myapplication.R;
import com.example.myapplication.bean.Account;

public class DemoActivity extends AppCompatActivity {
    private Account account;
    ActivityDemoBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_demo);
        //用来更新数据
        binding.setActivity(this);
        account = new Account("Test","100");
        binding.setAccount(account);

    }

    public void onclick(View view){
        account.setUserLevel(String.valueOf(Integer.parseInt(account.getUserLevel())+1));
        Toast.makeText(this, "点击了按钮", Toast.LENGTH_SHORT).show();
    }
}
