package com.akylbek.quizapp.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question {

    private String category;

    private TypeEnum type;

    private DifficultyEnum difficulty;

    private String question;

    @SerializedName("correct_answer")
    private String correctAnswer;

    @SerializedName("incorrect_answers")
    private List<String> incorrectAnswers;

    private String selectedAnswer;

    private ArrayList<String> questionsShuffle = new ArrayList<>();

    private boolean isAccepted;

    public Question(String category,
                    TypeEnum type,
                    DifficultyEnum difficulty,
                    String question,
                    String correctAnswer,
                    List<String> incorrectAnswers) {
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public ArrayList<String> getQuestionsShuffle() {
        return questionsShuffle;
    }

    public void setQuestionsShuffle(ArrayList<String> questionsShuffle) {
        this.questionsShuffle = questionsShuffle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public DifficultyEnum getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(DifficultyEnum difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(List<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
}
