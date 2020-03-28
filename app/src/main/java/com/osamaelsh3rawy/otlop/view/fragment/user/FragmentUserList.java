package com.osamaelsh3rawy.otlop.view.fragment.user;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.adapter.ListAdapter;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.model.items.ItemsData;
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
import static com.osamaelsh3rawy.otlop.data.local.Constanse.User_Api_Takin;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.dismissProgressDialog;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.showProgressDialog;


public class FragmentUserList extends BaseFragment {

    @BindView(R.id.item_recycler_list)
    RecyclerView itemRecyclerList;

    private List<MyOrderData> listOrderData = new ArrayList<>();

    private OnEndLess onEndLess;

    private int MaxPage;
    ApiServies apiServies;

    private ListAdapter listAdapter;
    public String status;
    private String TAG = "FragmentUserList";
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

        itemRecyclerList.addOnScrollListener(onEndLess);

        listAdapter = new ListAdapter(getContext(), (BaseActivity) getActivity(), listOrderData, status);
        itemRecyclerList.setAdapter(listAdapter);

        getMyOrder(1);

    }

    private void getMyOrder(int current_page) {

        String api = SharedPreferencesManger.LoadData(getActivity(), User_Api_Takin);
        apiServies.MyOrder(api, status, current_page).enqueue(new Callback<MyOrder>() {
            @Override
            public void onResponse(Call<MyOrder> call, Response<MyOrder> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        MaxPage = response.body().getData().getLastPage();
                        listOrderData.addAll(response.body().getData().getData());
                        listAdapter.notifyDataSetChanged();
                    } else {
                        Log.e(TAG, response.body().getMsg());
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<MyOrder> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

}
