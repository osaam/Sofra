package com.osamaelsh3rawy.otlop.view.fragment.restourant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.adapter.Sppineradpter;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.model.listOfAreaNot.ListOfAreaNot;
import com.osamaelsh3rawy.otlop.helper.HelperMethods;
import com.osamaelsh3rawy.otlop.helper.MediaLoader;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;
import com.osamaelsh3rawy.otlop.view.fragment.restourantLogin.FragmentRestourantRigester2;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_City;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Dele_fee;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Email;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Name;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Photo;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_reagon;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.replaceFragment;


public class FragmentRestourantModify extends BaseFragment {


    @BindView(R.id.fragment_restorant_modefy_img)
    ImageView fragmentRestorantModefyImg;
    @BindView(R.id.fragment_login_restorant_txt_name)
    EditText fragmentLoginRestorantTxtName;
    @BindView(R.id.fragment_restorant_modefy_et_email)
    EditText fragmentRestorantModefyEtEmail;
    @BindView(R.id.fragment_restorant_modefy_et_city)
    EditText fragmentRestorantModefyEtCity;
    @BindView(R.id.fragment_restorant_modefy_sp_city)
    Spinner fragmentRestorantModefySpCity;
    @BindView(R.id.fragment_restorant_modefy_et_regon)
    EditText fragmentRestorantModefyEtRegon;
    @BindView(R.id.fragment_restorant_modefy_sp_regon)
    Spinner fragmentRestorantModefySpRegon;
    @BindView(R.id.fragment_restorant_modefy_et_delevary)
    EditText fragmentRestorantModefyEtDelevary;
    Sppineradpter cityAdapter, areaAdapter;
    private ApiServies apiServies;
    public String path;

    public FragmentRestourantModify() {
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
        View view = inflater.inflate(R.layout.fragment_resturant_modefy, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        apiServies=getClient();
        inti();
        inticity();
        return view;
    }

    private void inti() {
        Glide.with(getContext()).load(SharedPreferencesManger.LoadData(getActivity(),Restourant_Photo)).into(fragmentRestorantModefyImg);
        fragmentLoginRestorantTxtName.setText(SharedPreferencesManger.LoadData(getActivity(), Restourant_Name));
        fragmentRestorantModefyEtEmail.setText(SharedPreferencesManger.LoadData(getActivity(), Restourant_Email));
      fragmentRestorantModefyEtCity.setText(SharedPreferencesManger.LoadData(getActivity(), Restourant_City));
      fragmentRestorantModefyEtRegon.setText(SharedPreferencesManger.LoadData(getActivity(), Restourant_reagon));
        fragmentRestorantModefyEtDelevary.setText(SharedPreferencesManger.LoadData(getActivity(), Restourant_Dele_fee));
    }

    private void setdata() {

        Bundle bundle = new Bundle();
        bundle.putString("photo", path);
        bundle.putString("name", fragmentLoginRestorantTxtName.getText().toString().trim());
        bundle.putString("emali", fragmentRestorantModefyEtEmail.getText().toString().trim());
        bundle.putInt("city", cityAdapter.selectedId);
        bundle.putInt("reagon", areaAdapter.selectedId);
        bundle.putString("dele_fee", fragmentRestorantModefyEtDelevary.getText().toString().trim());

        FragmentRestourantModify2 fragmentRestourantModify2 = new FragmentRestourantModify2();
        fragmentRestourantModify2.setArguments(bundle);
        replaceFragment(getFragmentManager(), R.id.activity_restourant_container, fragmentRestourantModify2);

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
                            fragmentRestorantModefySpRegon.setAdapter(areaAdapter);
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
                    fragmentRestorantModefySpCity.setAdapter(cityAdapter);
                    fragmentRestorantModefySpCity.setOnItemSelectedListener(listener);
                }
            }

            @Override
            public void onFailure(Call<ListOfAreaNot> call, Throwable t) {

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


    @OnClick({R.id.fragment_restorant_modefy_btn_continue, R.id.fragment_restorant_modefy_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.fragment_restorant_modefy_btn_continue:
                setdata();
                break;

            case R.id.fragment_restorant_modefy_img:
                HelperMethods.initImage2(fragmentRestorantModefyImg,getActivity());

                break;
        }
    }
}
