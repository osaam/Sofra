package com.osamaelsh3rawy.otlop.view.fragment.restourantLogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.model.restourantLogin.RestourantLogin;
import com.osamaelsh3rawy.otlop.view.activity.ActivityRestourantCycle;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Api_Takin;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Avilability;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Dele_Time;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Dele_fee;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Email;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Mini;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Name;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Phone;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Photo;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Whats;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_reagon;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.replaceFragment;


public class FragmentRestourantLogin extends BaseFragment {


    @BindView(R.id.fragment_login_restorant_txt_email)
    EditText fragmentLoginRestorantTxtEmail;
    @BindView(R.id.fragment_login_restorant_txt_password)
    EditText fragmentLoginRestorantTxtPassword;
    @BindView(R.id.fragment_login_restorant_ck_remmember_me)
    CheckBox fragmentLoginRestorantCkRemmemberMe;

    private ApiServies apiServies;

    public FragmentRestourantLogin() {
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
        View view = inflater.inflate(R.layout.fragment_restorant_login, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        apiServies = getClient();
        sharedorefrance();
        return view;
    }

    private void sharedorefrance() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login_Restourant", 0);
        if (sharedPreferences.getBoolean("y", false)) {

            fragmentLoginRestorantTxtEmail.setText(sharedPreferences.getString("email_Restourant",""));
            fragmentLoginRestorantTxtPassword.setText(sharedPreferences.getString("pass_Restourant", ""));
            fragmentLoginRestorantCkRemmemberMe.setChecked(true);
        }
    }


    private void Restourantlogin(String e, String p) {

        String email = fragmentLoginRestorantTxtEmail.getText().toString().trim();
        String pass = fragmentLoginRestorantTxtPassword.getText().toString().trim();
        apiServies.RestourantLogin(email, pass).enqueue(new Callback<RestourantLogin>() {
            @Override
            public void onResponse(Call<RestourantLogin> call, Response<RestourantLogin> response) {
                if (response.body().getStatus() == 1) {

                    SharedPreferencesManger.SaveData(getActivity(), Restourant_Api_Takin, response.body().getData().getApiToken());
                    SharedPreferencesManger.SaveData(getActivity(), Restourant_Name, response.body().getData().getUser().getName());
                    SharedPreferencesManger.SaveData(getActivity(), Restourant_Email, response.body().getData().getUser().getEmail());
                    SharedPreferencesManger.SaveData(getActivity(), Restourant_reagon, response.body().getData().getUser().getRegionId());
                    SharedPreferencesManger.SaveData(getActivity(), Restourant_Dele_fee, response.body().getData().getUser().getDeliveryCost());
                    SharedPreferencesManger.SaveData(getActivity(), Restourant_Dele_Time, response.body().getData().getUser().getDeliveryTime());
                    SharedPreferencesManger.SaveData(getActivity(), Restourant_Phone, response.body().getData().getUser().getPhone());
                    SharedPreferencesManger.SaveData(getActivity(), Restourant_Whats, response.body().getData().getUser().getWhatsapp());
                    SharedPreferencesManger.SaveData(getActivity(), Restourant_Photo, response.body().getData().getUser().getPhotoUrl());
                    SharedPreferencesManger.SaveData(getActivity(), Restourant_Avilability, response.body().getData().getUser().getAvailability());
                    SharedPreferencesManger.SaveData(getActivity(), Restourant_Mini, response.body().getData().getUser().getMinimumCharger());


                    Intent intent = new Intent(getActivity(), ActivityRestourantCycle.class);
                    getActivity().startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<RestourantLogin> call, Throwable t) {

            }
        });


    }


    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @OnClick({R.id.fragment_splash_choose_btn_login, R.id.fragment_login_restorant_btn_register,R.id.fragment_login_restorant_ck_remmember_me,R.id.fragment_login_restorant_txt_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_splash_choose_btn_login:

                Restourantlogin(fragmentLoginRestorantTxtEmail.getText().toString().trim(),
                        fragmentLoginRestorantTxtPassword.getText().toString().trim());
                break;
            case R.id.fragment_login_restorant_btn_register:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_restourant_login_container, new FragmentRestourantRigester());
                break;
            case R.id.fragment_login_restorant_ck_remmember_me:

                if(fragmentLoginRestorantCkRemmemberMe.isChecked()){
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login_Restourant", 0);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("email_Restourant",fragmentLoginRestorantTxtEmail.getText().toString());
                    editor.putString("pass_Restourant",fragmentLoginRestorantTxtPassword.getText().toString());
                    editor.putBoolean("y",true);
                    editor.commit();
                    Restourantlogin(fragmentLoginRestorantTxtEmail.getText().toString().trim(),
                            fragmentLoginRestorantTxtPassword.getText().toString().trim());

                }
                break;
            case R.id.fragment_login_restorant_txt_forget:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_restourant_login_container, new FragmentRestourantResetPassword());

                break;

        }
    }
}
