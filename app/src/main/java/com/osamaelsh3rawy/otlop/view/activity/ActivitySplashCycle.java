package com.osamaelsh3rawy.otlop.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.view.fragment.splash.FragmentSplashChoose;

import static com.osamaelsh3rawy.otlop.helper.HelperMethods.replaceFragment;

public class ActivitySplashCycle extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_cycle);

        replaceFragment(getSupportFragmentManager(), R.id.activity_splash_container, new FragmentSplashChoose());
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}

