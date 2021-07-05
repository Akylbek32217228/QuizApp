package com.akylbek.quizapp.data.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.akylbek.quizapp.model.QuizResult;

import java.util.List;

@Dao
public interface HistoryDao {

    @Insert
    long insert(QuizResult result);

    @Query("SELECT * FROM quiz_result WHERE id=:id")
    LiveData<QuizResult> get(int id);

    @Query("DELETE FROM quiz_result WHERE id=:id")
    void delete(int id);

    @Query("SELECT * FROM quiz_result")
    LiveData<List<QuizResult>> getAll();

    @Query("DELETE FROM quiz_result")
    void deleteAll();

}
