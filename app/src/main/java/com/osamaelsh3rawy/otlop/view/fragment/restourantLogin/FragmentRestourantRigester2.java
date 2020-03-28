package com.osamaelsh3rawy.otlop.view.fragment.restourantLogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.model.restourantLogin.RestourantLogin;
import com.osamaelsh3rawy.otlop.helper.HelperMethods;
import com.osamaelsh3rawy.otlop.helper.MediaLoader;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.convertFileToMultipart;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.convertToRequestBody;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.replaceFragment;


public class FragmentRestourantRigester2 extends BaseFragment {

    FragmentRestourantRigester fragmentRestourantRigester;
    @BindView(R.id.fragment_regester_restorant_et_phone)
    EditText fragmentRegesterRestorantEtPhone;
    @BindView(R.id.fragment_regester_restorant_et_whatsapp)
    EditText fragmentRegesterRestorantEtWhatsapp;
    @BindView(R.id.fragment_regester_restorant2_img)
    ImageView fragmentRegesterRestorant2Img;
    private String path;
    private ApiServies apiServies;

    RequestBody name, emali, city, pass, pass_confirm, pbone, whatsApp, reagon, mini, del_time, dele_fee;
    MultipartBody.Part photo;

    public FragmentRestourantRigester2() {
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
        View view = inflater.inflate(R.layout.fragment_resturant_regester2, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        apiServies = getClient();


        return view;
    }

    private void getdata() {

        name = convertToRequestBody(getArguments().getString("name"));
        emali = convertToRequestBody(getArguments().getString("email"));
        del_time = convertToRequestBody(getArguments().getString("del_time"));
        pass = convertToRequestBody(getArguments().getString("pass"));
        pass_confirm = convertToRequestBody(getArguments().getString("pass_confirm"));
        mini = convertToRequestBody(getArguments().getString("mini"));
        dele_fee = convertToRequestBody(getArguments().getString("dele_fee"));

        city = convertToRequestBody(String.valueOf(getArguments().getInt("city")));
        reagon = convertToRequestBody(String.valueOf(getArguments().getInt("reagon")));

        pbone = convertToRequestBody(fragmentRegesterRestorantEtPhone.getText().toString().trim());
        whatsApp = convertToRequestBody(fragmentRegesterRestorantEtWhatsapp.getText().toString().trim());

        photo = convertFileToMultipart(path, "photo");


        apiServies.RestourantRegister(name, emali, pass, pass_confirm, pbone, whatsApp, reagon, dele_fee, mini, photo, del_time).enqueue(new Callback<RestourantLogin>() {
            @Override
            public void onResponse(Call<RestourantLogin> call, Response<RestourantLogin> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText(baseActivity, "succesfully Regiestration you Can Login Now !1", Toast.LENGTH_SHORT).show();

                    replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_restourant_login_container, new FragmentRestourantLogin());
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

    @OnClick({R.id.fragment_regester_restorant2_img, R.id.fragment_regester_restorant_btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_regester_restorant2_img:
                HelperMethods.initImage2(fragmentRegesterRestorant2Img,getActivity());
                break;
            case R.id.fragment_regester_restorant_btn_confirm:
                getdata();
                break;
        }
    }
}
