package com.osamaelsh3rawy.otlop.view.fragment.user;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.adapter.Sppineradpter;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.Constanse;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
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
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Photo;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.User_Api_Takin;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.User_Email;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.User_Name;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.User_Phone;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.User_Photo;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.User_reagon;
import static com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger.SaveData;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.convertToRequestBody;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.dismissProgressDialog;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.showProgressDialog;


public class FragmentUserMyAccount extends BaseFragment {


    @BindView(R.id.fragment_user_my_account_img)
    ImageView fragmentUserMyAccountImg;
    @BindView(R.id.fragment_user_my_account_txt_name)
    EditText fragmentUserMyAccountTxtName;
    @BindView(R.id.fragment_user_my_account_txt_email)
    EditText fragmentUserMyAccountTxtEmail;
    @BindView(R.id.fragment_user_my_account_txt_phone)
    EditText fragmentUserMyAccountTxtPhone;
    @BindView(R.id.fragment_user_my_account_et_city)
    EditText fragmentUserMyAccountEtCity;
    @BindView(R.id.fragment_user_my_account_sp_city)
    Spinner fragmentUserMyAccountSpCity;
    @BindView(R.id.fragment_user_my_account_te_area)
    EditText fragmentUserMyAccountTeArea;
    @BindView(R.id.fragment_user_my_account_sp_area)
    Spinner fragmentUserMyAccountSpArea;
    ApiServies apiServies;
    private String path;
    Sppineradpter cityAdapter, areaAdapter;
    RequestBody name, email, phone, token, city, area;
    MultipartBody.Part photo;
    String TAG = "FragmentUserMyAccount";

    public FragmentUserMyAccount() {
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
        View view = inflater.inflate(R.layout.fragment_user_my_account, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        apiServies = getClient();
        inticity();
        setData();

        return view;
    }

    private void setData() {
        fragmentUserMyAccountTxtName.setText(SharedPreferencesManger.LoadData(getActivity(), Constanse.User_Name));
        fragmentUserMyAccountTxtEmail.setText(SharedPreferencesManger.LoadData(getActivity(), Constanse.User_Email));
        fragmentUserMyAccountTxtPhone.setText(SharedPreferencesManger.LoadData(getActivity(), Constanse.User_Phone));
        fragmentUserMyAccountEtCity.setText(SharedPreferencesManger.LoadData(getActivity(), Constanse.User_City));
        fragmentUserMyAccountTeArea.setText(SharedPreferencesManger.LoadData(getActivity(), Constanse.User_reagon));
        Glide.with(getContext()).load(SharedPreferencesManger.LoadData(getActivity(), Constanse.User_Photo)).into(fragmentUserMyAccountImg);
    }

    private void inticity() {
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
                            fragmentUserMyAccountSpArea.setAdapter(areaAdapter);
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
                    fragmentUserMyAccountSpCity.setAdapter(cityAdapter);
                    fragmentUserMyAccountSpCity.setOnItemSelectedListener(listener);
                }
            }

            @Override
            public void onFailure(Call<ListOfAreaNot> call, Throwable t) {
            }
        }); }

    private void Edit() {
        name = convertToRequestBody(fragmentUserMyAccountTxtName.getText().toString().trim());
        email = convertToRequestBody(fragmentUserMyAccountTxtEmail.getText().toString().trim());
        phone = convertToRequestBody(fragmentUserMyAccountTxtPhone.getText().toString().trim());
        city = convertToRequestBody(String.valueOf(cityAdapter.selectedId));
        area = convertToRequestBody(String.valueOf(areaAdapter.selectedId));
        photo = HelperMethods.convertFileToMultipart(path, "photo");
        token = convertToRequestBody(SharedPreferencesManger.LoadData(getActivity(), User_Api_Takin));

        showProgressDialog(getActivity(), getString(R.string.please_wait));
        apiServies.UserEditProfile(token, name, phone, email, photo, area).enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                try {
                    dismissProgressDialog();
                    if (response.body().getStatus() == 1) {
                        SaveData(getActivity(), User_Name, response.body().getData().getUser().getName());
                        SaveData(getActivity(), User_Email, response.body().getData().getUser().getEmail());
                        SaveData(getActivity(), User_reagon, response.body().getData().getUser().getRegionId());
                        SaveData(getActivity(), User_Phone, response.body().getData().getUser().getPhone());
                        SaveData(getActivity(), User_Photo, response.body().getData().getUser().getPhotoUrl());

                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, response.body().getMsg());

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

    //class
//    private void initImage() {
//        Album.initialize(AlbumConfig.newBuilder(getActivity())
//                .setAlbumLoader(new MediaLoader())
//                .build());
//        Album.image(this) // Image selection.
//                .singleChoice()
//                .camera(true)
//                .columnCount(3)
//                .onResult(new Action<ArrayList<AlbumFile>>() {
//                    @Override
//                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
//                        path = result.get(0).getPath();
//                        HelperMethods.onLoadImageFromUrl(fragmentUserMyAccountImg, path, getActivity());
//                    }
//                })
//                .onCancel(new Action<String>() {
//                    @Override
//                    public void onAction(@NonNull String result) {
//                    }
//                })
//                .start();
//    }


    @OnClick({R.id.fragment_user_my_account_img, R.id.fragment_splash_choose_btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_user_my_account_img:

                HelperMethods.initImage2(fragmentUserMyAccountImg,getActivity());
                break;
            case R.id.fragment_splash_choose_btn_confirm:
                Edit();
                break;
        }
    }
}
