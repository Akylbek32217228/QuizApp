package com.akylbek.quizapp.ui.main;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akylbek.quizapp.R;
import com.akylbek.quizapp.ui.quiz.QuizActivity;
import com.akylbek.quizapp.widgets.SeekBarProgressListener;


public class MainFragment extends Fragment {

    private AppCompatSeekBar mSeekBar;
    private AppCompatSpinner mSpinnerCategory;
    private AppCompatSpinner mSpinnerDifficulty;

    private TextView amount;
    private View start;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSeekBar = view.findViewById(R.id.main_amount_seek_bar);
        mSpinnerCategory = view.findViewById(R.id.main_category_spinner);
        mSpinnerDifficulty = view.findViewById(R.id.main_difficulty_spinner);
        amount = view.findViewById(R.id.main_questions_amount);
        start = view.findViewById(R.id.main_start);

        mSeekBar.setOnSeekBarChangeListener(new SeekBarProgressListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                amount.setText(String.valueOf(progress));
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( mSeekBar.getProgress() != 0) {
                    QuizActivity.start(getContext(), mSeekBar.getProgress(), mSpinnerCategory.getSelectedItemPosition() + 8,
                            mSpinnerDifficulty.getSelectedItem().toString(), false);
                } else {
                    Toast.makeText(getContext(), "Incompatible amount", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
