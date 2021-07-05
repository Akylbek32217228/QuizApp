package com.akylbek.quizapp.data.history;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.akylbek.quizapp.model.QuizResult;
import com.akylbek.quizapp.model.ShortQuizResult;

import java.util.List;

public interface IHistoryStorage {

    int saveQuizResult(QuizResult quizResult);

    LiveData<QuizResult> getQuizResult(int id);

    void deleteQuizResult(int pos);

    LiveData<List<QuizResult>> getAll();

    void deleteAll();

    LiveData<List<ShortQuizResult>> getAllShort();

}
