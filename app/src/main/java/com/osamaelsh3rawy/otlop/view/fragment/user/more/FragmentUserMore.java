package com.osamaelsh3rawy.otlop.view.fragment.user.more;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.view.activity.ActivitySplashCycle;
import com.osamaelsh3rawy.otlop.view.activity.ActivityUserCycle;
import com.osamaelsh3rawy.otlop.view.activity.ActivityUserLoginCycle;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;
import com.osamaelsh3rawy.otlop.view.fragment.user.FragmentUserMyAccount;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.osamaelsh3rawy.otlop.data.local.Constanse.User_Api_Takin;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.replaceFragment;


public class FragmentUserMore extends BaseFragment {


    public FragmentUserMore() {
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
        View view = inflater.inflate(R.layout.fragment_user_more, container, false);
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

    @OnClick({R.id.fragment_more_txt_offer, R.id.fragment_more_txt_contact, R.id.fragment_more_txt_about, R.id.fragment_more_txt_change_pass, R.id.fragment_more_txt_log_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_more_txt_offer:

                replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_container, new FragmentUserOffers());
                break;
            case R.id.fragment_more_txt_contact:
                if (SharedPreferencesManger.LoadData(getActivity(), User_Api_Takin) != (null)) {
                    replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_container, new FragmentUserContact());
                } else {
                    Intent intent = new Intent(getActivity(), ActivityUserLoginCycle.class);
                    startActivity(intent);
                }

                break;
            case R.id.fragment_more_txt_about:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_container, new FragmentUserAboutUs());

                break;
            case R.id.fragment_more_txt_change_pass:
                if (SharedPreferencesManger.LoadData(getActivity(), User_Api_Takin) != (null)) {
                    replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_container, new FragmentUserChangePassword());
                } else {
                    Intent intent = new Intent(getActivity(), ActivityUserLoginCycle.class);
                    startActivity(intent);
                }

                break;
            case R.id.fragment_more_txt_log_out:
                if (SharedPreferencesManger.LoadData(getActivity(), User_Api_Takin) != (null)) {
                    Dialog dialog = new Dialog(getActivity());
                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = inflater.inflate(R.layout.dialog_log_out, null);
                    dialog.setContentView(v);

                    Button button = (Button) dialog.findViewById(R.id.dialog_yes_btn);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), ActivitySplashCycle.class);
                            getActivity().startActivity(intent);
                            getActivity().finish();
                            SharedPreferencesManger.clean(getActivity());
                            Toast.makeText(baseActivity, "You Have LogOut", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Button btn_done = (Button) dialog.findViewById(R.id.dialog_no_btn);
                    btn_done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                }
                else {
                    Intent intent = new Intent(getActivity(), ActivityUserLoginCycle.class);
                    startActivity(intent);
                }

                break;
        }
    }
}
