package com.osamaelsh3rawy.otlop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.model.userRestourantReview.UserRestaurantReviewData;
import com.osamaelsh3rawy.otlop.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {


    private Context context;
    private BaseActivity activity;
    private List<UserRestaurantReviewData> listOfReview = new ArrayList<>();


    public ReviewAdapter(Context context, FragmentActivity activity, List<UserRestaurantReviewData> listOfUserData) {

        this.context = context;
        this.activity = (BaseActivity) activity;
        this.listOfReview = listOfUserData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycler_shop_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserRestaurantReviewData userRestaurantReviewData=listOfReview.get(position);

        holder.itemRecyclerCommentName.setText(userRestaurantReviewData.getClient().getName());
        holder.itemRecyclerCommentComment.setText(userRestaurantReviewData.getComment());

        if(userRestaurantReviewData.getRate()=="1"){
            holder.itemRecyclerCommentImg.setImageResource(R.drawable.ic_face_angry);
        }
       else if(userRestaurantReviewData.getRate()=="2"){
            holder.itemRecyclerCommentImg.setImageResource(R.drawable.ic_face_sad);
        }
        else if(userRestaurantReviewData.getRate()=="3"){
            holder.itemRecyclerCommentImg.setImageResource(R.drawable.ic_face_normal);
        }
        else if(userRestaurantReviewData.getRate()=="4"){
            holder.itemRecyclerCommentImg.setImageResource(R.drawable.ic_face_happy);
        }

        else if(userRestaurantReviewData.getRate()=="5"){
            holder.itemRecyclerCommentImg.setImageResource(R.drawable.ic_face_love);
        }




    }

    @Override
    public int getItemCount() {
        return listOfReview.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.item_recycler_comment_name)
        TextView itemRecyclerCommentName;
        @BindView(R.id.item_recycler_comment_comment)
        TextView itemRecyclerCommentComment;
        @BindView(R.id.item_recycler_comment_img)
        ImageView itemRecyclerCommentImg;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
