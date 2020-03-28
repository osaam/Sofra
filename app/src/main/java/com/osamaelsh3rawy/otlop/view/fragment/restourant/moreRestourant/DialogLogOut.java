package com.osamaelsh3rawy.otlop.view.fragment.restourant.moreRestourant;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.local.room.RoomDao;
import com.osamaelsh3rawy.otlop.data.local.room.RoomManager;
import com.osamaelsh3rawy.otlop.view.activity.ActivitySplashCycle;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Api_Takin;

public class DialogLogOut extends Dialog {
    private ApiServies apiServies;
    private boolean Cancelable;
    RoomDao roomDao;
    Activity activity;
    Dialog dialog;

    public DialogLogOut(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       try{
        apiServies = getClient();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_log_out);
        ButterKnife.bind(this);
        DialogLogOut.this.setCancelable(Cancelable);
        roomDao = RoomManager.getInstance(activity).roomDao();

        if (SharedPreferencesManger.LoadData(activity,Restourant_Api_Takin) != (null)) {

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            show();
        }
        }catch (Exception e){

       }

    }

    @OnClick({R.id.dialog_yes_btn, R.id.dialog_no_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialog_yes_btn:
                SharedPreferencesManger.clean(activity);
                roomDao.deleteAll();
                Intent intent = new Intent(activity, ActivitySplashCycle.class);
                activity.startActivity(intent);

                break;
            case R.id.dialog_no_btn:
                dismiss();
                break;
        }
    }
}
