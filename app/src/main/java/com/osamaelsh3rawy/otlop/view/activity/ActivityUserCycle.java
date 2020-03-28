package com.osamaelsh3rawy.otlop.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.local.room.RoomDao;
import com.osamaelsh3rawy.otlop.data.local.room.RoomManager;
import com.osamaelsh3rawy.otlop.data.model.items.ItemsData;
import com.osamaelsh3rawy.otlop.view.fragment.user.FragmentNotifications;
import com.osamaelsh3rawy.otlop.view.fragment.user.FragmentTabList;
import com.osamaelsh3rawy.otlop.view.fragment.user.FragmentUserOrderItemCart;
import com.osamaelsh3rawy.otlop.view.fragment.user.more.FragmentUserMore;
import com.osamaelsh3rawy.otlop.view.fragment.user.FragmentUserMyAccount;
import com.osamaelsh3rawy.otlop.view.fragment.user.FragmentUserShops;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.osamaelsh3rawy.otlop.R.id.bottom_navigation_view;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.User_Api_Takin;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.replaceFragment;

public class ActivityUserCycle extends BaseActivity {


    RoomDao roomDao;
    private boolean gologin = false;
    List<ItemsData> listitem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cycle);
        ButterKnife.bind(this);
        roomDao = RoomManager.getInstance(ActivityUserCycle.this).roomDao();

        replaceFragment(getSupportFragmentManager(), R.id.activity_user_container, new FragmentUserShops());

        BottomNavigationView bottomNavView = findViewById(bottom_navigation_view);

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                String tokin = SharedPreferencesManger.LoadData(ActivityUserCycle.this, User_Api_Takin);

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        replaceFragment(getSupportFragmentManager(), R.id.activity_user_container, new FragmentUserShops());
                        break;
                    case R.id.nav_list:
                        if (tokin != (null)) {
                            replaceFragment(getSupportFragmentManager(), R.id.activity_user_container, new FragmentTabList());
                        } else {
                            Intent intent = new Intent(ActivityUserCycle.this, ActivityUserLoginCycle.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.nav_my_account:
                        if (tokin != (null)) {
                            replaceFragment(getSupportFragmentManager(), R.id.activity_user_container, new FragmentUserMyAccount());
                        } else {
                            Intent intent = new Intent(ActivityUserCycle.this, ActivityUserLoginCycle.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.nav_more:
                        replaceFragment(getSupportFragmentManager(), R.id.activity_user_container, new FragmentUserMore());

                        break;

                }
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gologin && SharedPreferencesManger.LoadData(this, User_Api_Takin) != (null)) {
            gologin = false;

        }
    }

    @OnClick({R.id.nav_shopping, R.id.nav_notify, R.id.activity_user_container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nav_shopping:

                FragmentUserOrderItemCart fragmentUserOrderItemCart = new FragmentUserOrderItemCart();
                fragmentUserOrderItemCart.listitem = listitem;
                listitem = roomDao.getAll();
                replaceFragment(getSupportFragmentManager(), R.id.activity_user_container, fragmentUserOrderItemCart);

                break;
            case R.id.nav_notify:
                replaceFragment(getSupportFragmentManager(), R.id.activity_user_container, new FragmentNotifications());
                break;

            case R.id.activity_user_container:
                //                this to dismiss keyboard when touch background
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

                break;
        }
    }
}