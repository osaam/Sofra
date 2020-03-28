package com.osamaelsh3rawy.otlop.view.fragment.restourantLogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.adapter.Sppineradpter;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.model.listOfAreaNot.ListOfAreaNot;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.replaceFragment;


public class FragmentRestourantRigester extends BaseFragment {

    @BindView(R.id.fragment_regester_restorant_et_mini)
    EditText fragmentRegesterRestorantEtMini;
    @BindView(R.id.fragment_regester_restorant_et_delevary)
    EditText fragmentRegesterRestorantEtDelevary;
    private String path;

    @BindView(R.id.fragment_login_restorant_txt_name)
    EditText fragmentLoginRestorantTxtName;
    @BindView(R.id.fragment_regester_restorant_et_email)
    EditText fragmentRegesterRestorantEtEmail;
    @BindView(R.id.fragment_regester_restorant_et_time)
    EditText fragmentRegesterRestorantEtTime;
    @BindView(R.id.fragment_regester_restorant_et_city)
    EditText fragmentRegesterRestorantEtCity;
    @BindView(R.id.fragment_regester_restorant_sp_city)
    Spinner fragmentRegesterRestorantSpCity;
    @BindView(R.id.fragment_regester_restorant_et_regon)
    EditText fragmentRegesterRestorantEtRegon;
    @BindView(R.id.fragment_regester_restorant_sp_regon)
    Spinner fragmentRegesterRestorantSpRegon;
    @BindView(R.id.fragment_regester_restorant_et_pass)
    EditText fragmentRegesterRestorantEtPass;
    @BindView(R.id.fragment_regester_restorant_et_pass_confirm)
    EditText fragmentRegesterRestorantEtPassConfirm;
    @BindView(R.id.fragment_regester_restorant_btn_continue)
    Button fragmentRegesterRestorantBtnContinue;
    Sppineradpter cityAdapter, areaAdapter;
    private ApiServies apiServies;
    private int cityId;

    public FragmentRestourantRigester() {
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
        View view = inflater.inflate(R.layout.fragment_resturant_regester1, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        apiServies = getClient();
        inticity();


        return view;
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
                            fragmentRegesterRestorantSpRegon.setAdapter(areaAdapter);
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
                    fragmentRegesterRestorantSpCity.setAdapter(cityAdapter);
                    fragmentRegesterRestorantSpCity.setOnItemSelectedListener(listener);
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

    private void setdata() {

        Bundle bundle = new Bundle();
        bundle.putString("name", fragmentLoginRestorantTxtName.getText().toString().trim());
        bundle.putString("emali", fragmentRegesterRestorantEtEmail.getText().toString().trim());
        bundle.putString("del_time", fragmentRegesterRestorantEtTime.getText().toString().trim());
        bundle.putInt("city", cityAdapter.selectedId);
        bundle.putInt("reagon", areaAdapter.selectedId);
        bundle.putString("pass", fragmentRegesterRestorantEtPass.getText().toString().trim());
        bundle.putString("pass_confirm", fragmentRegesterRestorantEtPassConfirm.getText().toString().trim());
        bundle.putString("dele_fee", fragmentRegesterRestorantEtDelevary.getText().toString().trim());
        bundle.putString("mini", fragmentRegesterRestorantEtMini.getText().toString().trim());

        FragmentRestourantRigester2 fragmentRestourantRigester2 = new FragmentRestourantRigester2();
        fragmentRestourantRigester2.setArguments(bundle);
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_restourant_login_container, fragmentRestourantRigester2);

    }



    @OnClick(R.id.fragment_regester_restorant_btn_continue)
    public void onViewClicked() {
        setdata();
    }
}
