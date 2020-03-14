package com.example.myapplication.livedata;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;

public class MyLiveDataActivity extends AppCompatActivity {
    MyViewModelLiveData myViewModelLiveData;
    TextView textView;
    ImageButton upBtn,downBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livedata);
        myViewModelLiveData = ViewModelProviders.of(this).get(MyViewModelLiveData.class);
        initView();
        myViewModelLiveData.getNumber().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                textView.setText(String.valueOf(integer));
            }
        });
    }

    public void initView(){
        textView = findViewById(R.id.textview);
        upBtn = findViewById(R.id.ibtn_up);
        downBtn = findViewById(R.id.ibtn_down);
        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModelLiveData.setNumber(1);
            }
        });
        downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModelLiveData.setNumber(-1);
            }
        });
    }
}
