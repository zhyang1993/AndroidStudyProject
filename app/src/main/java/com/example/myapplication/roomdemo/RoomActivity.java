package com.example.myapplication.roomdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class RoomActivity extends AppCompatActivity {
   private Button btnInsaert,btnUpdate,btnDeleteAll;
   private Switch aSwitch;
   private RecyclerView recyclerView;
   private WordViewModel wordViewModel;
   private MyAdapter myAdapter1,myAdapter2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        wordViewModel.getAllWordsLiveData().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                int temp = myAdapter1.getItemCount();
                myAdapter1.setAllWordsList(words);
                myAdapter2.setAllWordsList(words);
                //防止多次回调重新刷新视图，影响性能
                if(temp!=words.size()){
                    myAdapter1.notifyDataSetChanged();
                    myAdapter2.notifyDataSetChanged();
                }
            }
        });
        initView();
        initRecycler();
    }

    public void initView(){
        btnInsaert = findViewById(R.id.button9);
        btnUpdate = findViewById(R.id.button11);
        aSwitch = findViewById(R.id.switch1);
        btnDeleteAll = findViewById(R.id.button8);
        recyclerView = findViewById(R.id.recyclerView);
        btnInsaert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word1 = new Word("Hello","你好！");
                Word word2 = new Word("Hi","你好啊！");
                Word word3 = new Word("match","数学！");
                Word word4 = new Word("English","英语啊！");
                wordViewModel.insertWord(word1,word2,word3,word4);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word1 = new Word("How are you ?","最近怎么样！");
                word1.setId(34);
                wordViewModel.UpdateWord(word1);
            }
        });


        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordViewModel.deleteAlltWord();
            }
        });
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    recyclerView.setAdapter(myAdapter2);
                }else{
                    recyclerView.setAdapter(myAdapter1);
                }
            }
        });
    }
    public void initRecycler(){
        myAdapter1 = new MyAdapter(false,wordViewModel);
        myAdapter2 = new MyAdapter(true,wordViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter1);
    }
}
