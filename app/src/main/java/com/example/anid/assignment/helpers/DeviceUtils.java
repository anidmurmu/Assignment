package com.example.anid.assignment.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class DeviceUtils {

    public static boolean hasInternetAccess(Context context) {

        if (!isNetworkAvailable(context)) {
            return false;
        }
        return true;
    }

    private static boolean isNetworkAvailable( Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
