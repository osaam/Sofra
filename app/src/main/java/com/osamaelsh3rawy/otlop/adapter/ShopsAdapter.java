package com.osamaelsh3rawy.otlop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.model.userListOfRestourante.RestourantesData;
import com.osamaelsh3rawy.otlop.view.activity.BaseActivity;
import com.osamaelsh3rawy.otlop.view.fragment.user.FragmentTabShop;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.osamaelsh3rawy.otlop.helper.HelperMethods.replaceFragment;

public class ShopsAdapter extends RecyclerView.Adapter<ShopsAdapter.ViewHolder> {

    private Context context;
    private BaseActivity activity;


    private ArrayList<RestourantesData> listOfRestourantes = new ArrayList<>();


    public ShopsAdapter(Context context, BaseActivity activity, ArrayList<RestourantesData> listOfRestourantes) {

        this.context = context;
        this.activity = (BaseActivity) activity;
        this.listOfRestourantes = listOfRestourantes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycler_shops, parent, false));


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RestourantesData restourantesData = listOfRestourantes.get(position);
        holder.position = position;

        holder.itemRecyclerShopsTxtShopName.setText(restourantesData.getName());
        holder.itemRecyclerShopsTxtDlevaryFees.append(restourantesData.getDeliveryCost());
        holder.itemRecyclerShopsTxtMinimumCharge.append(restourantesData.getMinimumCharger());
        holder.itemRecyclerShopsRatingBar.setRating(restourantesData.getRate());
        Glide.with(context).load(restourantesData.getPhotoUrl()).into(holder.itemRecyclerShopsImg);
        holder.itemRecyclerShopsRatingBar.setMax(5);

//
        holder.itemRecyclerShopsTxtOC.setText(restourantesData.getAvailability());
        if (restourantesData.getAvailability().equals("open")) {
            holder.itemRecyclerShopsShapOC.setImageResource(R.drawable.circle_open);
        } else {
            holder.itemRecyclerShopsShapOC.setImageResource(R.drawable.circle_red);
        }


    }

    @Override
    public int getItemCount() {
        return listOfRestourantes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public int position;
        @BindView(R.id.item_recycler_shops_txt_shop_name)
        TextView itemRecyclerShopsTxtShopName;
        @BindView(R.id.item_recycler_shops_rating_bar)
        RatingBar itemRecyclerShopsRatingBar;
        @BindView(R.id.item_recycler_shops_shap_o_c)
        ImageView itemRecyclerShopsShapOC;
        @BindView(R.id.item_recycler_shops_txt_o_c)
        TextView itemRecyclerShopsTxtOC;
        @BindView(R.id.item_recycler_shops_txt_minimum_charge)
        TextView itemRecyclerShopsTxtMinimumCharge;
        @BindView(R.id.item_recycler_shops_txt_dlevary_fees)
        TextView itemRecyclerShopsTxtDlevaryFees;
        @BindView(R.id.item_recycler_shops_img)
        ImageView itemRecyclerShopsImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        @OnClick(R.id.item_recycler_shops_relateve)
        public void onViewClicked() {

            FragmentTabShop fragmentTabShop = new FragmentTabShop();
            fragmentTabShop.restourantesData = listOfRestourantes.get(position);
            replaceFragment(activity.getSupportFragmentManager(), R.id.activity_user_container, fragmentTabShop);
        }
    }
}
