package com.osamaelsh3rawy.otlop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.model.items.ItemsData;
import com.osamaelsh3rawy.otlop.view.activity.BaseActivity;
import com.osamaelsh3rawy.otlop.view.fragment.user.FragmentUserOrderItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.osamaelsh3rawy.otlop.helper.HelperMethods.replaceFragment;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private BaseActivity activity;
    private Context context;
    List<ItemsData> listItem = new ArrayList<>();

    public MenuAdapter(BaseActivity activity, Context context, List<ItemsData> listItem) {
        this.activity = (BaseActivity) activity;
        this.context = context;
        this.listItem = listItem;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycler_shops_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemsData itemsData = listItem.get(position);

        holder.itemsData = itemsData;

        holder.itemRecyclerShopsItemTxt1Title.setText(itemsData.getName());
        holder.itemRecyclerShopsItemTxt2Detaiels.setText(itemsData.getDescription());
        holder.itemRecyclerShopsItemTxt3PriceTxt.setText(String.valueOf(itemsData.getPrice()));

        Glide.with(context).load(itemsData.getPhotoUrl()).into(holder.itemRecyclerShopsItemImg);

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_recycler_shops_item_img)
        ImageView itemRecyclerShopsItemImg;
        @BindView(R.id.item_recycler_shops_item_txt1_title)
        TextView itemRecyclerShopsItemTxt1Title;
        @BindView(R.id.item_recycler_shops_item_txt2_detaiels)
        TextView itemRecyclerShopsItemTxt2Detaiels;
        @BindView(R.id.item_recycler_shops_item_txt3_price_txt)
        TextView itemRecyclerShopsItemTxt3PriceTxt;
        public ItemsData itemsData;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnClick(R.id.item_recycler_shops_item_relateve)
        public void onViewClicked() {

            FragmentUserOrderItem fragmentUserOrderItem = new FragmentUserOrderItem();
            fragmentUserOrderItem.itemsData = itemsData;
            replaceFragment(activity.getSupportFragmentManager(), R.id.activity_user_container, fragmentUserOrderItem);


        }
    }
}
