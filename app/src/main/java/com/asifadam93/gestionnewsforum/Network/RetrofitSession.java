package com.asifadam93.gestionnewsforum.Network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Asifadam93 on 10/07/2017.
 */

public class RetrofitSession {

    private static Retrofit retrofit;
    private final static String ENDPOINT = "https://esgi-2017.herokuapp.com/";

    public static Retrofit getInstance() {

        if (retrofit == null) {

            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY));

            retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient.build())
                    .build();
        }

        return retrofit;
    }

}
