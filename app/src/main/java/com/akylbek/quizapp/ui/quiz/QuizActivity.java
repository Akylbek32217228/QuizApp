package com.akylbek.quizapp.ui.quiz;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.akylbek.quizapp.R;
import com.akylbek.quizapp.model.Question;
import com.akylbek.quizapp.model.QuizResult;
import com.akylbek.quizapp.ui.quiz.recycler.QuizAdapter;
import com.akylbek.quizapp.ui.quiz.recycler.QuizViewHolder;
import com.akylbek.quizapp.ui.result.ResultActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity implements QuizViewHolder.QuizViewHolderListener {

    private QuizViewModel viewModel;
    private QuizAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar quizProgress;
    private ProgressBar quizLoadingProgress;
    private TextView progressText;
    private TextView titleText;
    private boolean shuffled;
    private TextView skip;
    private ImageView accept;
    private ImageView decline;

    private final static String EXTRA_AMOUNT = "amount";
    private final static String EXTRA_CATEGORY = "category";
    private final static String EXTRA_DIFFICULTY = "difficulty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);

        initView();

        viewModel = ViewModelProviders.of(this).get(QuizViewModel.class);

        viewModel.loading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean loading) {
                if ( loading) {
                    quizLoadingProgress.setVisibility(ProgressBar.INVISIBLE);
                    quizProgress.setVisibility(ProgressBar.VISIBLE);
                    findViewById(R.id.question_skip).setVisibility(ProgressBar.VISIBLE);
                    findViewById(R.id.quiz_back_btn).setVisibility(ProgressBar.VISIBLE);
                } else {
                    quizLoadingProgress.setVisibility(ProgressBar.VISIBLE);
                    quizProgress.setVisibility(ProgressBar.INVISIBLE);
                    findViewById(R.id.question_skip).setVisibility(ProgressBar.INVISIBLE);
                    findViewById(R.id.quiz_back_btn).setVisibility(ProgressBar.INVISIBLE);
                }
            }
        });

        viewModel.questions.observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(@Nullable List<Question> questions) {
                if ( questions != null) {
                    if ( !shuffled ) {
                        for (int i = 0; i < questions.size(); ++i) {
                            ArrayList<String> listShuffle = new ArrayList<>();
                            listShuffle.add(questions.get(i).getCorrectAnswer());
                            listShuffle.addAll(questions.get(i).getIncorrectAnswers());
                            Collections.shuffle(listShuffle);
                            questions.get(i).setQuestionsShuffle(listShuffle);
                        }
                        shuffled = true;
                    }
                    adapter.setQuestions(questions);
                    quizProgress.setMax(questions.size());
                }
            }
        });

        /*viewModel.questionsHistory.observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                adapter.setQuestions(questions);
            }
        });*/

        viewModel.currentQuestionPosition.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer possition) {
                if ( possition != null) {
                    quizProgress.setProgress(possition + 1);
                    progressText.setText((possition + 1) + "/" + adapter.getItemCount());
                    titleText.setText(viewModel.getTitle());

                    recyclerView.smoothScrollToPosition(possition);
                }
            }
        });

        viewModel.closeEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                finish();
            }
        });

        viewModel.resultEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                ResultActivity.start(getBaseContext(), integer);
            }
        });

        boolean isHistory = getIntent().getBooleanExtra("isHistory", false);

        if(isHistory) {
            viewModel.getQuizFromDatabase(getIntent().getIntExtra("pos", 1));
            viewModel.result.observe(this, new Observer<QuizResult>() {
                @Override
                public void onChanged(QuizResult quizResult) {
                    /*viewModel.questionsHistory.setValue(quizResult.getQuestions(), quizResult.getCorrectAnswers());
                    viewModel.currentQuestionPosition.setValue(0);
                    quizLoadingProgress.setVisibility(View.INVISIBLE);*/
                }
            });
        } else {
            viewModel.getQuiz(getIntent().getIntExtra(EXTRA_AMOUNT, 10),
                    getIntent().getIntExtra(EXTRA_CATEGORY, 9),
                    getIntent().getStringExtra(EXTRA_DIFFICULTY));
        }




    }

    private void initView() {
        quizProgress = findViewById(R.id.quiz_progress);
        progressText = findViewById(R.id.quiz_progress_text);
        titleText = findViewById(R.id.category_name);
        quizLoadingProgress = findViewById(R.id.progress_bar_loading);
        skip = findViewById(R.id.question_skip);
        accept = findViewById(R.id.question_accept);
        decline = findViewById(R.id.question_decline);

        progressText.setEnabled(false);

        initRecycler();

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onNextClick();
            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept.setVisibility(ImageView.INVISIBLE);
                decline.setVisibility(ImageView.INVISIBLE);
                skip.setVisibility(TextView.VISIBLE);
                adapter.notifyDataSetChanged();
            }
        });

        findViewById(R.id.quiz_back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onBackPressed();
            }
        });

    }

    private void initRecycler() {
        adapter = new QuizAdapter(this);
        recyclerView = findViewById(R.id.recycler_view_quiz);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(lm);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    public static void start(Context context, int amount, int category, String difficulty, boolean isHistory) {
        Intent intent = new Intent(context, QuizActivity.class);
        intent.putExtra("amount", amount);
        intent.putExtra("category", category);
        intent.putExtra("difficulty", difficulty);
        intent.putExtra("isHistory", isHistory);
        context.startActivity(intent);
    }

    public static void start(Context context,int pos, boolean isHistory) {
        Intent intent = new Intent(context, QuizActivity.class);
        intent.putExtra("pos", pos);
        intent.putExtra("isHistory", isHistory);
        context.startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        viewModel.onBackPressed();
        accept.setVisibility(ImageView.INVISIBLE);
        decline.setVisibility(ImageView.INVISIBLE);
        skip.setVisibility(TextView.VISIBLE);
    }


    @Override
    public void onAnswerSelected(final int pos, final String answer) {
        accept.setVisibility(ImageView.VISIBLE);
        decline.setVisibility(ImageView.VISIBLE);
        skip.setVisibility(TextView.INVISIBLE);

        //on accept click item become disabled
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onAnswerSelected(pos, answer);
                viewModel.onNextClick();
                accept.setVisibility(ImageView.INVISIBLE);
                decline.setVisibility(ImageView.INVISIBLE);
                skip.setVisibility(TextView.VISIBLE);
            }
        });
    }
}
