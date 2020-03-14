package com.example.myapplication.mvc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.bean.Account;
import com.example.myapplication.callback.UserInfoCallBack;

public class NormalActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText;
    private TextView textView;
    private Button button;
    private McvModel accountModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        initView();
        accountModel = new McvModel();
    }

    public void initView(){
        editText = findViewById(R.id.edit_query);
        textView = findViewById(R.id.tv_showinfo);
        button   = findViewById(R.id.btn_getinfo);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        accountModel.getAccount(getUserInput(), new UserInfoCallBack() {
            @Override
            public void onSuccess(Account account) {
                showSuccessUser(account);

            }

            @Override
            public void onFailure() {
                showFailureUser();
            }
        });
    }

    public String getUserInput(){
        return editText.getText().toString().trim();
    }

    public void showSuccessUser(Account account){
        textView.setText("用户昵称为："+account.getUserName()+"，级别："+account.getUserLevel());
    }
    public void showFailureUser(){
        textView.setText("用户信息不存在");
    }
}
