package com.akylbek.quizapp.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.Nullable;
import android.util.Log;

import com.akylbek.quizapp.data.local.IQuizLocalStorage;
import com.akylbek.quizapp.data.remote.IQuizRemoteStorage;
import com.akylbek.quizapp.model.Question;

import java.util.List;

public class QuizRepository implements IQuizRepository{
    @Nullable
    private IQuizRemoteStorage remoteStorage;
    @Nullable
    private IQuizLocalStorage localStorage;


    public QuizRepository(@Nullable IQuizRemoteStorage remoteStorage, @Nullable IQuizLocalStorage localStorage) {
        this.remoteStorage = remoteStorage;
        this.localStorage = localStorage;
    }
    @Nullable
    @Override
    public void getQuiz(int amount, int category, String difficulty, final QuestionsCallback callback) {
        if(remoteStorage != null) {
            remoteStorage.getQuiz(amount, category, difficulty, new QuestionsCallback() {
                @Override
                public void onSuccess(List<Question> result) {
                    callback.onSuccess(result);
                }

                @Override
                public void onFailure(String message) {
                    Log.d("ololo", message);
                }
            });
        }
    }
}
