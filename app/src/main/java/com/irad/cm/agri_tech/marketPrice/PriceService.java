package com.irad.cm.agri_tech.marketPrice;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PriceService {

    @GET("/api/vegetable/prices")
    Call<Prices> getPriceList(@Query("lang") String lang);
}
