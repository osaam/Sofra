package com.osamaelsh3rawy.otlop.view.fragment.user.more;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.model.restourantLogin.RestourantLogin;
import com.osamaelsh3rawy.otlop.data.model.userLogin.UserLogin;
import com.osamaelsh3rawy.otlop.helper.HelperMethods;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;
import com.osamaelsh3rawy.otlop.view.fragment.restourant.moreRestourant.FragmentRestourantMore;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Api_Takin;


public class FragmentUserChangePassword extends BaseFragment {


    @BindView(R.id.fragment_forget_pass_et_old_pass)
    EditText fragmentForgetPassEtOldPassUser;
    @BindView(R.id.fragment_restourant_change_pass_et_new_pass)
    EditText fragmentRestourantChangePassEtNewPassUser;
    @BindView(R.id.fragment_restourant_change_pass_et_new_pass_confirm)
    EditText fragmentRestourantChangePassEtNewPassConfirmUser;
    private ApiServies apiServies;
    private String tokin, oldPass, newPass, NewPassConfirmation;

    private  String TAG="FragmentUserChangePassword";
    public FragmentUserChangePassword() {
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
        View view = inflater.inflate(R.layout.fragment_restourant_change_pass, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        apiServies = getClient();

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

    @OnClick(R.id.fragment_forget_pass_btn_send)
    public void onViewClicked() {
        tokin = SharedPreferencesManger.LoadData(getActivity(), Restourant_Api_Takin);
        oldPass = fragmentForgetPassEtOldPassUser.getText().toString().trim();
        newPass = fragmentRestourantChangePassEtNewPassUser.getText().toString().trim();
        NewPassConfirmation = fragmentRestourantChangePassEtNewPassConfirmUser.getText().toString().trim();
        apiServies.UserChangePass(tokin, oldPass, newPass, NewPassConfirmation).enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
             try {
                 if (response.body().getStatus() == 1) {
                     Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                     HelperMethods.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_restourant_container, new FragmentRestourantMore());
                 }
                 else{
                     Log.e(TAG,response.body().getMsg());
}
             }catch (Exception e){
                 Log.e(TAG,e.getMessage());
             }
            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {

            }
        });
    }
}
