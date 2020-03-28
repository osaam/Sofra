package com.osamaelsh3rawy.otlop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.model.restourantOffer.RestourantOffer;
import com.osamaelsh3rawy.otlop.data.model.restourantOffer.RestourantOfferData;
import com.osamaelsh3rawy.otlop.helper.HelperMethods;
import com.osamaelsh3rawy.otlop.view.activity.BaseActivity;
import com.osamaelsh3rawy.otlop.view.fragment.restourant.moreRestourant.FragmentRestourantUpdateOffer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Api_Takin;

public class OffersAdapterRestourant extends RecyclerView.Adapter<OffersAdapterRestourant.ViewHolder> {


    private BaseActivity activity;
    private Context context;
    List<RestourantOfferData> listItem = new ArrayList<>();
    private ApiServies apiServies;


    public OffersAdapterRestourant(BaseActivity activity, Context context, List<RestourantOfferData> listItem) {
        this.activity = (BaseActivity) activity;
        this.context = context;
        this.listItem = listItem;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycler_restourant_offers, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RestourantOfferData restourantOfferData = listItem.get(position);
        holder.itemRecyclerRestourantOffersTxtOffers.setText(restourantOfferData.getDescription());
        Glide.with(context).load(restourantOfferData.getPhotoUrl()).into(holder.itemRecyclerRestourantOfferImg);


    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_recycler_restourant_offers_txt_offers)
        TextView itemRecyclerRestourantOffersTxtOffers;
        @BindView(R.id.item_recycler_restourant_offer_img)
        CircleImageView itemRecyclerRestourantOfferImg;
        private int position;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        @OnClick({R.id.restourant_offer_btn_del, R.id.restourant_offer_btn_edt})
        public void onViewClicked(View view) {
            switch (view.getId()) {

                case R.id.restourant_offer_btn_del:
                    apiServies=getClient();
                    String tokin= SharedPreferencesManger.LoadData(activity, Restourant_Api_Takin);
                    apiServies.RestourantDeleteOffer(listItem.get(position).getId(),tokin).enqueue(new Callback<RestourantOffer>() {
                        @Override
                        public void onResponse(Call<RestourantOffer> call, Response<RestourantOffer> response) {
                        }
                        @Override
                        public void onFailure(Call<RestourantOffer> call, Throwable t) {
                        }
                    });
                    break;

                case R.id.restourant_offer_btn_edt:
                    FragmentRestourantUpdateOffer fragmentRestourantUpdateOffer = new FragmentRestourantUpdateOffer();
                    fragmentRestourantUpdateOffer.restourantOfferData = listItem.get(position);
                    HelperMethods.replaceFragment(activity.getSupportFragmentManager(), R.id.activity_restourant_container, fragmentRestourantUpdateOffer);
                    break;
            }
        }
    }
}
