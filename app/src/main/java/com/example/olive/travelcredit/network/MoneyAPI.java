package com.example.olive.travelcredit.network;

import com.example.olive.travelcredit.data.MoneyResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoneyAPI {
    @GET("latest")
    Call<MoneyResult> getRates(@Query("base") String base);
}