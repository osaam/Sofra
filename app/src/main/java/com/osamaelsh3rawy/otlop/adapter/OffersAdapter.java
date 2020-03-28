package com.osamaelsh3rawy.otlop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.model.userOffers.UserOffersData;
import com.osamaelsh3rawy.otlop.view.activity.BaseActivity;
import com.osamaelsh3rawy.otlop.view.fragment.user.more.FragmentUserOffersDetaiels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.osamaelsh3rawy.otlop.helper.HelperMethods.replaceFragment;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {


    private BaseActivity activity;
    private Context context;
   private List<UserOffersData> listItem = new ArrayList<>();

    public OffersAdapter(BaseActivity activity, Context context, List<UserOffersData> listItem) {
        this.activity = (BaseActivity) activity;
        this.context = context;
        this.listItem = listItem;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycler_offers, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserOffersData userOffersData = listItem.get(position);
        holder.itemRecyclerOffersTxtOffers.setText(userOffersData.getDescription());
        Glide.with(context).load(userOffersData.getPhotoUrl()).into(holder.itemRecyclerOffersImg);


    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_recycler_offers_txt_offers)
        TextView itemRecyclerOffersTxtOffers;
        @BindView(R.id.item_recycler_offers_btn)
        Button itemRecyclerOffersBtn;
        @BindView(R.id.item_recycler_offers_img)
        CircleImageView itemRecyclerOffersImg;
        private int position;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.item_recycler_offers_btn)
        public void onViewClicked() {

            FragmentUserOffersDetaiels fragmentUserOffersDetaiels = new FragmentUserOffersDetaiels();
            fragmentUserOffersDetaiels.offerdata = listItem.get(position);
            replaceFragment(activity.getSupportFragmentManager(), R.id.activity_user_container, fragmentUserOffersDetaiels);
        }
    }
}
