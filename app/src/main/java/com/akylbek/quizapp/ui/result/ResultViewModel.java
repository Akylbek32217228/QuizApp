package com.akylbek.quizapp.ui.result;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.akylbek.quizapp.App;
import com.akylbek.quizapp.SingleLiveEvent;
import com.akylbek.quizapp.model.QuizResult;
import com.akylbek.quizapp.ui.quiz.QuizActivity;

import java.util.List;

public class ResultViewModel extends ViewModel {

    SingleLiveEvent closeResult = new SingleLiveEvent();
    LiveData<QuizResult> quizActivityLiveData;
    public void closeResult() {
        closeResult.call();
    }

    public void setId(int id){

        quizActivityLiveData = App.historyStorage.getQuizResult(id);

    }

}
