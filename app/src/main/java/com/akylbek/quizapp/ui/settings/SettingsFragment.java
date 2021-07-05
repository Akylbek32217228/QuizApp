package com.akylbek.quizapp.ui.settings;

import androidx.arch.core.executor.TaskExecutor;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akylbek.quizapp.App;
import com.akylbek.quizapp.R;
import com.akylbek.quizapp.ui.main.MainActivity;

import guy4444.smartrate.SmartRate;


public class SettingsFragment extends Fragment {

    private TextView shareApp;
    private TextView rateUs;
    private TextView leaveFeedback;
    private TextView clearHistory;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

    }

    private void initView(View view) {
        shareApp = view.findViewById(R.id.settings_share_this_app);
        rateUs = view.findViewById(R.id.settings_rate_us);
        leaveFeedback = view.findViewById(R.id.settings_leave_feedback);
        clearHistory = view.findViewById(R.id.settings_clear_history);

        shareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_TEXT, "Hey, check this Quiz App");
                share.setType("text/plain");
                startActivity(share);
            }
        });

        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //installed custom rate liblary
                SmartRate.Rate(getActivity()
                        , "Rate Us"
                        , "Tell others what you think about this app"
                        , "Continue"
                        , "Please take a moment and rate us on Google Play"
                        , "click here"
                        , "Cancel"
                        , "Thanks for the feedback"
                        , Color.parseColor("#3F3356")
                        , 4
                );

            }
        });

        clearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.historyStorage.deleteAll();
            }
        });


    }
}
