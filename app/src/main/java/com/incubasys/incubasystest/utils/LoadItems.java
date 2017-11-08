package com.incubasys.incubasystest.utils;

import android.util.Log;

import com.incubasys.incubasystest.api.ApiService;
import com.incubasys.incubasystest.api.RetrofitClient;
import com.incubasys.incubasystest.model.CompanyData;
import com.incubasys.incubasystest.model.CompanyResponse;
import com.incubasys.incubasystest.model.User;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by _red_ on 08/11/2017.
 */

public final class LoadItems {
    public static void load(final List<CompanyData> list, int limit, int offset) {
        ApiService api = RetrofitClient.getApiService();
        Call<CompanyResponse> call = api.getCompanies(User.getToken(), limit, offset);
        System.out.println(User.getToken());

        call.enqueue(new Callback<CompanyResponse>() {
            @Override
            public void onResponse(Response<CompanyResponse> response, Retrofit retrofit) {
                Log.d("Response","Success");
                if (response.body().getSuccess()) {
                    list.addAll(response.body().getData());
                }
//                else UserNotification.showMessage(getApplicationContext(), response.body().getError().getMessage());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("Response","Failure " + t.toString());
//                UserNotification.showMessage(getApplicationContext(), "Failure");
            }
        });
    }
}
