package com.osamaelsh3rawy.otlop.view.fragment.restourant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.adapter.TypeFoodAdapter;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.model.restourantCategory.RestourantCategory;
import com.osamaelsh3rawy.otlop.data.model.restourantCategory.RestourantCategoryData;
import com.osamaelsh3rawy.otlop.helper.OnEndLess;
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
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.dismissProgressDialog;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.showProgressDialog;


public class FragmentRestourantTypeFood extends BaseFragment {


    @BindView(R.id.item_recycler_restorant_food_type)
    RecyclerView itemRecyclerRestorantFoodType;
    private List<RestourantCategoryData> listItem = new ArrayList<>();
    private ApiServies apiServies;
    public TypeFoodAdapter typeFoodAdapter;
    private OnEndLess onEndLess;
    private int MaxPage;

    public FragmentRestourantTypeFood() {
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
        View view = inflater.inflate(R.layout.fragment_restourant_type_food, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        apiServies = getClient();
        init();


        return view;
    }


    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        itemRecyclerRestorantFoodType.setLayoutManager(linearLayoutManager);

        typeFoodAdapter = new TypeFoodAdapter((BaseActivity) getActivity(), getContext(), listItem);
        itemRecyclerRestorantFoodType.setAdapter(typeFoodAdapter);

        getCategories();
    }

    private void getCategories() {
        //  api takin

        String tokin = SharedPreferencesManger.LoadData(getActivity(), Restourant_Api_Takin);
        showProgressDialog(getActivity(), getString(R.string.please_wait));
        apiServies.getMyCategories(tokin).enqueue(new Callback<RestourantCategory>() {
            @Override
            public void onResponse(Call<RestourantCategory> call, Response<RestourantCategory> response) {
                dismissProgressDialog();
                if (response.body().getStatus() == 1) {
                    listItem.clear();
                    listItem.addAll(response.body().getData().getData());
                    typeFoodAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RestourantCategory> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBack() {


    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {

        DialogAddCategory dialogAddCategory = new DialogAddCategory(getActivity(), getActivity(), true, typeFoodAdapter,listItem);

        dialogAddCategory.show();

    }


}
