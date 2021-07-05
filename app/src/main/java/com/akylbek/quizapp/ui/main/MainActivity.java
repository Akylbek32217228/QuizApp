package com.akylbek.quizapp.ui.main;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.akylbek.quizapp.widgets.PageChangeListener;
import com.akylbek.quizapp.R;
import com.akylbek.quizapp.ui.history.HistoryFragment;
import com.akylbek.quizapp.ui.settings.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private BottomNavigationView mNavigation;
    private MainPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        mAdapter = new MainPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.main_view_pager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new PageChangeListener() {
            @Override
            public void onPageSelected(int i) {
                int selectedItemId = R.id.nav_main;

                switch (i) {
                    case 1: selectedItemId = R.id.nav_history;
                        break;
                    case 2: selectedItemId = R.id.nav_settings;
                        break;
                }

                mNavigation.setSelectedItemId(selectedItemId);
            }
        });

        mNavigation = findViewById(R.id.main_bottom_nav_bar);
        mNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_main: mViewPager.setCurrentItem(0);
                        break;
                    case R.id.nav_history: mViewPager.setCurrentItem(1);
                        break;
                    case R.id.nav_settings: mViewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });

    }

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    private class MainPagerAdapter extends FragmentPagerAdapter {


        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            switch (i) {
                case 0: fragment = MainFragment.newInstance();
                    break;
                case 1: fragment = HistoryFragment.newInstance();
                    break;
                case 2: fragment = SettingsFragment.newInstance();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    public void onBackPressed() {
        if ( mViewPager.getCurrentItem() != 0) {
            mViewPager.setCurrentItem(0);
        } else {
            finish();
        }
    }
}
