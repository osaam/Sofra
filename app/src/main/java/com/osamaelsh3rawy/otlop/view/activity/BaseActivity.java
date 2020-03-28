package com.osamaelsh3rawy.otlop.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;

public class BaseActivity extends AppCompatActivity {
    public BaseFragment baseFragment;

    public void superonBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        baseFragment.onBack();
    }
}
