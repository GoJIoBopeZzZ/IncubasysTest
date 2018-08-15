package com.incubasys.incubasystest.api;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by _red_ on 08/11/2017.
 */

public class RetrofitClient {
    private static final String BASE_URL = "http://95.213.199.15:9000";

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }
}
