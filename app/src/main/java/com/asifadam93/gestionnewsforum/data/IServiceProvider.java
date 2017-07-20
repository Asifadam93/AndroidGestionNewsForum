package com.asifadam93.gestionnewsforum.data;

import android.content.Context;
import android.util.Log;

import com.asifadam93.gestionnewsforum.data.local.RealmService;
import com.asifadam93.gestionnewsforum.data.network.RetrofitService;
import com.asifadam93.gestionnewsforum.util.Const;

/**
 * Created by fabibi on 18/07/2017.
 */

public class IServiceProvider {
    public static IService  getService(Context context) {
        if (Const.isInternetAvailable(context)) {
            Log.i("ServiceProvider", "RetrofitService");
            return RetrofitService.getInstance();
        } else {
            Log.i("ServiceProvider", "RealmService");
            return RealmService.getInstance();
        }

    }
}
