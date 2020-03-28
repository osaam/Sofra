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
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.model.restourantCategory.RestourantCategoryData;
import com.osamaelsh3rawy.otlop.data.model.restourantItemMeal.RestourantItemMeal;
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
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.dismissProgressDialog;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.initImage2;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.showProgressDialog;


public class FragmentRestourantAddMeal extends BaseFragment {


    @BindView(R.id.fragment_restorant_add_offer_img)
    ImageView fragmentRestorantAddOfferImg;
    @BindView(R.id.fragment_restorant_add_meal_name)
    EditText fragmentRestorantAddMealName;
    @BindView(R.id.fragment_restorant_add_meal_description)
    EditText fragmentRestorantAddMealDescription;
    @BindView(R.id.fragment_restorant_add_meal_price)
    EditText fragmentRestorantAddMealPrice;
    @BindView(R.id.fragment_restorant_add_meal_price_in_offer)
    EditText fragmentRestorantAddMealPriceInOffer;
    @BindView(R.id.fragment_restorant_add_meal_time)
    EditText fragmentRestorantAddMealTime;
    private String path;
    public RestourantCategoryData categoryData;
    MultipartBody.Part photo;
    RequestBody name, description, price, priceInOffer, time, tokin, category;
    private ApiServies apiServies;
    private String TAG = "FragmentRestourantAddMeal";

    public FragmentRestourantAddMeal() {
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
        View view = inflater.inflate(R.layout.fragment_resturant_add_meal, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        apiServies = getClient();


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

    @OnClick({R.id.fragment_restorant_add_offer_img, R.id.fragment_restorant_add_btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_restorant_add_offer_img:
                initImage2(fragmentRestorantAddOfferImg,getActivity());
                break;
            case R.id.fragment_restorant_add_btn_confirm:

                name = convertToRequestBody(fragmentRestorantAddMealName.getText().toString().trim());
                description = convertToRequestBody(fragmentRestorantAddMealDescription.getText().toString().trim());
                price = convertToRequestBody(fragmentRestorantAddMealPrice.getText().toString().trim());
                priceInOffer = convertToRequestBody(fragmentRestorantAddMealPriceInOffer.getText().toString().trim());
                time = convertToRequestBody(fragmentRestorantAddMealTime.getText().toString().trim());
                photo = HelperMethods.convertFileToMultipart(path, "photo");
                tokin = convertToRequestBody(SharedPreferencesManger.LoadData(getActivity(), Restourant_Api_Takin));
                category = convertToRequestBody(String.valueOf(categoryData.getId()));

                //api
                showProgressDialog(getActivity(), getString(R.string.please_wait));
                apiServies.RestourantAddMeal(description, price, time, name, photo, tokin, priceInOffer, category).enqueue(new Callback<RestourantItemMeal>() {

                    @Override
                    public void onResponse(Call<RestourantItemMeal> call, Response<RestourantItemMeal> response) {
                        try {
                            dismissProgressDialog();
                            if (response.body().getStatus() == 1) {
                                FragmentRestourantAddMeal.super.onBack();

                             //   HelperMethods.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_restourant_container, new FragmentRestourantItemMeal());
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
