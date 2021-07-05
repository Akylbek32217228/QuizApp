package com.akylbek.quizapp.ui.history.recycler;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.akylbek.quizapp.R;
import com.akylbek.quizapp.model.ShortQuizResult;
import com.akylbek.quizapp.ui.quiz.QuizActivity;

import java.text.DateFormat;

import static com.akylbek.quizapp.App.getContext;


public class HistoryViewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private TextView correctAnswers;
    private TextView difficulty;
    private TextView createdDate;
    private ImageView itemHistoryDetails;
    private PopupMenu popupMenu;
    private CardView cardView;
    HistoryViewHolder.HistoryViewHolderListener mListener;

    public HistoryViewHolder(@NonNull View itemView, HistoryViewHolderListener listener) {
        super(itemView);

        title = itemView.findViewById(R.id.item_history_title);
        correctAnswers = itemView.findViewById(R.id.item_history_correct_amount);
        difficulty = itemView.findViewById(R.id.item_history_difficulty);
        createdDate = itemView.findViewById(R.id.item_history_created_date);
        itemHistoryDetails = itemView.findViewById(R.id.item_history_details);
        mListener = listener;
        cardView = itemView.findViewById(R.id.item_quiz_name);

    }

    void onBind(final ShortQuizResult quizResult, Context context) {

        title.setText(("Category: " + quizResult.getCategory()));
        correctAnswers.setText(("Corrected answers: " + quizResult.getCorrectAnswers() + "/" + quizResult.getQuestionsAmount()));
        difficulty.setText(("Difficulty: " + quizResult.getDifficulty()));
        String date = DateFormat.getInstance().format(quizResult.getCreatedAt());

        createdDate.setText(date);

        popupMenu = new PopupMenu(context, itemHistoryDetails);

        popupMenu.inflate(R.menu.menu_item_history);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete_item:
                        Log.d("ololo", quizResult.getId() + " id HistoryViewHolder");
                        mListener.onClickOption(quizResult.getId());
                        break;
                }
                return false;
            }
        });

        itemHistoryDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ololo", "item menu");
                HistoryViewHolder.this.popupMenu.show();
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("ololo", "item menu name");
                //mListener.onClick(quizResult.getId());
            }
        });

    }

    public interface HistoryViewHolderListener {
        void onClickOption(int position);

        void onClick(int position);
        void onOptionsClick(int position);
    }


}
