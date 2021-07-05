package com.akylbek.quizapp.ui.quiz.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akylbek.quizapp.R;
import com.akylbek.quizapp.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Question> mList = new ArrayList<>();
    QuizViewHolder.QuizViewHolderListener mListener;

    public QuizAdapter(QuizViewHolder.QuizViewHolderListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_quiz, viewGroup, false);
        return new QuizViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if ( viewHolder instanceof QuizViewHolder) {
            ((QuizViewHolder) viewHolder).onBind(mList.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setQuestions(List<Question> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    /*public void setQuestions(List<Question> list, ) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }
*/
}
