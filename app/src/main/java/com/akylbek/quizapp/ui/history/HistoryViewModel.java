package com.akylbek.quizapp.ui.history;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.akylbek.quizapp.App;
import com.akylbek.quizapp.model.QuizResult;
import com.akylbek.quizapp.model.ShortQuizResult;

import java.util.List;

import static com.akylbek.quizapp.App.historyStorage;

public class HistoryViewModel extends ViewModel {

    LiveData<List<ShortQuizResult>> history = historyStorage.getAllShort();
    LiveData<QuizResult> quizResultLiveDataOpen;


    public void setQuizResult(int pos) {
        App.historyStorage.deleteQuizResult(pos);
    }

    public void openQuiz(int pos) {
        quizResultLiveDataOpen = historyStorage.getQuizResult(pos);

    }
}
