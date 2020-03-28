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
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.model.restourantCategory.RestourantCategory;
import com.osamaelsh3rawy.otlop.data.model.restourantCategory.RestourantCategoryData;
import com.osamaelsh3rawy.otlop.helper.HelperMethods;
import com.osamaelsh3rawy.otlop.view.activity.BaseActivity;
import com.osamaelsh3rawy.otlop.view.fragment.restourant.DialogUpdateCategory;
import com.osamaelsh3rawy.otlop.view.fragment.restourant.FragmentRestourantItemMeal;

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

public class TypeFoodAdapter extends RecyclerView.Adapter<TypeFoodAdapter.ViewHolder> {

    private BaseActivity activity;
    private Context context;
    RestourantCategoryData categoryData;
    public List<RestourantCategoryData> categoryDataList = new ArrayList<>();
    TypeFoodAdapter typeFoodAdapter;
    public int positionUpdate;
    private String TAG = "TypeFoodAdapter";
    private ViewBinderHelper viewBienderHelper = new ViewBinderHelper();


    public TypeFoodAdapter(BaseActivity activity, Context context, List<RestourantCategoryData> categoryDataList) {
        this.activity = (BaseActivity) activity;
        this.context = context;
        this.categoryDataList = categoryDataList;
        viewBienderHelper.setOpenOnlyOne(true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycler_restorant_type_food, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RestourantCategoryData restourantesData = categoryDataList.get(position);

        holder.position = position;

        holder.itemRecyclerTypeName.setText(restourantesData.getName());
        Glide.with(context).load(restourantesData.getPhotoUrl()).into(holder.itemRecyclerCommentImg);

        viewBienderHelper.bind(holder.swipeRevealLayout, String.valueOf(categoryDataList.get(position).getId()));
        holder.swipeRevealLayout.computeScroll();
    }


    @Override
    public int getItemCount() {
        return categoryDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btn_del)
        ImageView btnDel;
        @BindView(R.id.btn_edt)
        ImageView btnEdt;
        @BindView(R.id.item_recycler_comment_img)
        ImageView itemRecyclerCommentImg;
        @BindView(R.id.item_recycler_type_name)
        TextView itemRecyclerTypeName;
        private ApiServies apiServies;
        private int position;
        @BindView(R.id.swipeRevealLayout)
        SwipeRevealLayout swipeRevealLayout;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.btn_del, R.id.btn_edt, R.id.item_relatev_recycler_type_food})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.btn_del:
                    //      AlertDialog.Builder builder=new AlertDialog.Builder()

                    apiServies = getClient();
                    String tokin = SharedPreferencesManger.LoadData(activity, Restourant_Api_Takin);
                    apiServies.RestourantDeleteCategory(tokin, categoryDataList.get(position).getId()).enqueue(new Callback<RestourantCategory>() {
                        @Override
                        public void onResponse(Call<RestourantCategory> call, Response<RestourantCategory> response) {
                            try {
                                if (response.body().getStatus() == 1) {
                                    notifyDataSetChanged();
                                    Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e(TAG, response.body().getMsg());

                                }
                            } catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                            }

                        }


                        @Override
                        public void onFailure(Call<RestourantCategory> call, Throwable t) {

                        }
                    });
                    break;
                case R.id.btn_edt:

                    DialogUpdateCategory dialogUpdateCategory = new DialogUpdateCategory(context, activity, true, typeFoodAdapter);
                    dialogUpdateCategory.categoryData = categoryDataList.get(position);
                    dialogUpdateCategory.positionUpdate = position;
                    dialogUpdateCategory.show();
                    notifyDataSetChanged();


                    break;

                case R.id.item_relatev_recycler_type_food:
                    FragmentRestourantItemMeal fragmentRestourantItemMeal = new FragmentRestourantItemMeal();
                    fragmentRestourantItemMeal.categoryData = categoryDataList.get(position);
                    HelperMethods.replaceFragment(activity.getSupportFragmentManager(), R.id.activity_restourant_container, fragmentRestourantItemMeal);
                    break;
            }
        }
    }
}
