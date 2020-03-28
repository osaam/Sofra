package com.osamaelsh3rawy.otlop.view.fragment.user.more;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.adapter.OffersAdapter;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.model.userListOfRestourante.RestourantesData;
import com.osamaelsh3rawy.otlop.data.model.userOffers.UserOffers;
import com.osamaelsh3rawy.otlop.data.model.userOffers.UserOffersData;
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


public class FragmentUserOffers extends BaseFragment {


    @BindView(R.id.recycler_offers)
    RecyclerView recyclerOffers;
    RestourantesData restourantesData;
    private ApiServies apiServies;
    private OffersAdapter offersAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<UserOffersData> listItem = new ArrayList<>();
    private String TAG = "FragmentUserOffers";
    private OnEndLess onEndLess;
    private int MaxPage;
    int restourant=1;


    public FragmentUserOffers() {
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
        View view = inflater.inflate(R.layout.fragment_user_offers, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        apiServies = getClient();
        inti();

        return view;
    }

    private void inti() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerOffers.setLayoutManager(linearLayoutManager);
        //   showProgressDialog(getActivity(), getString(R.string.please_wait));

        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= MaxPage) {
                    if (MaxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        getOffer(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                }
            }
        };

        recyclerOffers.addOnScrollListener(onEndLess);
        offersAdapter = new OffersAdapter((BaseActivity) getActivity(), getContext(), listItem);
        recyclerOffers.setAdapter(offersAdapter);
        getOffer(1);

    }

    private void getOffer(int page) {
        apiServies.offers(restourant).enqueue(new Callback<UserOffers>() {
            @Override
            public void onResponse(Call<UserOffers> call, Response<UserOffers> response) {
                //         dismissProgressDialog();
                try {
                    if (response.body().getStatus() == 1) {
                        listItem.addAll(response.body().getData().getData());
                        offersAdapter.notifyDataSetChanged();
                        MaxPage=response.body().getData().getLastPage();

                    } else {
                        Log.e(TAG, response.body().getMsg());
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());

                }
            }

            @Override
            public void onFailure(Call<UserOffers> call, Throwable t) {

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
