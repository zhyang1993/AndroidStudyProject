package com.example.myapplication.roomdemo;

import android.content.Context;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {
    private WordDataBase wordDataBase;
    private WordDao wordDao;
    private LiveData<List<Word>> allWordsLiveData;

    public WordRepository(Context context) {
        wordDataBase = WordDataBase.getINSTANCE(context);
        wordDao = wordDataBase.getWordDao();
        allWordsLiveData = wordDao.findWords();
    }

    public LiveData<List<Word>> getAllWordsLiveData() {
        return allWordsLiveData;
    }

    void insertWord(Word... words) {
        new InsterAsnycTask(wordDao).execute(words);
    }

    void UpdateWord(Word... words) {
        new UpdateAsnycTask(wordDao).execute(words);
    }

    void deleteWord(Word... words) {
        new DeleteAsnycTask(wordDao).execute(words);
    }
    void deleteAllWords(){
        new DeleteAllAsnycTask(wordDao).execute();
    }

    //AsyncTask类型的内部类需要静态类，因为会使内存泄漏
    static class InsterAsnycTask extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;

        public InsterAsnycTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.insertWords(words);
            return null;
        }
    }

    static class UpdateAsnycTask extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;

        public UpdateAsnycTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.updateWord(words);
            return null;
        }
    }

    static class DeleteAsnycTask extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;

        public DeleteAsnycTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.deleteWord(words);
            return null;
        }
    }

    static class DeleteAllAsnycTask extends AsyncTask<Void, Void, Void> {
        private WordDao wordDao;

        public DeleteAllAsnycTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.deleteAllWords();
            return null;
        }
    }
}
