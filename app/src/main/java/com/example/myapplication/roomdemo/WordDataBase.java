package com.example.myapplication.roomdemo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Word.class},version = 4,exportSchema = false)
public abstract class WordDataBase extends RoomDatabase {
    private static WordDataBase INSTANCE;
    public static WordDataBase getINSTANCE(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context,WordDataBase.class,"word_database").allowMainThreadQueries().
                    //fallbackToDestructiveMigration()
                    addMigrations(migration3_4)
                    .build();
        }
        return INSTANCE;
    }
    public abstract WordDao getWordDao();
    //依然要修改版本号！！！！
    //表中添加字段
    // 版本迁移，损坏原来表的数据，原来表的数据被清空fallbackToDestructiveMigration()
    // 版本迁移，不损坏原来表的数据，向原来表中添加数据

    static  final Migration migration2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
          database.execSQL("ALTER TABLE word ADD COLUMN bar_data INTEGER NOT NULL DEFAULT 1");
        }
    };
    //表中删除字段 1.先建议一个新表自己想要的字段，然后把数据拷贝到新表中 2.把旧数据删除，再把新表改革名字
    //基于现有数据不丢失
    static  final Migration migration3_4 = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE word_temp (id INTEGER PRIMARY KEY NOT NULL,english_word TEXT," +
                    "chinese_meaning TEXT)");
            database.execSQL("INSERT INTO word_temp (id,english_word,chinese_meaning) " +
                    "SELECT id,english_word,chinese_meaning FROM word");
            database.execSQL("DROP TABLE word");
            database.execSQL("ALTER TABLE word_temp RENAME TO word");
        }
    };
}
