package com.akylbek.quizapp.data.remote;

import com.akylbek.quizapp.data.IQuizRepository;

public interface IQuizRemoteStorage {

    void getQuiz(int amount, int category, String difficulty, IQuizRepository.QuestionsCallback callback);

}
