package com.osamaelsh3rawy.otlop.view.fragment.restourant;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.model.restourantLogin.RestourantLogin;
import com.osamaelsh3rawy.otlop.helper.HelperMethods;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.convertFileToMultipart;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.convertToRequestBody;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.dismissProgressDialog;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.showProgressDialog;


public class FragmentRestourantModify2 extends BaseFragment {
    RequestBody name, emali, city, pbone, whatsApp, reagon, mini, del_time, dele_fee, api_takin, ck;
    MultipartBody.Part photo;
    @BindView(R.id.fragment_restorant_modefy2_et_mini)
    EditText fragmentRestorantModefyEtMini;
    @BindView(R.id.fragment_restorant_modefy2_et_time)
    EditText fragmentRestorantModefyEtTime;
    @BindView(R.id.fragment_restorant_modefy2_et_phone)
    EditText fragmentRestorantModefyEtPhone;
    @BindView(R.id.fragment_restorant_modefy2_et_whatsapp)
    EditText fragmentRestorantModefyEtWhatsapp;
    @BindView(R.id.fragment_restorant_modefy2_ck_activation)
    Switch fragmentRestorantModefyCkActivation;
    private String path;
    private ApiServies apiServies;
     private String TAG="FragmentRestourantModify2";
    public FragmentRestourantModify2() {
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
        View view = inflater.inflate(R.layout.fragment_resturant_modefy2, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        apiServies = getClient();
        setData();
        getData();
        return view;
    }



    private void setData() {
        fragmentRestorantModefyEtMini.setText(SharedPreferencesManger.LoadData(getActivity(), Restourant_Mini));
        fragmentRestorantModefyEtTime.setText(SharedPreferencesManger.LoadData(getActivity(), Restourant_Dele_Time));
        fragmentRestorantModefyEtPhone.setText(SharedPreferencesManger.LoadData(getActivity(), Restourant_Phone));
        fragmentRestorantModefyEtWhatsapp.setText(SharedPreferencesManger.LoadData(getActivity(), Restourant_Whats));
    }

    private void getData() {
        name = convertToRequestBody(getArguments().getString("name"));
        emali = convertToRequestBody(getArguments().getString("email"));
        dele_fee = convertToRequestBody(getArguments().getString("dele_fee"));

        city = convertToRequestBody(String.valueOf(getArguments().getInt("city")));
        reagon = convertToRequestBody(String.valueOf(getArguments().getInt("reagon")));

//        FragmentRestourantModify fragmentRestourantModify=new FragmentRestourantModify();
//        fragmentRestourantModify.path=path;
        photo = convertFileToMultipart(path,String.valueOf(getArguments().getString( "photo")));

        pbone = convertToRequestBody(fragmentRestorantModefyEtPhone.getText().toString().trim());
        whatsApp = convertToRequestBody(fragmentRestorantModefyEtWhatsapp.getText().toString().trim());
        del_time = convertToRequestBody(fragmentRestorantModefyEtTime.getText().toString().trim());

        mini = convertToRequestBody(SharedPreferencesManger.LoadData(getActivity(), Restourant_Mini));

        api_takin = convertToRequestBody(SharedPreferencesManger.LoadData(getActivity(), Restourant_Api_Takin));

        if (SharedPreferencesManger.LoadData(getActivity(), Restourant_Avilability).equals("open")) {
            fragmentRestorantModefyCkActivation.setChecked(true);
            SharedPreferencesManger.SaveData(getActivity(), Restourant_Avilability, "open");

        } else {
            fragmentRestorantModefyCkActivation.setChecked(false);
            SharedPreferencesManger.SaveData(getActivity(), Restourant_Avilability, "close");
        }


    }

    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }


    @OnClick({R.id.fragment_restorant_modefy2_btn_confirm, R.id.fragment_restorant_modefy2_ck_activation})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.fragment_restorant_modefy2_btn_confirm:
                showProgressDialog(getActivity(), getString(R.string.please_wait));
                apiServies.RestourantModify(emali, name, pbone, reagon, dele_fee, mini, ck, photo, api_takin, del_time, whatsApp).enqueue(new Callback<RestourantLogin>() {
                    @Override
                    public void onResponse(Call<RestourantLogin> call, Response<RestourantLogin> response) {
                        try {
                            dismissProgressDialog();
                            SharedPreferencesManger.SaveData(getActivity(), Restourant_Name, response.body().getData().getUser().getName());
                            SharedPreferencesManger.SaveData(getActivity(), Restourant_Email, response.body().getData().getUser().getEmail());
                            SharedPreferencesManger.SaveData(getActivity(), Restourant_reagon, response.body().getData().getUser().getRegionId());
                            SharedPreferencesManger.SaveData(getActivity(), Restourant_Dele_fee, response.body().getData().getUser().getDeliveryCost());
                            SharedPreferencesManger.SaveData(getActivity(), Restourant_Dele_Time, response.body().getData().getUser().getDeliveryTime());
                            SharedPreferencesManger.SaveData(getActivity(), Restourant_Phone, response.body().getData().getUser().getPhone());
                            SharedPreferencesManger.SaveData(getActivity(), Restourant_Whats, response.body().getData().getUser().getWhatsapp());
                            SharedPreferencesManger.SaveData(getActivity(), Restourant_Photo, response.body().getData().getUser().getPhotoUrl());
                            SharedPreferencesManger.SaveData(getActivity(), Restourant_Avilability, response.body().getData().getUser().getAvailability());

                            Toast.makeText(getActivity(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                            HelperMethods.replaceFragment(getActivity().getSupportFragmentManager(),R.id.activity_restourant_container,new FragmentRestourantTypeFood());
                        } catch (Exception e) {
                            Log.e(TAG,e.getMessage());
                        }
                    }
                    @Override
                    public void onFailure(Call<RestourantLogin> call, Throwable t) { }
                });

                break;

            case R.id.fragment_restorant_modefy2_ck_activation:

                if (SharedPreferencesManger.LoadData(getActivity(), Restourant_Avilability).equals("open")) {
                    fragmentRestorantModefyCkActivation.setChecked(true);
                } else {
                    fragmentRestorantModefyCkActivation.setChecked(false);
                }
                break;
        }
    }
}
