package com.incubasys.incubasystest.api;

import com.incubasys.incubasystest.model.AuthResponse;
import com.incubasys.incubasystest.model.CompanyData;
import com.incubasys.incubasystest.model.CompanyDetailsData;
import com.incubasys.incubasystest.model.CompanyResponse;
import com.incubasys.incubasystest.model.User;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("/login")
    Call<AuthResponse> authorization(@Body User user);

    @GET("/test")
    Call<CompanyResponse> getCompanies(@Header("Authorization") String token,
                                       @Query("limit") int limit,
                                       @Query("offset") int offset);

    @GET("/test/{id}")
    Call<CompanyDetailsData> getCompanyDetails(@Header("Authorization") String token,
                                               @Path("id") int id);
}
