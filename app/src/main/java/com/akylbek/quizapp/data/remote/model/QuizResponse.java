package com.akylbek.quizapp.data.remote.model;

import com.akylbek.quizapp.model.Question;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuizResponse {

    @SerializedName("results")
    List<Question> questions;

    public QuizResponse(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
