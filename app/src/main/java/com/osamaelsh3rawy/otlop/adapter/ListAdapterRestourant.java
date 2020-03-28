package com.osamaelsh3rawy.otlop.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.model.restourantMyOrder.RestourantMyOrder;
import com.osamaelsh3rawy.otlop.data.model.restourantMyOrder.RestourantMyOrderData;
import com.osamaelsh3rawy.otlop.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Api_Takin;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.onPermission;

public class ListAdapterRestourant extends RecyclerView.Adapter<ListAdapterRestourant.ViewHolder> {


    ApiServies apiServies = getClient();

    private Context context;
    private BaseActivity activity;
    private List<RestourantMyOrderData> listOfUserData = new ArrayList<>();
    private String status;
    private RestourantMyOrderData myOrderData;


    public ListAdapterRestourant(Context context, BaseActivity activity, List<RestourantMyOrderData> listOfUserData, String status) {

        this.context = context;
        this.activity = (BaseActivity) activity;
        this.listOfUserData = listOfUserData;
        this.status = status;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycler_restourant_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        myOrderData = listOfUserData.get(position);

        holder.itemRecyclerRestourantShopsTxtShopName.setText(myOrderData.getRestaurant().getName());
        holder.itemRecyclerRestourantShopsTxtDlevaryAddress.setText(myOrderData.getAddress());
        holder.itemRecyclerRestourantShopsTxtTotal.setText(myOrderData.getTotal());
      //  holder.itemRecyclerRestouranrShopsTxtShopNum.setText(myOrderData.getId());
        Glide.with(context).load(myOrderData.getRestaurant().getPhotoUrl()).into(holder.itemRecyclerRestourantShopsImg);
        holder.linearCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPermission(activity);
                activity.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", listOfUserData.get(position).getClient().getPhone(), null)));
            }
        });

        if (status.equals("pending")) {
            holder.linearAccepted.setVisibility(View.VISIBLE);
            holder.linearCall.setVisibility(View.VISIBLE);
            holder.linearClose.setVisibility(View.VISIBLE);

            holder.linearAccepted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tokin = SharedPreferencesManger.LoadData(activity, Restourant_Api_Takin);
                    apiServies.RestaurantAcceptOrder(myOrderData.getId(), tokin).enqueue(new Callback<RestourantMyOrder>() {
                        @Override
                        public void onResponse(Call<RestourantMyOrder> call, Response<RestourantMyOrder> response) {
                            notifyDataSetChanged();
                            listOfUserData.remove(position);
                        }
                        @Override
                        public void onFailure(Call<RestourantMyOrder> call, Throwable t) {
                        }
                    });
                }
            });

            holder.linearClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = new Dialog(activity);
                    LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = inflater.inflate(R.layout.dialog_reject_order, null);
                    dialog.setContentView(v);

                    Button cancelBtn = (Button) dialog.findViewById(R.id.dialog_reject_btn);
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(activity, "Order Canceled", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                    listOfUserData.remove(position);
                    notifyDataSetChanged();
                }
            });
        } else if (status.equals("current")) {
            holder.itemRecyclerRestourantListTxtAccepted.setText("transform compleated");
            holder.itemRecyclerRestourantListTxtAccepted.setTextSize(10);
            holder.linearClose.setVisibility(View.GONE);

        } else if (status.equals("completed")) {
            holder.itemRecyclerRestourantListTxtAccepted.setText("order compleated");
            holder.linearCall.setVisibility(View.GONE);
            holder.linearClose.setVisibility(View.GONE);
            holder.linearAccepted.getLayoutParams().width = 200;
         //   notifyDataSetChanged();

        } }
    @Override
    public int getItemCount() {
        return listOfUserData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_recycler_restourant_shops_txt_shop_name)
        TextView itemRecyclerRestourantShopsTxtShopName;
        @BindView(R.id.item_recycler_restouranr_shops_txt_shop_num)
        TextView itemRecyclerRestouranrShopsTxtShopNum;
        @BindView(R.id.item_recycler_restourant_shops_txt_total)
        TextView itemRecyclerRestourantShopsTxtTotal;
        @BindView(R.id.item_recycler_restourant_shops_txt_dlevary_address)
        TextView itemRecyclerRestourantShopsTxtDlevaryAddress;
        @BindView(R.id.item_recycler_restourant_shops_img)
        ImageView itemRecyclerRestourantShopsImg;
        @BindView(R.id.img_call)
        ImageView imgCall;
        @BindView(R.id.item_recycler_restourant_list_txt_call)
        TextView itemRecyclerRestourantListTxtCall;
        @BindView(R.id.linear_call)
        LinearLayout linearCall;
        @BindView(R.id.img_accepted)
        ImageView imgAccepted;
        @BindView(R.id.item_recycler_restourant_list_txt_accepted)
        TextView itemRecyclerRestourantListTxtAccepted;
        @BindView(R.id.linear_accepted)
        LinearLayout linearAccepted;
        @BindView(R.id.img_close)
        ImageView imgClose;
        @BindView(R.id.item_recycler_restourant_list_txt_close)
        TextView itemRecyclerRestourantListTxtClose;
        @BindView(R.id.linear_close)
        LinearLayout linearClose;


        public ViewHolder(@NonNull View itemView) {


            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
