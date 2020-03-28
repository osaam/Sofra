package com.osamaelsh3rawy.otlop.view.fragment.user.more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.model.userOffers.UserOffersData;
import com.osamaelsh3rawy.otlop.data.model.userOffersDetaiels.UserOffersDetaiels;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;


public class FragmentUserOffersDetaiels extends BaseFragment {


    public UserOffersData offerdata;
    @BindView(R.id.fragment_user_offer_detaiels_titel)
    TextView fragmentUserOfferDetaielsTitel;
    @BindView(R.id.fragment_user_offer_detaiels_description)
    TextView fragmentUserOfferDetaielsDescription;
    @BindView(R.id.fragment_user_offer_detaiels_from)
    TextView fragmentUserOfferDetaielsFrom;
    @BindView(R.id.fragment_user_offer_detaiels_to)
    TextView fragmentUserOfferDetaielsTo;
    @BindView(R.id.fragment_user_offer_detaiels_btn)
    Button fragmentUserOfferDetaielsBtn;
    private ApiServies apiServies;

    public FragmentUserOffersDetaiels() {
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
        View view = inflater.inflate(R.layout.fragment_user_offers_detaiels, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        apiServies = getClient();
        inti();

        return view;
    }

    private void inti() {
        apiServies.OffersDetaiels(offerdata.getId()).enqueue(new Callback<UserOffersDetaiels>() {
            @Override
            public void onResponse(Call<UserOffersDetaiels> call, Response<UserOffersDetaiels> response) {
                fragmentUserOfferDetaielsTitel.setText(offerdata.getName());
                fragmentUserOfferDetaielsDescription.setText(offerdata.getDescription());
                fragmentUserOfferDetaielsFrom.append(offerdata.getStartingAt());
                fragmentUserOfferDetaielsTo.append(offerdata.getEndingAt());
            }

            @Override
            public void onFailure(Call<UserOffersDetaiels> call, Throwable t) {

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

    @OnClick(R.id.fragment_user_offer_detaiels_btn)
    public void onViewClicked() {
    }
}
