package com.osamaelsh3rawy.otlop.view.fragment.userLogin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.adapter.Sppineradpter;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.model.listOfAreaNot.ListOfAreaNot;
import com.osamaelsh3rawy.otlop.data.model.userLogin.UserLogin;
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
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.convertToRequestBody;


public class FragmentRigerter extends BaseFragment {


    @BindView(R.id.fragment_login_user_et_name)
    EditText fragmentLoginUserEtName;
    @BindView(R.id.fragment_regester_user_et_email)
    EditText fragmentRegesterUserEtEmail;
    @BindView(R.id.fragment_regester_user_et_phone)
    EditText fragmentRegesterUserEtPhone;
    @BindView(R.id.fragment_regester_user_et_city)
    EditText fragmentRegesterUserEtCity;
    @BindView(R.id.fragment_regester_user_sp_city)
    Spinner fragmentRegesterUserSpCity;
    @BindView(R.id.fragment_regester_user_et_regon)
    EditText fragmentRegesterUserEtRegon;
    @BindView(R.id.fragment_regester_user_sp_regon)
    Spinner fragmentRegesterUserSpRegon;
    @BindView(R.id.fragment_regester_user_et_pass)
    EditText fragmentRegesterUserEtPass;
    @BindView(R.id.fragment_regester_user_et_pass_confirm)
    EditText fragmentRegesterUserEtPassConfirm;
    @BindView(R.id.fragment_regester_user_btn_confirm)
    Button fragmentRegesterUserBtnConfirm;
    Sppineradpter cityAdapter, areaAdapter;
    @BindView(R.id.fragment_regester_user_img)
    CircleImageView fragmentRegesterUserImg;
    private ApiServies apiServies;
    private int id;
    private String path;
    private  String TAG="FragmentRigerter";
    RequestBody name, email, phone, pass, passConfirm, city, area;
    MultipartBody.Part photo;


    public FragmentRigerter() {
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
        View view = inflater.inflate(R.layout.fragment_user_regester, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        apiServies = getClient();
        listOfCiteis();


        return view;
    }


    private void listOfCiteis() {
        cityAdapter = new Sppineradpter(getActivity());
        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                areaAdapter = new Sppineradpter(getActivity());

                apiServies.ListArea(i).enqueue(new Callback<ListOfAreaNot>() {
                    @Override
                    public void onResponse(Call<ListOfAreaNot> call, Response<ListOfAreaNot> response) {
                        if (response.body().getStatus() == 1) {
                            areaAdapter.setData((response.body().getData()), "choose ragon");
                            fragmentRegesterUserSpRegon.setAdapter(areaAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<ListOfAreaNot> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };


        apiServies.ListCities().enqueue(new Callback<ListOfAreaNot>() {
            @Override
            public void onResponse(Call<ListOfAreaNot> call, Response<ListOfAreaNot> response) {
                if (response.body().getStatus() == 1) {
                    cityAdapter.setData(response.body().getData(), "choose city");
                    fragmentRegesterUserSpCity.setAdapter(cityAdapter);
                    fragmentRegesterUserSpCity.setOnItemSelectedListener(listener);
                }
            }

            @Override
            public void onFailure(Call<ListOfAreaNot> call, Throwable t) {

            }
        });
    }

    private void inti() {
        name = convertToRequestBody(fragmentLoginUserEtName.getText().toString().trim());
        email = convertToRequestBody(fragmentRegesterUserEtEmail.getText().toString().trim());
        phone = convertToRequestBody(fragmentRegesterUserEtPhone.getText().toString().trim());
        pass = convertToRequestBody(fragmentRegesterUserEtPass.getText().toString().trim());
        passConfirm = convertToRequestBody(fragmentRegesterUserEtPassConfirm.getText().toString().trim());
        city = convertToRequestBody(String.valueOf(cityAdapter.selectedId));
        area = convertToRequestBody(String.valueOf(areaAdapter.selectedId));
        photo = HelperMethods.convertFileToMultipart(path, "photo");

        apiServies.Register(name, email, pass, passConfirm, phone, area, photo).enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                if (response.body().getStatus() == 1) {
                    try {
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        HelperMethods.replaceFragment(getChildFragmentManager(), R.id.activity_restourant_container, new FragmentLogin());

                    } catch (Exception e) {
                        Log.e(TAG,e.getMessage());
                    }
                }
            }
            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {

            }
        });


    }
    @Override
    public void onDestroy() {

        super.onDestroy();
    }


    @OnClick({R.id.fragment_regester_user_img, R.id.fragment_regester_user_btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_regester_user_img:
                HelperMethods.initImage2(fragmentRegesterUserImg,getActivity());
                break;
            case R.id.fragment_regester_user_btn_confirm:
                inti();
                break;
        }
    }
}
