package com.osamaelsh3rawy.otlop.view.fragment.restourant;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.adapter.ItemMealAdapter;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.model.restourantItemMeal.RestourantItemMeal;
import com.osamaelsh3rawy.otlop.data.model.restourantItemMeal.RestourantItemMealData;
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
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Api_Takin;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.convertToRequestBody;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.initImage2;


public class FragmentRestourantUpdateMeal extends BaseFragment {


    @BindView(R.id.fragment_restorant_modefy_img)
    ImageView fragmentRestorantModefyImg;
    @BindView(R.id.fragment_restorant_modefy_meal_name)
    EditText fragmentRestorantModefyMealName;
    @BindView(R.id.fragment_restorant_modefy_meal_description)
    EditText fragmentRestorantModefyMealDescription;
    @BindView(R.id.fragment_restorant_modefy_meal_price)
    EditText fragmentRestorantModefyMealPrice;
    @BindView(R.id.fragment_restorant_modefy_meal_price_in_offer)
    EditText fragmentRestorantModefyMealPriceInOffer;
    @BindView(R.id.fragment_restorant_modefy_meal_time)
    EditText fragmentRestorantModefyMealTime;
    private String TAG="FragmentRestourantUpdateMeal";

   private ItemMealAdapter itemMealAdapter;
    public RestourantItemMealData restourantItemMealData;
    private String path;
    MultipartBody.Part photo;
    RequestBody name, description, price, priceInOffer,time ,tokin,item;
    private ApiServies apiServies;


    public FragmentRestourantUpdateMeal(ItemMealAdapter itemMealAdapter) {
        // Required empty public constructor
       this.itemMealAdapter=itemMealAdapter;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restourant_update_meal, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        getData();
ApiServies apiServies=getClient();

        return view;
    }

    private void getData() {
        //get data last meal

    }

    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @OnClick({R.id.fragment_restorant_modefy_img, R.id.fragment_restorant_modefy_btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.fragment_restorant_modefy_img:
            initImage2(fragmentRestorantModefyImg,getActivity());
            break;
            case R.id.fragment_restorant_modefy_btn_confirm:

                name = convertToRequestBody(fragmentRestorantModefyMealName.getText().toString().trim());
                description = convertToRequestBody(fragmentRestorantModefyMealDescription.getText().toString().trim());
                price = convertToRequestBody(fragmentRestorantModefyMealPrice.getText().toString().trim());
                priceInOffer = convertToRequestBody(fragmentRestorantModefyMealPriceInOffer.getText().toString().trim());
                time=convertToRequestBody(fragmentRestorantModefyMealTime.getText().toString().trim());
                photo = HelperMethods.convertFileToMultipart(path, "photo");
                tokin=convertToRequestBody(SharedPreferencesManger.LoadData(getActivity(),Restourant_Api_Takin));
               item=convertToRequestBody(String.valueOf(restourantItemMealData.getId()));
                //api
                apiServies.RestourantUpdateMeal(description,price,time,name,photo,item,tokin,priceInOffer).enqueue(new Callback<RestourantItemMeal>() {
                    @Override
                    public void onResponse(Call<RestourantItemMeal> call, Response<RestourantItemMeal> response) {
                        try {
                            if (response.body().getStatus() == 1) {
                                itemMealAdapter.notifyDataSetChanged();
                                FragmentRestourantUpdateMeal.super.onBack();
//                                HelperMethods.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_restourant_container, new FragmentRestourantItemMeal());
                                Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e(TAG,response.body().getMsg());

                            }

                        } catch (Exception e) {
                            Log.e(TAG,e.getMessage());

                        }
                    }

                    @Override
                    public void onFailure(Call<RestourantItemMeal> call, Throwable t) {

                    }
                });
                break;
        }
    }
}
