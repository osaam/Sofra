package com.osamaelsh3rawy.otlop.view.fragment.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.view.activity.ActivityRestourantCycle;
import com.osamaelsh3rawy.otlop.view.activity.ActivityRestourantLoginCycle;
import com.osamaelsh3rawy.otlop.view.activity.ActivityUserCycle;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;
import com.osamaelsh3rawy.otlop.view.fragment.user.FragmentUserShops;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.osamaelsh3rawy.otlop.helper.HelperMethods.replaceFragment;


public class FragmentSplashChoose extends BaseFragment {


    public FragmentSplashChoose() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash_choose, container, false);
        ButterKnife.bind(this, view);
        intiFragment();

        return view;
    }

    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.fragment_splash_choose_btn_order, R.id.fragment_splash_choose_btn_sell})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.fragment_splash_choose_btn_order:
                Intent intent = new Intent(getActivity(), ActivityUserCycle.class);
                getActivity().startActivity(intent);
            break;

            case R.id.fragment_splash_choose_btn_sell:
                Intent intent2 = new Intent(getActivity(), ActivityRestourantLoginCycle.class);
                getActivity().startActivity(intent2);
                break;
        }
    }
}
