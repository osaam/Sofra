package com.osamaelsh3rawy.otlop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.model.userNotifications.NotificationData;
import com.osamaelsh3rawy.otlop.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private Context context;
    private BaseActivity activity;
    private List<NotificationData> notificationList = new ArrayList<>();


    public NotificationAdapter(Context context, BaseActivity activity, List<NotificationData> notificationList) {
        this.context = context;
        this.activity = (BaseActivity) activity;
        this.notificationList = notificationList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycler_notify, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationData notificationData = notificationList.get(position);

        holder.notifyItemTxtAlarm.setText(notificationData.getTitle());
        holder.notifyItemTxtDate.setText(notificationData.getCreatedAt());
        holder.notifyItemImgClock.setImageResource(R.drawable.ic_clock_black);
        holder.notifyItemImgIcon.setImageResource(R.drawable.ic_notifications_active_black);

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.notify_item_img_icon)
        ImageView notifyItemImgIcon;
        @BindView(R.id.notify_item_txt_alarm)
        TextView notifyItemTxtAlarm;
        @BindView(R.id.notify_item_img_clock)
        ImageView notifyItemImgClock;
        @BindView(R.id.notify_item_txt_date)
        TextView notifyItemTxtDate;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
