package com.akylbek.quizapp.ui.result;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.akylbek.quizapp.App;
import com.akylbek.quizapp.R;
import com.akylbek.quizapp.model.QuizResult;

import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private ResultViewModel viewModel;
    private int id;
    private TextView difficlulty;
    private TextView correctAnswers;
    private TextView correctAnswersPercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initView();

        id = getIntent().getIntExtra("id", 0);

        viewModel = ViewModelProviders.of(this).get(ResultViewModel.class);

        viewModel.setId(id);

        viewModel.quizActivityLiveData.observe(this, new Observer<QuizResult>() {
            @Override
            public void onChanged(QuizResult quizResult) {
                difficlulty.setText(quizResult.getQuestions().get(0).getDifficulty().toString());
                correctAnswers.setText((quizResult.getCorrectAnswers() + "/" + quizResult.getQuestions().size()));
                int percentage = quizResult.getCorrectAnswers() * 100 / quizResult.getQuestions().size();
                correctAnswersPercentage.setText(String.valueOf(percentage)) ;
            }
        });

        viewModel.closeResult.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                finish();
            }
        });

        findViewById(R.id.result_activity_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.closeResult();
            }
        });
    }

    private void initView() {
        difficlulty = findViewById(R.id.result_activity_difficulty);
        correctAnswers = findViewById(R.id.result_correct_answers);
        correctAnswersPercentage = findViewById(R.id.result_activity_corrected_answers_percentage);
    }

    public static void start(Context context, int id) {
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);

    }

}
