package com.irad.cm.agri_tech.cropDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CropDetailService {

    @GET("/api/vegetable/{slug}")
    Call<CropDetailList> getCropDetailList(@Path("slug") String slug, @Query("lang") String lang);

//    @GET("/api/vegetable/seed/{seedType}/")
//    Call<SeedDetail> getSeedDetail(@Path("seedType") String seedType, @Query("region") String region);
}
