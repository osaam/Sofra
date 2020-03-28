package com.osamaelsh3rawy.otlop.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.Constanse;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.model.restourantItemMeal.RestourantItemMeal;
import com.osamaelsh3rawy.otlop.data.model.restourantItemMeal.RestourantItemMealData;
import com.osamaelsh3rawy.otlop.helper.HelperMethods;
import com.osamaelsh3rawy.otlop.view.activity.BaseActivity;
import com.osamaelsh3rawy.otlop.view.fragment.restourant.FragmentRestourantAddMeal;
import com.osamaelsh3rawy.otlop.view.fragment.restourant.FragmentRestourantUpdateMeal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;

public class ItemMealAdapter extends RecyclerView.Adapter<ItemMealAdapter.ViewHolder> {

    ItemMealAdapter itemMealAdapter;
    private Context context;
    private BaseActivity activity;
    private List<RestourantItemMealData> listMeal = new ArrayList<>();
     private ApiServies apiServies;
     private String TAG="ItemMealAdapter";


    public ItemMealAdapter(Context context, BaseActivity activity, List<RestourantItemMealData> listMeal) {
        this.context = context;
        this.activity = activity;
        this.listMeal = listMeal;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycler_restorant_item_meal, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RestourantItemMealData MealData = listMeal.get(position);
        holder.itemRecyclerMealName.setText(MealData.getName());
        holder.itemRecyclerMealDescription.setText(MealData.getDescription());
        holder.itemRecyclerMealPrice.setText(MealData.getPrice());
        Glide.with(context).load(MealData.getPhotoUrl()).into(holder.itemRecyclerMealImg);


    }

    @Override
    public int getItemCount() {
        return listMeal.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_recycler_meal_img)
        ImageView itemRecyclerMealImg;
        @BindView(R.id.item_recycler_meal_name)
        TextView itemRecyclerMealName;
        @BindView(R.id.item_recycler_meal_description)
        TextView itemRecyclerMealDescription;
        @BindView(R.id.item_recycler_meal_price)
        TextView itemRecyclerMealPrice;
        private int position;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.btn_del, R.id.btn_edt})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.btn_del:
                    //api meal del
                    apiServies=getClient();
                    apiServies.RestourantDeleteItem(listMeal.get(position).getId(), SharedPreferencesManger.LoadData(activity, Constanse.Restourant_Api_Takin)).enqueue(new Callback<RestourantItemMeal>() {
                        @Override
                        public void onResponse(Call<RestourantItemMeal> call, Response<RestourantItemMeal> response) {
                            try{
                                if(response.body().getStatus()==1){
                                    notifyDataSetChanged();
                                    Toast.makeText(activity,response.body().getMsg(),Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Log.e(TAG,response.body().getMsg());
                                }
                            }catch (Exception e){
                                Log.e(TAG,e.getMessage());
                            }
                        }
                        @Override
                        public void onFailure(Call<RestourantItemMeal> call, Throwable t) {

                        }
                    });

                    break;
                case R.id.btn_edt:
                    FragmentRestourantUpdateMeal fragmentRestourantUpdateMeal = new FragmentRestourantUpdateMeal( itemMealAdapter);
                    //position
                    fragmentRestourantUpdateMeal.restourantItemMealData = listMeal.get(position);
                    HelperMethods.replaceFragment(activity.getSupportFragmentManager(), R.id.activity_restourant_container, fragmentRestourantUpdateMeal);
                    break;
            }
        }

    }
}
