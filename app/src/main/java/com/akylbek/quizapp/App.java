package com.akylbek.quizapp;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.akylbek.quizapp.data.IQuizRepository;
import com.akylbek.quizapp.data.QuizRepository;
import com.akylbek.quizapp.data.db.QuizDataBase;
import com.akylbek.quizapp.data.history.HistoryStorage;
import com.akylbek.quizapp.data.history.IHistoryStorage;
import com.akylbek.quizapp.data.local.QuizLocalStorage;
import com.akylbek.quizapp.data.remote.QuizRemoteStorage;

public class App extends Application {

    public static IQuizRepository repository;
    public static QuizDataBase database;
    public static IHistoryStorage historyStorage;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        database = Room
                .databaseBuilder(this, QuizDataBase.class, "quiz")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        context = this;

        repository = new QuizRepository(new QuizRemoteStorage(), new QuizLocalStorage());
        historyStorage = new HistoryStorage(database.historyDao());

    }

    public static Context getContext() {
        return context;
    }
}
