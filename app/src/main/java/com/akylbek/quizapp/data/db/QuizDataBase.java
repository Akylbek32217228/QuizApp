package com.akylbek.quizapp.data.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.akylbek.quizapp.data.history.HistoryDao;
import com.akylbek.quizapp.model.QuizResult;


@Database(entities = QuizResult.class, version = 1, exportSchema = false)
public abstract class QuizDataBase extends RoomDatabase {

    public abstract HistoryDao historyDao();

}
