package com.akylbek.quizapp.ui.quiz.recycler;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akylbek.quizapp.R;
import com.akylbek.quizapp.model.Question;
import com.akylbek.quizapp.model.TypeEnum;

import java.util.ArrayList;

public class QuizViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mQuestion;
    private TextView answer1;
    private TextView answer2;
    private TextView answer3;
    private TextView answer4;
    private TextView optionTrue;
    private TextView optionFalse;
    private LinearLayout multipleQuiestions;
    private LinearLayout truefalseQuiestions;
    private QuizViewHolderListener mListener;
    private ArrayList<TextView> multipleAnswersList = new ArrayList<>();
    private Context context;

    public QuizViewHolder(@NonNull View itemView, QuizViewHolderListener listener) {
        super(itemView);

        context = itemView.getContext();

        mListener = listener;

        //Linear layouts
        multipleQuiestions = itemView.findViewById(R.id.question_multiple_container);
        truefalseQuiestions = itemView.findViewById(R.id.question_boolean_container);

        mQuestion = itemView.findViewById(R.id.question_text);

        answer1 = itemView.findViewById(R.id.question_option_1);
        answer2 = itemView.findViewById(R.id.question_option_2);
        answer3 = itemView.findViewById(R.id.question_option_3);
        answer4 = itemView.findViewById(R.id.question_option_4);

        //added ArrayList which will hold TextView answers
        multipleAnswersList.add(answer1);
        multipleAnswersList.add(answer2);
        multipleAnswersList.add(answer3);
        multipleAnswersList.add(answer4);

        //true & false TextView
        optionTrue = itemView.findViewById(R.id.question_option_true);
        optionFalse = itemView.findViewById(R.id.question_option_false);

        //attach listeners
        for (TextView answer : multipleAnswersList) {
            answer.setOnClickListener(this);
        }


        optionTrue.setOnClickListener(this);
        optionFalse.setOnClickListener(this);
    }

    public void onBind(Question question) {
        //code which will generate state of each TextView

        mQuestion.setText(Html.fromHtml(question.getQuestion()));

        ArrayList<String> list = new ArrayList<>();

        if ( question.getType() == TypeEnum.MULTIPLE) {
            multipleQuiestions.setVisibility(LinearLayout.VISIBLE);
            truefalseQuiestions.setVisibility(LinearLayout.INVISIBLE);

            list.addAll(question.getQuestionsShuffle());

            for ( int k = 0; k < multipleAnswersList.size(); ++k) {
                multipleAnswersList.get(k).setText(list.get(k));
            }

            if (question.isAccepted()) {
                for ( int g = 0; g < multipleAnswersList.size(); ++g) {
                    TextView answer = multipleAnswersList.get(g);

                    if ( question.getCorrectAnswer().equals(answer.getText().toString()) ) {
                        answer.setBackgroundResource(R.drawable.bg_question_right);
                        answer.setEnabled(false);

                    } else if (question.getSelectedAnswer().equals(answer.getText().toString()) ) {
                        answer.setBackgroundResource(R.drawable.bg_question_accepted);
                        answer.setEnabled(false);
                    } else {
                        answer.setBackgroundResource(R.drawable.bg_questions_other);
                        answer.setEnabled(false);
                    }

                }
            } else if (!question.isAccepted()) {
                for (TextView answer : multipleAnswersList) {
                    answer.setBackgroundResource(R.drawable.bg_question_option);
                    answer.setEnabled(true);
                }
            }

        } else {
            multipleQuiestions.setVisibility(LinearLayout.INVISIBLE);
            truefalseQuiestions.setVisibility(LinearLayout.VISIBLE);

            if (question.isAccepted()) {
                if (question.getCorrectAnswer().equals("True")) {
                    optionTrue.setBackgroundResource(R.drawable.bg_question_right);
                    optionTrue.setEnabled(false);
                } else if ( question.getSelectedAnswer().equals("True")) {
                    optionTrue.setBackgroundResource(R.drawable.bg_question_accepted);
                    optionTrue.setEnabled(false);
                }

                if (question.getCorrectAnswer().equals("False")) {
                    optionFalse.setBackgroundResource(R.drawable.bg_question_right);
                    optionFalse.setEnabled(false);
                } else if ( question.getSelectedAnswer().equals("False")) {
                    optionFalse.setBackgroundResource(R.drawable.bg_question_accepted);
                    optionFalse.setEnabled(false);
                }
            } else {
                optionTrue.setBackgroundResource(R.drawable.bg_question_option);
                optionFalse.setBackgroundResource(R.drawable.bg_question_option);
                optionTrue.setEnabled(true);
                optionFalse.setEnabled(true);
            }

        }

    }
    //on TextView click listener send to Activity
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.question_option_1:
                answerPressed(0);
                mListener.onAnswerSelected(getAdapterPosition(), multipleAnswersList.get(0).getText().toString());
                break;
            case R.id.question_option_2:
                answerPressed(1);
                mListener.onAnswerSelected(getAdapterPosition(), multipleAnswersList.get(1).getText().toString());
                break;
            case R.id.question_option_3:
                answerPressed(2);
                mListener.onAnswerSelected(getAdapterPosition(), multipleAnswersList.get(2).getText().toString());
                break;
            case R.id.question_option_4:
                answerPressed(3);
                mListener.onAnswerSelected(getAdapterPosition(), multipleAnswersList.get(3).getText().toString());
                break;
            case R.id.question_option_true:
                optionTrue.setEnabled(false);
                optionFalse.setEnabled(true);
                mListener.onAnswerSelected(getAdapterPosition(), "True");
                break;
            case R.id.question_option_false:
                optionFalse.setEnabled(false);
                optionTrue.setEnabled(true);
                mListener.onAnswerSelected(getAdapterPosition(), "False");
                break;
        }
    }

    private void answerPressed(int g) {
        for ( int i = 0; i < multipleAnswersList.size(); ++i) {
            if (i != g) {
                multipleAnswersList.get(i).setEnabled(true);
            } else {
                multipleAnswersList.get(i).setEnabled(false);
            }
        }
    }


    public interface QuizViewHolderListener {

        void onAnswerSelected(int pos, String answer);

    }

}
