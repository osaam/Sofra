package com.osamaelsh3rawy.otlop.view.activity;

import android.os.Bundle;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.view.fragment.restourantLogin.FragmentRestourantLogin;
import com.osamaelsh3rawy.otlop.view.fragment.userLogin.FragmentLogin;

import static com.osamaelsh3rawy.otlop.helper.HelperMethods.replaceFragment;

public class ActivityRestourantLoginCycle extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restourant_login_cycle);

        replaceFragment(getSupportFragmentManager(), R.id.activity_restourant_login_container, new FragmentRestourantLogin());
    }

}
