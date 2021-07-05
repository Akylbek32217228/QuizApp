package com.akylbek.quizapp.data.remote;

import com.akylbek.quizapp.data.IQuizRepository;
import com.akylbek.quizapp.data.remote.model.QuizResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class QuizRemoteStorage implements IQuizRemoteStorage{

        private final static String BASE_URL = "https://opentdb.com";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static QuizNetworkClient client = retrofit.create(QuizNetworkClient.class);

    @Override
    public void getQuiz(int amount, int categoty, String difficulty,  final IQuizRepository.QuestionsCallback callback) {
        Call<QuizResponse> call;
        if ( categoty == 8) {
            call = client.getQuizWithoutCategory(
                    amount, difficulty
            );
        } else {
            call = client.getQuizaWithCategory(
                    amount, categoty, difficulty
            );
        }


        call.enqueue(new Callback<QuizResponse>() {
            @Override
            public void onResponse(Call<QuizResponse> call, Response<QuizResponse> response) {
                if ( response.isSuccessful()) {
                    if ( response.body() != null) {
                        callback.onSuccess(response.body().getQuestions());
                    } else {
                        callback.onFailure("Body is empty " + response.code());
                    }
                } else {
                    callback.onFailure("Request failed " + response.code());
                }
            }

            @Override
            public void onFailure(Call<QuizResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    private interface QuizNetworkClient {
        @GET("/api.php")
        Call<QuizResponse> getQuizaWithCategory(
            @Query("amount") int amount,
            @Query("category") int category,
            @Query("difficulty") String difficulty
        );

        @GET("/api.php")
        Call<QuizResponse> getQuizWithoutCategory(
                @Query("amount") int amount,
                @Query("difficulty") String difficulty
        );
    }

}
