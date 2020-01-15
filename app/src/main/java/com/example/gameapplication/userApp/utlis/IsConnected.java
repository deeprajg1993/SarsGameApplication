package com.example.gameapplication.userApp.utlis;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Chinki Sai on 6/22/2017.
 */
public class IsConnected
{

    public static Boolean isInternet_connected(Context context)
    {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo( ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo( ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else {
            connected = false;
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
        return connected;
    }
    static boolean exists = false;


}
