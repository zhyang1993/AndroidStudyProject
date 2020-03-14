package com.example.myapplication.roomdemo;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Word> allWordsList = new ArrayList<>();
    private OnRecyclerViewItemClick onRecyclerViewItemClick;
    private boolean useCarView;
    private WordViewModel wordViewModel;
    public void setAllWordsList(List<Word> allWordsList){
        this.allWordsList = allWordsList;
    }
    public void setOnRecyclerViewItemClick(OnRecyclerViewItemClick onRecyclerViewItemClick){
        this.onRecyclerViewItemClick = onRecyclerViewItemClick;
    }
    public MyAdapter(boolean useCarView,WordViewModel wordViewModel){
        this.useCarView = useCarView;
        this.wordViewModel = wordViewModel;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;
        if(useCarView){
            itemView = layoutInflater.inflate(R.layout.layout_word_item_carview,parent,false);
        }else{
            itemView = layoutInflater.inflate(R.layout.layout_word_item,parent,false);
        }

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
       Word word = allWordsList.get(position);
       holder.tvId.setText(String.valueOf(position+1));
       holder.tvEnglish.setText(word.getWord());
       holder.tvEnglish.setText(word.getChineseMeaning());
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Uri uri = Uri.parse("https://baidu.com");
               Intent intent = new Intent(Intent.ACTION_VIEW);
               intent.setData(uri);
               holder.itemView.getContext().startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return allWordsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvId,tvEnglish,tvChinese;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_word_id);
            tvEnglish = itemView.findViewById(R.id.tv_english);
            tvChinese = itemView.findViewById(R.id.tv_chinese);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onRecyclerViewItemClick.onItemClick(getAdapterPosition(),allWordsList);
//                }
//            });
        }
    }


    public interface OnRecyclerViewItemClick{
        void onItemClick(int position,List<Word> wordList);
    }
}
