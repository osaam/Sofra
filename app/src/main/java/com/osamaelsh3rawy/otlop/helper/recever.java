package com.osamaelsh3rawy.otlop.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class recever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager con=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if(info.isConnected()){
            Toast.makeText(context,"you are houngry ?1 check new in otlop",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"we are miss you !!",Toast.LENGTH_SHORT).show();
        }
    }
}
