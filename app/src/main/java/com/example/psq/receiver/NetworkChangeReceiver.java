package com.example.psq.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.psq.api.NetworkChangeApi;
import com.example.psq.utils.NetWortUtil;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            if (NetWortUtil.isConnected(context)) {
                if(networkChangeApi!=null){
                    networkChangeApi.onConnect();
                }
            }else {
                if(networkChangeApi!=null){
                    networkChangeApi.onDisconnection();
                }
            }
        }
    }

    private NetworkChangeApi networkChangeApi;

    public void setNetworkChangeApi(NetworkChangeApi networkChangeApi) {
        this.networkChangeApi = networkChangeApi;
    }
}
