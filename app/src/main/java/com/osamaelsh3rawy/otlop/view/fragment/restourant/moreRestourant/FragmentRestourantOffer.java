package com.osamaelsh3rawy.otlop.view.fragment.restourant.moreRestourant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.adapter.OffersAdapterRestourant;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.model.restourantOffer.RestourantOffer;
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


public class FragmentRestourantOffer extends BaseFragment {

    OffersAdapterRestourant offersAdapterRestourant;
    @BindView(R.id.fragment_recycler_restorant_add_offer)
    RecyclerView fragmentRecyclerRestorantAddOffer;
    private ApiServies apiServies;
    private List<RestourantOfferData> listItem = new ArrayList<>();

    public FragmentRestourantOffer() {
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
        View view = inflater.inflate(R.layout.fragment_resturant_offers, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        apiServies = getClient();
        inti();

        return view;
    }

    private void inti() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentRecyclerRestorantAddOffer.setLayoutManager(linearLayoutManager);

        offersAdapterRestourant = new OffersAdapterRestourant((BaseActivity) getActivity(), getContext(), listItem);
        fragmentRecyclerRestorantAddOffer.setAdapter(offersAdapterRestourant);
        String tokin=SharedPreferencesManger.LoadData(getActivity(), Restourant_Api_Takin);

        apiServies.RestourantGetOffer(tokin, 1).enqueue(new Callback<RestourantOffer>() {
            @Override
            public void onResponse(Call<RestourantOffer> call, Response<RestourantOffer> response) {
                if (response.body().getStatus()==1) {
                listItem.addAll(response.body().getData().getData());
                offersAdapterRestourant.notifyDataSetChanged();
            }}

            @Override
            public void onFailure(Call<RestourantOffer> call, Throwable t) {

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

    @OnClick(R.id.fragment_restorant_add_btn_offer)
    public void onViewClicked() {

        HelperMethods.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_restourant_container, new FragmentRestourantAddOffer());
    }
}
