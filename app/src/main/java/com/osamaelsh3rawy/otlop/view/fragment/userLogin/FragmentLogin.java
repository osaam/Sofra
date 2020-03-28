package com.osamaelsh3rawy.otlop.view.fragment.userLogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.model.userLogin.UserLogin;
import com.osamaelsh3rawy.otlop.view.activity.ActivityUserCycle;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Photo;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.User_Api_Takin;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.User_City;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.User_Email;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.User_Name;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.User_Phone;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.User_Photo;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.User_reagon;
import static com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger.SaveData;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.replaceFragment;


public class FragmentLogin extends BaseFragment {


    public String type;
    @BindView(R.id.fragment_user_login_et_email)
    EditText fragmentUserLoginEtEmail;
    @BindView(R.id.fragment_user_login_et_password)
    EditText fragmentUserLoginEtPassword;
    @BindView(R.id.ck_remember_login_fragment)
    CheckBox ckRememberLoginFragment;
    private ApiServies apiServies;
    private  String TAG="FragmentLogin";


    public FragmentLogin() {
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
        View view = inflater.inflate(R.layout.fragment_user_login, container, false);

        ButterKnife.bind(this, view);
        intiFragment();

        apiServies = getClient();

        sharedPref();

        return view;
    }

    private void sharedPref() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", 0);
        if (sharedPreferences.getBoolean("x", false)) {
            fragmentUserLoginEtEmail.setText(sharedPreferences.getString("email", ""));
            fragmentUserLoginEtPassword.setText(sharedPreferences.getString("pass", ""));
            ckRememberLoginFragment.setChecked(true);

        }
    }


    @Override
    public void onBack() {

        baseActivity.finish();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    private void login(String email, String pass) {
        apiServies.UserLogin(email, pass).enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                try {
                    if (response.body().getStatus() == 1) {

                        SaveData(getActivity(), User_Api_Takin, response.body().getData().getApiToken());
                        SaveData(getActivity(), User_Name, response.body().getData().getUser().getName());
                        SaveData(getActivity(), User_Email, response.body().getData().getUser().getEmail());
                        SaveData(getActivity(), User_City, response.body().getData().getUser().getRegionId());
                        SaveData(getActivity(), User_reagon, response.body().getData().getUser().getRegionId());
                        SaveData(getActivity(), User_Phone, response.body().getData().getUser().getPhone());
                        SaveData(getActivity(), User_Photo, response.body().getData().getUser().getPhotoUrl());

                        Toast.makeText(getActivity(),response.body().getMsg(),Toast.LENGTH_SHORT).show();

                        getActivity().finish();
//                    Intent intent = new Intent(getActivity(), ActivityUserCycle.class);
//                    getActivity().startActivity(intent);
                    } else {
                        Log.e(TAG,response.body().getMsg());
                    }
                } catch (Exception e) {
                    Log.e(TAG,e.getMessage());
                } }
            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {
            }
        });
    }

    @OnClick({R.id.fragment_user_login_txt_forget_pass, R.id.fragment_user_login_btn_login, R.id.fragment_user_login_relative_sign_up,
            R.id.ck_remember_login_fragment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_user_login_txt_forget_pass:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_login_container, new FragmentResetPass());
                break;

            case R.id.fragment_user_login_btn_login:
                login(fragmentUserLoginEtEmail.getText().toString().trim(), fragmentUserLoginEtPassword.getText().toString().trim());
                break;

            case R.id.fragment_user_login_relative_sign_up:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_login_container, new FragmentRigerter());
                break;

            case R.id.ck_remember_login_fragment:
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("num", fragmentUserLoginEtEmail.getText().toString().trim());
                editor.putString("pass", fragmentUserLoginEtPassword.getText().toString().trim());
                editor.putBoolean("x", true);
                editor.commit();
                break;

        }
    }
}
