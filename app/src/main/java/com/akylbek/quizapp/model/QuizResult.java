package com.akylbek.quizapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


import com.akylbek.quizapp.data.db.converters.QuestionsConverter;
import com.akylbek.quizapp.data.db.converters.TimestampConverter;

import java.util.Date;
import java.util.List;

@Entity(tableName = "quiz_result")
public class QuizResult implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "questions")
    @TypeConverters({QuestionsConverter.class})
    private List<Question> questions;

    @ColumnInfo(name = "correct_answers")
    private int correctAnswers;

    @ColumnInfo(name = "created_at")
    @TypeConverters({TimestampConverter.class})
    private Date createdAt;

    public QuizResult(List<Question> questions, int correctAnswers, Date createdAt) {
        this.questions = questions;
        this.correctAnswers = correctAnswers;
        this.createdAt = createdAt;
    }

    protected QuizResult(Parcel in) {
        id = in.readInt();
        correctAnswers = in.readInt();
    }

    public static final Creator<QuizResult> CREATOR = new Creator<QuizResult>() {
        @Override
        public QuizResult createFromParcel(Parcel in) {
            return new QuizResult(in);
        }

        @Override
        public QuizResult[] newArray(int size) {
            return new QuizResult[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(correctAnswers);
    }
}
