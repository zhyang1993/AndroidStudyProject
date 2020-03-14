package com.example.myapplication.roomdemo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {
    @Insert
    void insertWords(Word... words);
    @Update
    void updateWord(Word... words);
    @Query("select * from word order by id desc")
    LiveData<List<Word>> findWords();
    @Delete
    void deleteWord(Word... words);
    @Query("delete from word")
    void deleteAllWords();
}
