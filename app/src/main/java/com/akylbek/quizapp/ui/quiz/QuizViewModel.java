package com.akylbek.quizapp.ui.quiz;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import com.akylbek.quizapp.App;
import com.akylbek.quizapp.SingleLiveEvent;
import com.akylbek.quizapp.data.IQuizRepository;
import com.akylbek.quizapp.model.Question;
import com.akylbek.quizapp.model.QuizResult;

import java.util.Date;
import java.util.List;

public class QuizViewModel extends ViewModel {

    MutableLiveData<List<Question>> questions = new MutableLiveData<>();
    MutableLiveData<List<Question>> questionsHistory = new MutableLiveData<>();
    MutableLiveData<Integer> currentQuestionPosition = new MutableLiveData<>();
    MutableLiveData<Boolean> loading = new MutableLiveData<>();
    SingleLiveEvent closeEvent = new SingleLiveEvent();
    MutableLiveData<Integer> resultEvent = new MutableLiveData<>();
    LiveData<QuizResult> result;

    private void finishQuiz() {
        QuizResult quizResult = new QuizResult(
                    questions.getValue(),
                    getCorrectAnswersAmount(),
                    new Date()
        );

        int id = App.historyStorage.saveQuizResult(quizResult);
        Log.d("ololo", id + " id finish quiz");
        resultEvent.setValue(id);
        closeEvent.call();
    }

    public void getQuiz(int amount, int category, String difficulty) {
        App.repository.getQuiz(amount, category, difficulty, new IQuizRepository.QuestionsCallback() {
            @Override
            public void onSuccess(List<Question> result) {
                loading.setValue(true);
                QuizViewModel.this.questions.setValue(result);
                currentQuestionPosition.setValue(0);
            }

            @Override
            public void onFailure(String message) {
                loading.setValue(false);
            }
        });
    }

    public void getQuizFromDatabase(int pos) {

         result = App.historyStorage.getQuizResult(pos);

    }

    private int getCorrectAnswersAmount() {
        int corrertAnswers = 0;
        for ( int i = 0; i < questions.getValue().size(); ++i) {
            if ( questions.getValue().get(i).getCorrectAnswer().equals(questions.getValue().get(i).getSelectedAnswer())) {
                ++corrertAnswers;
            }
        }
        return corrertAnswers;
    }

    void onNextClick() {
        int currentPosition = currentQuestionPosition.getValue() + 1;
        if (currentPosition == questions.getValue().size()) {
            finishQuiz();
        } else {
            currentQuestionPosition.setValue(currentPosition);
        }
    }

    void onBackPressed() {
        int currentPosition = currentQuestionPosition.getValue() - 1;
        if (currentPosition >= 0) {
            currentQuestionPosition.setValue(currentPosition);
        } else {
            closeEvent.call();
        }
    }

    void onAnswerSelected(int position, String answer) {
        List<Question> list = questions.getValue();
        list.get(position).setSelectedAnswer(answer);
        list.get(position).setAccepted(true);
        questions.setValue(list);
    }

    String getTitle() {
        if (questions.getValue() != null && currentQuestionPosition.getValue() != null) {
            return questions.getValue()
                    .get(currentQuestionPosition.getValue())
                    .getCategory();
        } else {
            return "";
        }
    }


}
