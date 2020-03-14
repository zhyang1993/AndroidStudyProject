package com.example.myapplication.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Aactivity extends AppCompatActivity {
    private Button btn;
    private TextView tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        btn = findViewById(R.id.btn);
        tv = findViewById(R.id.tv);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Aactivity.this,Bactivity.class);
//                /*第一种简单传递数据*/
//                intent.putExtra("fromA","easy");
//                startActivity(intent);
//                /*第二种使用bundle传递数据*/
//                Bundle bundle = new Bundle();
//                bundle.putString("fromA","bundle");
//                intent.putExtras(bundle);
//                startActivity(intent);
//                /*使用Bundle传递单个对象*/
//                User user = new User("zhyang");
//                Bundle bundles = new Bundle();
//                bundles.putSerializable("user",user);
//                intent.putExtras(bundles);
//                startActivity(intent);
//                /*使用Bundle传递集合对象*/
//                User user1 = new User("zhyang");
//                User user2 = new User("liyang");
//                List<User> lists = new ArrayList<>();
//                lists.add(user1);
//                lists.add(user2);
//                Bundle bundles1 = new Bundle();
//                bundles1.putSerializable("user", (Serializable) lists);
//                intent.putExtras(bundles1);
//                startActivity(intent);
                Book book = new Book();
                book.setName("Chinese");
                Book book1 = new Book();
                book1.setName("Match");
                List<Book> list = new ArrayList<>();
                list.add(book);
                list.add(book1);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("parcelable", (ArrayList<? extends Parcelable>) list);
                //bundle.putParcelable("parcelable",book);
                intent.putExtras(bundle);
                startActivity(intent);
                saveDataBySharedPreferenc();
            }
        });
    }

    public void  saveDataBySharedPreferenc(){
        SharedPreferences sharedPreferences = getSharedPreferences("zhyang", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("user","zhyang");
        editor.apply();
    }
}
