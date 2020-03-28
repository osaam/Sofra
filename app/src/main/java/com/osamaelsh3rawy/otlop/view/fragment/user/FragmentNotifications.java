package com.osamaelsh3rawy.otlop.view.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.adapter.NotificationAdapter;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.Constanse;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.model.userNotifications.NotificationData;
import com.osamaelsh3rawy.otlop.data.model.userNotifications.Notifications;
import com.osamaelsh3rawy.otlop.helper.OnEndLess;
import com.osamaelsh3rawy.otlop.view.activity.ActivityUserLoginCycle;
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


public class FragmentNotifications extends BaseFragment {


    @BindView(R.id.recycler_notify)
    RecyclerView recyclerNotify;
    private ApiServies apiServies;
    private LinearLayoutManager linearLayoutManager;
    private OnEndLess onEndLess;
    private int MaxPage;
    private List<NotificationData> notificationList = new ArrayList<>();
    private NotificationAdapter notificationAdapter;
    private String TAG = "FragmentNotifications";

    public FragmentNotifications() {
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
        View view = inflater.inflate(R.layout.fragment_user_notify, container, false);
        ButterKnife.bind(this, view);
        intiFragment();

        apiServies = getClient();
        inti();

        return view;
    }

    private void inti() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerNotify.setLayoutManager(linearLayoutManager);

        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= MaxPage) {
                    if (MaxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        getAllNotifications();
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                }
            }
        };

        notificationAdapter = new NotificationAdapter(getActivity(), (BaseActivity) getActivity(), notificationList);
        recyclerNotify.setAdapter(notificationAdapter);
        recyclerNotify.addOnScrollListener(onEndLess);
        getAllNotifications();

    }

    private void getAllNotifications(){

        apiServies.getNotifications(SharedPreferencesManger.LoadData(getActivity(),Constanse.User_Api_Takin)).enqueue(new Callback<Notifications>() {
            @Override
            public void onResponse(Call<Notifications> call, Response<Notifications> response) {

                    try {
                        if (response.body().getStatus() == 1) {
                            notificationList.addAll(response.body().getData().getData());
                            notificationAdapter.notifyDataSetChanged();
                            MaxPage = response.body().getData().getLastPage();
                        } else {
                            Log.e(TAG, response.body().getMsg());
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

            @Override
            public void onFailure(Call<Notifications> call, Throwable t) {

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
