package com.osamaelsh3rawy.otlop.view.fragment.restourantLogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.model.restourantLogin.RestourantLogin;
import com.osamaelsh3rawy.otlop.helper.HelperMethods;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;


public class FragmentRestourantResetPassword extends BaseFragment {


    @BindView(R.id.fragment_restourant_reset_pass_et_email)
    EditText fragmentRestourantResetPassEtEmail;
    private ApiServies apiServies;

    public FragmentRestourantResetPassword() {
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
        View view = inflater.inflate(R.layout.fragment_restourant_reset_pass, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        apiServies=getClient();

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

    @OnClick(R.id.fragment_restourant_reset_pass_btn_send)
    public void onViewClicked() {
        String email=fragmentRestourantResetPassEtEmail.getText().toString().trim();
apiServies.RestourantResetPass(email).enqueue(new Callback<RestourantLogin>() {
    @Override
    public void onResponse(Call<RestourantLogin> call, Response<RestourantLogin> response) {
        if (response.body().getStatus()==1) {
            Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
            HelperMethods.replaceFragment(getActivity().getSupportFragmentManager(),R.id.activity_restourant_login_container,new FragmentRestourantNewPassword());

        }
    }

    @Override
    public void onFailure(Call<RestourantLogin> call, Throwable t) {

    }
});
    }
}
