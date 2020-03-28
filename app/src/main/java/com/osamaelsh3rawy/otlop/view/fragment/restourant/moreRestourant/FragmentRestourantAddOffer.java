package com.osamaelsh3rawy.otlop.view.fragment.restourant.moreRestourant;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.model.restourantOffer.RestourantOffer;
import com.osamaelsh3rawy.otlop.helper.DateModel;
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
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.replaceFragment;


public class FragmentRestourantAddOffer extends BaseFragment {

    @BindView(R.id.fragment_restorant_add_meal_offer_price_txt_from)
    TextView fragmentRestorantAddMealOfferPriceTxtFrom;
    @BindView(R.id.fragment_restorant_add__offer_img)
    ImageView fragmentRestorantAddOfferImg;
    @BindView(R.id.fragment_restorant_add_meal_offer_price_txt_to)
    TextView fragmentRestorantAddMealOfferPriceTxtTo;
    @BindView(R.id.fragment_restorant_add_meal_name)
    EditText fragmentRestorantAddMealName;
    @BindView(R.id.fragment_restorant_add_meal_description)
    EditText fragmentRestorantAddMealDescription;
    @BindView(R.id.fragment_restorant_add_meal_offer_price)
    EditText fragmentRestorantAddMealOfferPrice;
    @BindView(R.id.fragment_restorant_add_meal_offer_price_in_offer)
    EditText fragmentRestorantAddMealOfferPriceInOffer;
    private ApiServies apiServies;
    private String path;
    DateModel dataModel;
    private String TAG="FragmentRestourantAddOffer";
    MultipartBody.Part photo;
    RequestBody name, description, price, starting_at, tokin, ending_at, priceInOffer;

    public FragmentRestourantAddOffer() {
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
        View view = inflater.inflate(R.layout.fragment_resturant_add_offer, container, false);
        ButterKnife.bind(this, view);
        intiFragment();


        return view;
    }

    private void initImage() {
        Album.initialize(AlbumConfig.newBuilder(getContext())
                .setAlbumLoader(new MediaLoader())
                .build());
        Album.image(getContext()) // Image selection.
                .singleChoice()
                .camera(true)
                .columnCount(3)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        path = result.get(0).getPath();
                        HelperMethods.onLoadImageFromUrl(fragmentRestorantAddOfferImg, path, getContext());
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                    }
                })
                .start();
    }

    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @OnClick({R.id.fragment_restorant_add__offer_img, R.id.fragment_restorant_add_btn_confirm, R.id.fragment_restorant_add_meal_offer_price_img_from, R.id.fragment_restorant_add_meal_offer_price_img_to})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_restorant_add__offer_img:
                initImage();
                break;
            case R.id.fragment_restorant_add_meal_offer_price_img_from:
                dataModel = new DateModel("02", "02", "2020", "02-02-2020");
                HelperMethods.showCalender(getActivity(), "choose Date", fragmentRestorantAddMealOfferPriceTxtFrom, dataModel);

                break;
            case R.id.fragment_restorant_add_meal_offer_price_img_to:
                dataModel = new DateModel("02", "02", "2020", "02-02-2020");
                HelperMethods.showCalender(getActivity(), "choose Date", fragmentRestorantAddMealOfferPriceTxtTo, dataModel);
                break;
            case R.id.fragment_restorant_add_btn_confirm:
                apiServies = getClient();
                name = convertToRequestBody(fragmentRestorantAddMealName.getText().toString().trim());
                description = convertToRequestBody(fragmentRestorantAddMealDescription.getText().toString().trim());
                price = convertToRequestBody(fragmentRestorantAddMealOfferPrice.getText().toString().trim());
                photo = HelperMethods.convertFileToMultipart(path, "photo");
                tokin = convertToRequestBody(SharedPreferencesManger.LoadData(getActivity(), Restourant_Api_Takin));
                priceInOffer = convertToRequestBody(fragmentRestorantAddMealOfferPriceInOffer.getText().toString().trim());
                starting_at = convertToRequestBody(fragmentRestorantAddMealOfferPriceTxtFrom.getText().toString());
                fragmentRestorantAddMealOfferPriceTxtFrom.setTextSize(10);
                ending_at = convertToRequestBody(fragmentRestorantAddMealOfferPriceTxtTo.getText().toString());
                fragmentRestorantAddMealOfferPriceTxtTo.setTextSize(10);
                apiServies.RestourantAddOffer(description, price, starting_at, name, photo, ending_at, tokin, priceInOffer).enqueue(new Callback<RestourantOffer>() {
                    @Override
                    public void onResponse(Call<RestourantOffer> call, Response<RestourantOffer> response) {
                       try{
                           if (response.body().getStatus() == 1) {
                               replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_restourant_container, new FragmentRestourantOffer());
                               Toast.makeText(getActivity(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                           }
                           else {
                               Log.e(TAG,response.body().getMsg());
                           }
                       }catch (Exception e){
                           Log.e(TAG,e.getMessage());
                       }
                    }

                    @Override
                    public void onFailure(Call<RestourantOffer> call, Throwable t) {

                    }
                });


                break;
        }
    }
}
