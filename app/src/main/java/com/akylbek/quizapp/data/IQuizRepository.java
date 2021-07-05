package com.akylbek.quizapp.data;

import androidx.lifecycle.LiveData;

import com.akylbek.quizapp.model.Question;

import java.util.List;

public interface IQuizRepository {

    void getQuiz(int amount, int category, String difficulty, QuestionsCallback callback);


    interface QuestionsCallback {

        void onSuccess(List<Question> result);

        void onFailure(String message);

    }

}
