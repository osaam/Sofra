package com.osamaelsh3rawy.otlop.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.view.fragment.restourant.FragmentCommition;
import com.osamaelsh3rawy.otlop.view.fragment.restourant.FragmentRestourantModify;
import com.osamaelsh3rawy.otlop.view.fragment.restourant.FragmentRestourantNotifications;
import com.osamaelsh3rawy.otlop.view.fragment.restourant.FragmentRestourantTabList;
import com.osamaelsh3rawy.otlop.view.fragment.restourant.FragmentRestourantTypeFood;
import com.osamaelsh3rawy.otlop.view.fragment.restourant.moreRestourant.FragmentRestourantChangePassword;
import com.osamaelsh3rawy.otlop.view.fragment.restourant.moreRestourant.FragmentRestourantMore;
import com.osamaelsh3rawy.otlop.view.fragment.user.FragmentNotifications;
import com.osamaelsh3rawy.otlop.view.fragment.user.FragmentTabList;
import com.osamaelsh3rawy.otlop.view.fragment.user.more.FragmentUserMore;
import com.osamaelsh3rawy.otlop.view.fragment.user.FragmentUserMyAccount;
import com.osamaelsh3rawy.otlop.view.fragment.user.FragmentUserShops;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.osamaelsh3rawy.otlop.R.id.bottom_navigation_view;
import static com.osamaelsh3rawy.otlop.R.id.bottom_navigation_view_res;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.replaceFragment;

public class ActivityRestourantCycle extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restourant_cycle);
        ButterKnife.bind(this);

        replaceFragment(getSupportFragmentManager(), R.id.activity_restourant_container, new FragmentRestourantTypeFood());

        BottomNavigationView bottomNavView = findViewById(bottom_navigation_view_res);

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home_res:
                        replaceFragment(getSupportFragmentManager(), R.id.activity_restourant_container, new FragmentRestourantTypeFood());
                        break;
                    case R.id.nav_list_res:
                 replaceFragment(getSupportFragmentManager(), R.id.activity_restourant_container, new FragmentRestourantTabList());
                        break;
                    case R.id.nav_my_account_res:
                        replaceFragment(getSupportFragmentManager(), R.id.activity_restourant_container, new FragmentRestourantModify());

                        break;
                    case R.id.nav_more_res:
                        replaceFragment(getSupportFragmentManager(), R.id.activity_restourant_container, new FragmentRestourantMore());

                        break;

                }
                return true;
            }
        });

    }


    @OnClick({R.id.nav_notify,R.id.nav_calc,R.id.activity_restourant_container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nav_notify:
                replaceFragment(getSupportFragmentManager(), R.id.activity_restourant_container, new FragmentRestourantNotifications());

                break;
            case R.id.nav_calc:
                replaceFragment(getSupportFragmentManager(), R.id.activity_restourant_container, new FragmentCommition());

                break;
            case R.id.activity_restourant_container:
                //                this to dismiss keyboard when touch background
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
        }
    }
}