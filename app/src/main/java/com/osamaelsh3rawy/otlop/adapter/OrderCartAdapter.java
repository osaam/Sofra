package com.osamaelsh3rawy.otlop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.local.room.RoomDao;
import com.osamaelsh3rawy.otlop.data.local.room.RoomManager;
import com.osamaelsh3rawy.otlop.data.model.items.ItemsData;
import com.osamaelsh3rawy.otlop.view.activity.BaseActivity;
import com.osamaelsh3rawy.otlop.view.fragment.user.FragmentUserOrderItemCart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.osamaelsh3rawy.otlop.view.fragment.user.FragmentUserOrderItem.num;

public class OrderCartAdapter extends RecyclerView.Adapter<OrderCartAdapter.ViewHolder> {


    private BaseActivity activity;
    private Context context;
    List<ItemsData> listItem = new ArrayList<>();
    private RoomDao roomDao;
    private ItemsData itemsData;
    FragmentUserOrderItemCart fragmentUserOrderItemCart;


    public OrderCartAdapter(BaseActivity activity, Context context, List<ItemsData> listItem
            , FragmentUserOrderItemCart fragmentUserOrderItemCart) {
        this.activity = (BaseActivity) activity;
        this.context = context;
        this.listItem = listItem;
        this.fragmentUserOrderItemCart = fragmentUserOrderItemCart;
        roomDao = RoomManager.getInstance(context).roomDao();

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycler_shops_item_orders_confirm, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        itemsData = listItem.get(position);
        holder.position = position;

        holder.itemRecyclerShopsItemOrderConfirmTitle.setText(itemsData.getName());
        holder.itemRecyclerShopsItemOrderConfirmPrice.setText(String.valueOf(itemsData.getPrice()));
        Glide.with(context).load(itemsData.getPhotoUrl()).into(holder.itemRecyclerShopsItemOrderConfirmImg);
        holder.itemRecyclerShopsItemOrderConfirmTxtNum.setText(String.valueOf(itemsData.getCount()));

        holder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num = itemsData.getCount();
                num += 1;
                itemsData.setCount(num);
                roomDao.Update(itemsData);
                fragmentUserOrderItemCart.setTotal();
                holder.itemRecyclerShopsItemOrderConfirmTxtNum.setText(String.valueOf(num));
            }
        });

        holder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Executors.newSingleThreadExecutor().execute(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                });
                if (num > 1){
                    num = itemsData.getCount();
                num -= 1;
                itemsData.setCount(num);
                roomDao.Update(itemsData);
                fragmentUserOrderItemCart.setTotal();
                holder.itemRecyclerShopsItemOrderConfirmTxtNum.setText(String.valueOf(num));
            }}
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomDao.Delete(itemsData);
                listItem.remove(position);
                fragmentUserOrderItemCart.setTotal();
                notifyDataSetChanged();
            }
        });

    }


    @Override
    public int getItemCount() {
        return listItem.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_recycler_shops_item_order_confirm_img)
        ImageView itemRecyclerShopsItemOrderConfirmImg;
        @BindView(R.id.item_recycler_shops_item_order_confirm_title)
        TextView itemRecyclerShopsItemOrderConfirmTitle;
        @BindView(R.id.item_recycler_shops_item_order_confirm_price)
        TextView itemRecyclerShopsItemOrderConfirmPrice;
        @BindView(R.id.item_recycler_shops_item_order_confirm_txt_num)
        TextView itemRecyclerShopsItemOrderConfirmTxtNum;
        public int position;
        @BindView(R.id.delete)
        Button delete;
        @BindView(R.id.increase)
        Button increase;
        @BindView(R.id.decrease)
        Button decrease;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }


}

