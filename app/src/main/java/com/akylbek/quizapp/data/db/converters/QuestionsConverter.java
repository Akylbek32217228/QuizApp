package com.akylbek.quizapp.data.db.converters;

import androidx.room.TypeConverter;

import com.akylbek.quizapp.model.Question;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class QuestionsConverter {
    @TypeConverter
    public static List<Question> fromRaw(String value) {
        if (value == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Question>>() {}.getType();
        return gson.fromJson(value, type);
    }

    @TypeConverter
    public static String questionsToRaw(List<Question> questions) {
        if (questions == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Question>>() {}.getType();
        return gson.toJson(questions, type);
    }
}
