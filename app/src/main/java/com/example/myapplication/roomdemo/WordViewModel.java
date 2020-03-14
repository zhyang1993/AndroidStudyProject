package com.example.myapplication.roomdemo;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private WordRepository wordRepository;

    public WordViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application);
    }

    void insertWord(Word... words) {
        wordRepository.insertWord(words);
    }

    void UpdateWord(Word... words) {
        wordRepository.UpdateWord(words);
    }

    void deleteWord(Word... words) {
        wordRepository.deleteWord(words);
    }

    void deleteAlltWord() {
        wordRepository.deleteAllWords();
    }

    public LiveData<List<Word>> getAllWordsLiveData() {
        return wordRepository.getAllWordsLiveData();
    }


}
