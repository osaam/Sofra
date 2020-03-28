package com.osamaelsh3rawy.otlop.view.fragment.restourant.moreRestourant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.osamaelsh3rawy.otlop.helper.HelperMethods.replaceFragment;


public class FragmentRestourantMore extends BaseFragment {


    @BindView(R.id.fragment_restourant_more_txt_comment)
    TextView fragmentRestourantMoreTxtComment;

    public FragmentRestourantMore() {
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
        View view = inflater.inflate(R.layout.fragment_restourant_more, container, false);
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

    @OnClick({R.id.fragment_restourant_more_txt_my_offer, R.id.fragment_restourant_more_txt_contact, R.id.fragment_restourant_more_txt_about, R.id.fragment_restourant_more_txt_change_pass, R.id.fragment_restourant_more_txt_log_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_restourant_more_txt_my_offer:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_restourant_container, new FragmentRestourantOffer());
                break;
            case R.id.fragment_restourant_more_txt_contact:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_restourant_container, new FragmentRestourantContact());

                break;
            case R.id.fragment_restourant_more_txt_about:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_restourant_container, new FragmentRestourantAboutUs());

                break;
            case R.id.fragment_restourant_more_txt_comment:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_restourant_container, new FragmentRestourantComment());

                break;
            case R.id.fragment_restourant_more_txt_change_pass:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_restourant_container, new FragmentRestourantChangePassword());

                break;
            case R.id.fragment_restourant_more_txt_log_out:
                DialogLogOut dialogLogOut = new DialogLogOut(getActivity());
                dialogLogOut.show();
                break;

        }
    }
}
