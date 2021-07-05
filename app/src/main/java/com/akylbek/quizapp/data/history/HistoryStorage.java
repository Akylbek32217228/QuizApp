package com.akylbek.quizapp.data.history;


import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.akylbek.quizapp.model.QuizResult;
import com.akylbek.quizapp.model.ShortQuizResult;

import java.util.ArrayList;
import java.util.List;

public class HistoryStorage implements IHistoryStorage {

    private HistoryDao dao;

    public HistoryStorage(HistoryDao historyDao) {
        dao = historyDao;
    }

    @Override
    public int saveQuizResult(QuizResult quizResult) {
        return (int) dao.insert(quizResult);
    }

    @Override
    public LiveData<QuizResult> getQuizResult(int id) {
        return dao.get(id);
    }

    @Override
    public void deleteQuizResult(int pos) {
        dao.delete(pos);
    }

    @Override
    public LiveData<List<QuizResult>> getAll() {
        return dao.getAll();
    }

    @Override
    public LiveData<List<ShortQuizResult>> getAllShort() {
        return Transformations.map(dao.getAll(), new Function<List<QuizResult>, List<ShortQuizResult>>() {
            @Override
            public List<ShortQuizResult> apply(List<QuizResult> quizResults) {
                ArrayList<ShortQuizResult> shortQuizResults = new ArrayList<>();

                for (QuizResult quizResult : quizResults) {
                    String category = quizResult.getQuestions().get(0).getCategory();
                    for ( int i = 1; i < quizResult.getQuestions().size(); ++i) {
                        if (!category.equals(quizResult.getQuestions().get(i).getCategory())) {
                            category = "Mixed";
                        }
                    }

                    shortQuizResults.add(new ShortQuizResult(
                            quizResult.getId(),
                            quizResult.getQuestions().size(),
                            quizResult.getCorrectAnswers(),
                            quizResult.getCreatedAt(),
                            category,
                            quizResult.getQuestions().get(0).getDifficulty().name()
                    ));
                }

                return shortQuizResults;
            }
        });
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
    }

    public QuizResult convertToQuizResult(ShortQuizResult shortQuizResult) {
        LiveData<List<QuizResult>> result = dao.getAll();
        QuizResult quizResult = null;
        for (int i = 0; i < result.getValue().size(); ++i) {
            if ( result.getValue().get(i).getId() == shortQuizResult.getId()) {
                quizResult = result.getValue().get(i);
            }
        }
        return quizResult;
    }

}
