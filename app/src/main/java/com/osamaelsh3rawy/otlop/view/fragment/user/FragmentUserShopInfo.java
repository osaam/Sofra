package com.osamaelsh3rawy.otlop.view.fragment.user;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.model.userListOfRestourante.RestourantesData;
import com.osamaelsh3rawy.otlop.data.model.userRestourantInfo.RestourantInfo;
import com.osamaelsh3rawy.otlop.data.model.userRestourantInfo.RestourantInfoData;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;


public class FragmentUserShopInfo extends BaseFragment {

    public RestourantesData restourantesData;
    public RestourantInfoData restourantInfoData;
    @BindView(R.id.fragment_user_shop_info_txt_aveliblity_res)
    TextView fragmentUserShopInfoTxtAveliblity;
    @BindView(R.id.fragment_user_shop_info_txt_city_res)
    TextView fragmentUserShopInfoTxtCity;
    @BindView(R.id.fragment_user_shop_info_txt_area_res)
    TextView fragmentUserShopInfoTxtArea;
    @BindView(R.id.fragment_user_shop_info_txt_minimum_res)
    TextView fragmentUserShopInfoTxtMinimum;
    @BindView(R.id.fragment_user_shop_info_txt_delevary_res)
    TextView fragmentUserShopInfoTxtDelevaryFee;
    private ApiServies apiServies;
    private  String TAG="FragmentUserShopInfo";

    public FragmentUserShopInfo() {
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
        View view = inflater.inflate(R.layout.fragment_user_shop_info, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        apiServies = getClient();
        inti();


        return view;
    }

    private void inti() {
        apiServies.RestaurantInfo(restourantesData.getId()).enqueue(new Callback<RestourantInfo>() {
            @Override
            public void onResponse(Call<RestourantInfo> call, Response<RestourantInfo> response) {
                try {if (response.body().getStatus() == 1) {
                        fragmentUserShopInfoTxtAveliblity.setText(restourantInfoData.getAvailability());
                        fragmentUserShopInfoTxtCity.setText(response.body().getData().getRegion().getCity().getName());
                        fragmentUserShopInfoTxtArea.setText(response.body().getData().getRegion().getName());
                        fragmentUserShopInfoTxtMinimum.setText(restourantInfoData.getMinimumCharger());
                        fragmentUserShopInfoTxtDelevaryFee.setText(restourantInfoData.getDeliveryCost());
                    }
                else{
                        Log.e(TAG,response.body().getMsg());
                }
                }catch (Exception e){
                    Log.e(TAG,e.getMessage());
                }
                }

                @Override
                public void onFailure (Call < RestourantInfo > call, Throwable t){

                }
            });
        }

        @Override
        public void onBack () {
            super.onBack();
        }

        @Override
        public void onDestroy () {

            super.onDestroy();
        }
    }
