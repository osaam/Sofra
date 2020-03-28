package com.osamaelsh3rawy.otlop.view.fragment.userLogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.model.userLogin.UserLogin;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.replaceFragment;


public class FragmentNewPass extends BaseFragment {


    @BindView(R.id.fragment_forget_pass_et_code)
    EditText fragmentForgetPassEtCode;
    @BindView(R.id.fragment_forget_pass_et_pass)
    EditText fragmentForgetPassEtPass;
    @BindView(R.id.fragment_forget_pass_et_pass_confirm)
    EditText fragmentForgetPassEtPassConfirm;
    private ApiServies apiServies;

    public FragmentNewPass() {
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
        View view = inflater.inflate(R.layout.fragment_user_new_pass, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        apiServies = getClient();



        return view;
    }

    private void inti() {
        String code = fragmentForgetPassEtCode.getText().toString().trim();
        String pass = fragmentForgetPassEtPass.getText().toString().trim();
        String passConfirm = fragmentForgetPassEtPassConfirm.getText().toString().trim();
        apiServies.UserNewPass(code, pass, passConfirm).enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                if (response.body().getStatus() == 1) {
                    replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_login_container, new FragmentLogin());

                } else {
                    Toast.makeText(getActivity(), "code is wrong", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {

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

    @OnClick(R.id.fragment_forget_pass_btn_send)
    public void onViewClicked() {
        inti();
    }
}
