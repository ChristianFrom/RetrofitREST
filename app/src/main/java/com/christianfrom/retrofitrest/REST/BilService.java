package com.christianfrom.retrofitrest.REST;

import com.christianfrom.retrofitrest.Model.Bil;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface BilService {
    @GET("vehicles/{nummerplade}")
    Call<Bil> getBil(@Path("nummerplade") String nummerplade, @Header("Authorization") String apiToken);

}

