package com.osamaelsh3rawy.otlop.view.fragment.restourant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.adapter.ItemMealAdapter;
import com.osamaelsh3rawy.otlop.adapter.OffersAdapterRestourant;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.model.restourantCategory.RestourantCategory;
import com.osamaelsh3rawy.otlop.data.model.restourantCategory.RestourantCategoryData;
import com.osamaelsh3rawy.otlop.data.model.restourantItemMeal.RestourantItemMeal;
import com.osamaelsh3rawy.otlop.data.model.restourantItemMeal.RestourantItemMealData;
import com.osamaelsh3rawy.otlop.data.model.restourantOffer.RestourantOfferData;
import com.osamaelsh3rawy.otlop.helper.HelperMethods;
import com.osamaelsh3rawy.otlop.view.activity.BaseActivity;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Api_Takin;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.replaceFragment;


public class FragmentRestourantItemMeal extends BaseFragment {

    public RestourantCategoryData categoryData;

    @BindView(R.id.item_txt_restorant_food_meal)
    TextView itemTxtRestorantFoodMeal;
    @BindView(R.id.item_recycler_restorant_food_meal)
    RecyclerView itemRecyclerRestorantFoodMeal;
    private LinearLayoutManager linearLayoutManager;

    ItemMealAdapter itemMealAdapter;
    private List<RestourantItemMealData> listItem = new ArrayList<>();
    private ApiServies apiServies;

    public FragmentRestourantItemMeal() {
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
        View view = inflater.inflate(R.layout.fragment_restourant_item_meal, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        apiServies = getClient();
        inti();
        return view;
    }

    private void inti() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        itemRecyclerRestorantFoodMeal.setLayoutManager(linearLayoutManager);

        itemMealAdapter = new ItemMealAdapter(getContext(), (BaseActivity) getActivity(), listItem);
        itemRecyclerRestorantFoodMeal.setAdapter(itemMealAdapter);

        itemTxtRestorantFoodMeal.setText(categoryData.getName());

        apiServies.getMyItemMeal(SharedPreferencesManger.LoadData(getActivity(), Restourant_Api_Takin), categoryData.getId()).enqueue(new Callback<RestourantItemMeal>() {
            @Override
            public void onResponse(Call<RestourantItemMeal> call, Response<RestourantItemMeal> response) {
                if (response.body().getStatus() == 1) {
                    listItem.clear();
                    listItem.addAll(response.body().getData().getData());
                    itemMealAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RestourantItemMeal> call, Throwable t) {

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

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        FragmentRestourantAddMeal fragmentRestourantAddMeal = new FragmentRestourantAddMeal();
        fragmentRestourantAddMeal.categoryData = categoryData;
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_restourant_container, fragmentRestourantAddMeal);
    }
}
