package com.akylbek.quizapp.ui.history;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akylbek.quizapp.App;
import com.akylbek.quizapp.R;
import com.akylbek.quizapp.model.QuizResult;
import com.akylbek.quizapp.model.ShortQuizResult;
import com.akylbek.quizapp.ui.history.recycler.HistoryAdapter;
import com.akylbek.quizapp.ui.history.recycler.HistoryViewHolder;
import com.akylbek.quizapp.ui.quiz.QuizActivity;

import java.util.List;

import static com.akylbek.quizapp.App.getContext;

public class HistoryFragment extends Fragment implements HistoryViewHolder.HistoryViewHolderListener {

    private HistoryViewModel mViewModel;
    private RecyclerView mRecycler;
    private HistoryAdapter mAdapter;

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        mViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);

        mViewModel.history.observe(this, new Observer<List<ShortQuizResult>>() {
            @Override
            public void onChanged(List<ShortQuizResult> shortQuizResults) {
                mAdapter.setQuizResults(shortQuizResults);
            }
        });



        /*mViewModel.quizResultLiveDataOpen.observe(this, new Observer<QuizResult>() {
            @Override
            public void onChanged(QuizResult quizResult) {
                //QuizResult result = App.historyStorage.getQuizResult(integer);
                QuizActivity.start(getContext(), quizResult);
            }
        });*/
    }

    private void initView(View view) {
        mAdapter = new HistoryAdapter(this, getContext());

        mRecycler = view.findViewById(R.id.history_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter);

    }


    @Override
    public void onClickOption(int position) {
        Log.d("ololo", "on Click " + position);
        mViewModel.setQuizResult(position);
    }

    @Override
    public void onClick(int position) {
        QuizActivity.start(getContext(), position, false);
    }

    @Override
    public void onOptionsClick(int position) {

    }

}
