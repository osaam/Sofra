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
import com.osamaelsh3rawy.otlop.data.model.userCtegories.CategoriesData;
import com.osamaelsh3rawy.otlop.view.activity.BaseActivity;
import com.osamaelsh3rawy.otlop.view.fragment.user.FragmentUserShopMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private Context context;
    private BaseActivity activity;
    List<CategoriesData> categoriesList = new ArrayList<>();
   public FragmentUserShopMenu fragmentUserShopMenu;
    int Restourant_id;

    public CategoriesAdapter(Context context, List<CategoriesData> categoriesList, FragmentUserShopMenu fragmentUserShopMenu, int Restourant_id) {
        this.context = context;
        this.activity = activity;
        this.fragmentUserShopMenu=fragmentUserShopMenu;
        this.Restourant_id=Restourant_id;
        if (categoriesList != null) {
            this.categoriesList = categoriesList;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycler_shops_shortcuts, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoriesData categoriesData = categoriesList.get(position);

        holder.itemRecyclerShopsItemTxtShortcuts.setText(categoriesData.getName());
        Glide.with(context).load(categoriesData.getPhotoUrl()).into(holder.itemRecyclerShopsItemImgShortcuts);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentUserShopMenu.getAllMenu(Restourant_id,categoriesData.getId(),1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_recycler_shops_item_img_shortcuts)
        ImageView itemRecyclerShopsItemImgShortcuts;
        @BindView(R.id.item_recycler_shops_item_txt_shortcuts)
        TextView itemRecyclerShopsItemTxtShortcuts;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
