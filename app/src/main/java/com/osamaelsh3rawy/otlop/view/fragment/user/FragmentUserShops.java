package com.osamaelsh3rawy.otlop.view.fragment.user;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.adapter.ShopsAdapter;
import com.osamaelsh3rawy.otlop.adapter.Sppineradpter;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.model.listOfAreaNot.ListOfAreaNot;
import com.osamaelsh3rawy.otlop.data.model.userListOfRestourante.ListOfRestourante;
import com.osamaelsh3rawy.otlop.data.model.userListOfRestourante.RestourantesData;
import com.osamaelsh3rawy.otlop.data.model.userRestourantFilter.RestourantFilter;
import com.osamaelsh3rawy.otlop.data.model.userRestourantFilter.RestouranteFilterData;
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
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.dismissProgressDialog;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.showProgressDialog;


public class FragmentUserShops extends BaseFragment {


    @BindView(R.id.recycler_shopes)
    RecyclerView recyclerShopes;
    @BindView(R.id.fragment_user_shop_et_search)
    EditText fragmentUserShopEtSearch;
    @BindView(R.id.fragment_user_shop_spinner)
    Spinner fragmentUserShopSpinner;
    private ArrayList<RestourantesData> listOfRestourant = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private ApiServies apiServies;
    private ShopsAdapter restourantesAdapter;
    private OnEndLess onEndLess;
    private int MaxPage;
    Sppineradpter sppineradpter;
    private String TAG = "FragmentUserShops";
    List<RestouranteFilterData> restouranteFilterData = new ArrayList<>();

    public FragmentUserShops() {
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

        View view = inflater.inflate(R.layout.fragment_user_shops, container, false);
        intiFragment();
        ButterKnife.bind(this, view);
        apiServies = getClient();


        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerShopes.setLayoutManager(linearLayoutManager);
        restourantesAdapter = new ShopsAdapter(getActivity(), (BaseActivity) getActivity(), listOfRestourant);
        recyclerShopes.setAdapter(restourantesAdapter);

        getCity();
        sppineradpter = new Sppineradpter(getActivity());
        fragmentUserShopSpinner.setAdapter(sppineradpter);
        getAllRestaurant(1);

        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= MaxPage) {
                    if (MaxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        getAllRestaurant(current_page);
                        restourantesAdapter.notifyDataSetChanged();
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                }
            }
        };

        recyclerShopes.addOnScrollListener(onEndLess);

        return view;
    }

    private void getAllRestaurant(int page) {
        showProgressDialog(getActivity(), getString(R.string.please_wait));
        apiServies.getAllRestourants(page).enqueue(new Callback<ListOfRestourante>() {
            @Override
            public void onResponse(Call<ListOfRestourante> call, Response<ListOfRestourante> response) {
                dismissProgressDialog();
                try {
                    if (response.body().getStatus() == 1) {
                        listOfRestourant.addAll(response.body().getData().getData());
                        MaxPage = response.body().getData().getLastPage();
                        restourantesAdapter.notifyDataSetChanged();
                    } else {
                        Log.e(TAG, response.body().getMsg());
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ListOfRestourante> call, Throwable t) {

            }
        });

    }

    private void getCity() {
        apiServies.ListCities().enqueue(new Callback<ListOfAreaNot>() {
            @Override
            public void onResponse(Call<ListOfAreaNot> call, Response<ListOfAreaNot> response) {

                if (response.body().getStatus() == 1) {
                    sppineradpter.setData((response.body().getData()),"City");
                    fragmentUserShopSpinner.setAdapter(sppineradpter);
                }

            }

            @Override
            public void onFailure(Call<ListOfAreaNot> call, Throwable t) {

            }
        });
    }


    @Override
    public void onBack() {
        getActivity().finish();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }


    @OnClick(R.id.fragment_user_shop_img_search)
    public void onViewClicked() {
        String search = fragmentUserShopEtSearch.getText().toString();
        showProgressDialog(getActivity(), getString(R.string.please_wait));
        apiServies.restourantesFilter(search, fragmentUserShopSpinner.getSelectedItemPosition()).enqueue(new Callback<ListOfRestourante>() {
            @Override
            public void onResponse(Call<ListOfRestourante> call, Response<ListOfRestourante> response) {
                dismissProgressDialog();
                try {
                    if (response.body().getStatus() == 1) {
                        listOfRestourant.clear();
                        listOfRestourant.addAll(response.body().getData().getData());
                        restourantesAdapter.notifyDataSetChanged();
                    } else {
                        Log.e(TAG, response.body().getMsg());
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ListOfRestourante> call, Throwable t) {

            }
        });


    }
}
