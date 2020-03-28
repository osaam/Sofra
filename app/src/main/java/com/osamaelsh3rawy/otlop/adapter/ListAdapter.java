package com.osamaelsh3rawy.otlop.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.model.items.ItemsData;
import com.osamaelsh3rawy.otlop.data.model.userMyOrder.MyOrder;
import com.osamaelsh3rawy.otlop.data.model.userMyOrder.MyOrderData;
import com.osamaelsh3rawy.otlop.helper.HelperMethods;
import com.osamaelsh3rawy.otlop.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.User_Api_Takin;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {


    private Context context;
    private BaseActivity activity;
    private List<MyOrderData> listOfUserData = new ArrayList<>();
    private String status;
    ApiServies apiServies = getClient();

    public ListAdapter(Context context, BaseActivity activity, List<MyOrderData> listOfUserData, String status) {

        this.context = context;
        this.activity = (BaseActivity) activity;
        this.listOfUserData = listOfUserData;
        this.status = status;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycler_user_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyOrderData myOrderData = listOfUserData.get(position);

        holder.itemRecyclerShopsTxtShopName.setText(myOrderData.getRestaurant().getName());
        holder.itemRecyclerShopsTxtDlevaryAddress.setText(myOrderData.getAddress());
        holder.itemRecyclerShopsTxtTotal.setText(myOrderData.getTotal());
        holder.itemRecyclerShopsTxtShopNum.setText(String.valueOf(myOrderData.getId()));
        Glide.with(context).load(myOrderData.getRestaurant().getPhotoUrl()).into(holder.itemRecyclerShopsImg);

        if (status.equals("pending")) {
            holder.itemRecyclerListBtnName.setText("confirm order");
            holder.itemRecyclerListIcon.setImageResource(R.drawable.ic_check_white);
            holder.itemRecyclerListBtnLinear.setBackgroundResource(R.color.green);
            

            holder.itemRecyclerListBtnLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String takin = SharedPreferencesManger.LoadData(activity, User_Api_Takin);
                    HelperMethods.showProgressDialog(activity,"please wait");
                    apiServies.UserConfirmOrder(myOrderData.getId(), takin).enqueue(new Callback<MyOrder>() {
                        @Override
                        public void onResponse(Call<MyOrder> call, Response<MyOrder> response) {
                           HelperMethods.dismissProgressDialog();
                            if (response.body().getStatus() == 1) {
                                listOfUserData.remove(position);
                                notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<MyOrder> call, Throwable t) {

                        }
                    });
                }
            });


        } else if (status.equals("current")) {
            holder.itemRecyclerListBtnName.setText("delete order");
            holder.itemRecyclerListIcon.setImageResource(R.drawable.ic_delete_white);
            holder.itemRecyclerListBtnLinear.setBackgroundResource(R.color.red);
            

            holder.itemRecyclerListBtnLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String takin = SharedPreferencesManger.LoadData(activity, User_Api_Takin);
                    HelperMethods.showProgressDialog(activity,"please wait");
                    apiServies.UserDeclineOrder(myOrderData.getId(), takin).enqueue(new Callback<MyOrder>() {
                        @Override
                        public void onResponse(Call<MyOrder> call, Response<MyOrder> response) {
                            HelperMethods.dismissProgressDialog();
                            if (response.body().getStatus() == 1) {
                                listOfUserData.remove(position);
                                notifyDataSetChanged();
                            }
                        }
                        @Override
                        public void onFailure(Call<MyOrder> call, Throwable t) {
                        }
                    });
                }
            });


        } else if (status.equals("completed")) {
            holder.itemRecyclerListBtnName.setText("confirm reciving");
            holder.itemRecyclerListIcon.setImageResource(R.drawable.ic_check_white);
            holder.itemRecyclerListBtnLinear.setBackgroundResource(R.color.sofra_color);
        }

    }

    @Override
    public int getItemCount() {
        return listOfUserData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_recycler_shops_txt_shop_name)
        TextView itemRecyclerShopsTxtShopName;
        @BindView(R.id.item_recycler_shops_txt_shop_num)
        TextView itemRecyclerShopsTxtShopNum;
        @BindView(R.id.item_recycler_shops_txt_total)
        TextView itemRecyclerShopsTxtTotal;
        @BindView(R.id.item_recycler_shops_txt_dlevary_address)
        TextView itemRecyclerShopsTxtDlevaryAddress;
        @BindView(R.id.item_recycler_shops_img)
        ImageView itemRecyclerShopsImg;
        @BindView(R.id.item_recycler_list_btn_name)
        TextView itemRecyclerListBtnName;
        @BindView(R.id.item_recycler_list_icon)
        ImageView itemRecyclerListIcon;
        @BindView(R.id.item_recycler_list_btn_linear)
        LinearLayout itemRecyclerListBtnLinear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
