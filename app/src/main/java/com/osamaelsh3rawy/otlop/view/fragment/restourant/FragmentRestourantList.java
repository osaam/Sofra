package com.osamaelsh3rawy.otlop.view.fragment.restourant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.adapter.ListAdapter;
import com.osamaelsh3rawy.otlop.adapter.ListAdapterRestourant;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.model.restourantMyOrder.RestourantMyOrder;
import com.osamaelsh3rawy.otlop.data.model.restourantMyOrder.RestourantMyOrderData;
import com.osamaelsh3rawy.otlop.data.model.userMyOrder.MyOrder;
import com.osamaelsh3rawy.otlop.data.model.userMyOrder.MyOrderData;
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
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Api_Takin;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.dismissProgressDialog;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.showProgressDialog;


public class FragmentRestourantList extends BaseFragment {

    public String state;
    @BindView(R.id.item_recycler_list)
    RecyclerView itemRecyclerList;
    private List<RestourantMyOrderData> listOrderData = new ArrayList<>();
    private OnEndLess onEndLess;
    private int MaxPage;
    ApiServies apiServies;
    private ListAdapterRestourant listAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        apiServies = getClient();
        ButterKnife.bind(this, view);
        intiFragment();
        inti();
        return view;

    }

    private void inti() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        itemRecyclerList.setLayoutManager(linearLayoutManager);
        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= MaxPage) {
                    if (MaxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;

                        getMyOrder(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                }
            }
        };

        listAdapter = new ListAdapterRestourant(getContext(), (BaseActivity) getActivity(), listOrderData, state);
        itemRecyclerList.setAdapter(listAdapter);

        getMyOrder(1);

    }

    private void getMyOrder(int current_page) {
        apiServies.RestourantMyOrder(SharedPreferencesManger.LoadData(getActivity(), Restourant_Api_Takin), state, current_page).enqueue(new Callback<RestourantMyOrder>() {
            @Override
            public void onResponse(Call<RestourantMyOrder> call, Response<RestourantMyOrder> response) {
                if (response.body().getStatus() == 1) {
                    listOrderData.addAll(response.body().getData().getData());
                    MaxPage = response.body().getData().getLastPage();
                    listAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RestourantMyOrder> call, Throwable t) {

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
