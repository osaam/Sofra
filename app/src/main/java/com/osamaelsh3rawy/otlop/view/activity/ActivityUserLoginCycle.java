package com.osamaelsh3rawy.otlop.view.activity;

import android.os.Bundle;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.view.fragment.user.FragmentUserShops;
import com.osamaelsh3rawy.otlop.view.fragment.userLogin.FragmentLogin;

import static com.osamaelsh3rawy.otlop.helper.HelperMethods.replaceFragment;

public class ActivityUserLoginCycle extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_cycle);

        FragmentLogin fragmentLogin = new FragmentLogin();
        if (getIntent().hasExtra("type")) {
            fragmentLogin.type = "finish";
        } else {
            fragmentLogin.type = "finishrelhwigt";
        }


        replaceFragment(getSupportFragmentManager(), R.id.activity_user_login_container, fragmentLogin);
    }

}
