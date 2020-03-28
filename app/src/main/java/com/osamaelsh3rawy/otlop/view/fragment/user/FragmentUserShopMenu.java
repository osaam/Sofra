package com.osamaelsh3rawy.otlop.view.fragment.user;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.adapter.CategoriesAdapter;
import com.osamaelsh3rawy.otlop.adapter.MenuAdapter;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.model.userCtegories.Categories;
import com.osamaelsh3rawy.otlop.data.model.userCtegories.CategoriesData;
import com.osamaelsh3rawy.otlop.data.model.items.Items;
import com.osamaelsh3rawy.otlop.data.model.items.ItemsData;
import com.osamaelsh3rawy.otlop.data.model.userListOfRestourante.RestourantesData;
import com.osamaelsh3rawy.otlop.helper.OnEndLess;
import com.osamaelsh3rawy.otlop.view.activity.BaseActivity;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.dismissProgressDialog;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.showProgressDialog;


public class FragmentUserShopMenu extends BaseFragment {

    @BindView(R.id.item_recycler_shops_shortcuts)
    RecyclerView itemRecyclerShopsShortcuts;
    @BindView(R.id.item_recycler_shops_item)
    RecyclerView itemRecyclerShopsItem;
    private LinearLayoutManager linearLayoutManager;
    LinearLayoutManager layoutManager;
    private ApiServies apiServies;
    private OnEndLess onEndLess;
    private int MaxPage;
    private List<ItemsData> listItem = new ArrayList<>();
    private MenuAdapter menuAdapter;
    private List<CategoriesData> categorylist = new ArrayList<>();
    public RestourantesData restourantesData;
    private int id = 0;
    private CategoriesAdapter categoriesAdapter;

    public ItemsData itemsData;
    private String TAG="FragmentUserShopMenu";

    public FragmentUserShopMenu() {
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
        View view = inflater.inflate(R.layout.fragment_user_shops_menu, container, false);
        ButterKnife.bind(this, view);
        intiFragment();

        apiServies = getClient();

        inti();
        intiCategories();
        return view;
    }


    private void inti() {
        //items
        linearLayoutManager = new LinearLayoutManager(getActivity());
        itemRecyclerShopsItem.setLayoutManager(linearLayoutManager);


        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= MaxPage) {
                    if (MaxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;

                        getAllMenu(restourantesData.getId(),id,1);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                }
            }
        };



        getAllMenu(restourantesData.getId(),id,1);
    }


    public void getAllMenu(int restourant_id, int categori_id, int current_page) {
        apiServies.getItems(restourant_id,categori_id, current_page).enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {
                try{
                    if (response.body().getStatus() == 1) {
                        listItem.clear();
                        listItem.addAll(response.body().getData().getData());

                        menuAdapter = new MenuAdapter((BaseActivity) getActivity(), getActivity(), listItem);
                        itemRecyclerShopsItem.setAdapter(menuAdapter);
                    }
                    else {
                        Log.e(TAG,response.body().getMsg());
                    }
                }
                catch (Exception e){
                    Log.e(TAG,e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Items> call, Throwable t) {

            }
        });
    }

    private void intiCategories() {
        //shortcuts
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        itemRecyclerShopsShortcuts.setLayoutManager(layoutManager);
        categoriesAdapter = new CategoriesAdapter(getActivity(), categorylist,FragmentUserShopMenu.this,restourantesData.getId());
        itemRecyclerShopsShortcuts.setAdapter(categoriesAdapter);

        getAllCategories();
    }

    private void getAllCategories() {
        showProgressDialog(getActivity(), getString(R.string.please_wait));
        apiServies.categories(restourantesData.getId()).enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
               dismissProgressDialog();
                if (response.body().getStatus() == 1) {
                    categorylist.clear();
                    categorylist.addAll(response.body().getData());

                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {

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


}
