package com.example.myapplication.mvp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.bean.Account;

public class MvpActivity extends AppCompatActivity implements View.OnClickListener,IMvpView{
    MvpPresenter mvpPresenter;
    private EditText editText;
    private TextView textView;
    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        mvpPresenter = new MvpPresenter(this);
        initView();
    }

    public void initView(){
        editText = findViewById(R.id.edit_query);
        textView = findViewById(R.id.tv_showinfo);
        button   = findViewById(R.id.btn_getinfo);
        button.setOnClickListener(this);
    }

    @Override
    public String getUserInput() {
        return editText.getText().toString().trim();
    }

    @Override
    public void showSuccessUser(Account account) {
        textView.setText("用户昵称为："+account.getUserName()+"，级别："+account.getUserLevel());
    }

    @Override
    public void showFailureUser() {
        textView.setText("用户信息不存在");
    }

    @Override
    public void onClick(View v) {
        mvpPresenter.getData(getUserInput());
    }


}
