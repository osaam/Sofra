package com.osamaelsh3rawy.otlop.view.fragment.restourant.moreRestourant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;

import butterknife.ButterKnife;


public class FragmentRestourantAboutUs extends BaseFragment {


    public FragmentRestourantAboutUs() {
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
        View view = inflater.inflate(R.layout.fragment_user_about_us, container, false);
        ButterKnife.bind(this,view);
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
}
