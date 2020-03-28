package com.osamaelsh3rawy.otlop.view.fragment.userLogin;

import android.os.Bundle;
import android.util.Log;
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


public class FragmentResetPass extends BaseFragment {


    @BindView(R.id.fragment_reset_pass_et_email)
    EditText fragmentResetPassEtEmail;
    private ApiServies apiServies;
    private String TAG = "FragmentResetPass";

    public FragmentResetPass() {
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
        View view = inflater.inflate(R.layout.fragment_user_reset_pass, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        apiServies = getClient();

        return view;
    }

    private void inti() {
        String email = fragmentResetPassEtEmail.getText().toString().trim();
        apiServies.UserResetPass(email).enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getActivity(), " check your email", Toast.LENGTH_LONG).show();
                        replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_login_container, new FragmentNewPass());

                    } else {
                        Toast.makeText(getActivity(), "email is wrong", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
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

    @OnClick(R.id.fragment_reset_pass_btn_send)
    public void onViewClicked() {
        inti();
    }
}
