package com.irad.cm.agri_tech.crops;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CropsService {

    @GET("/api/vegetable/list/all")
    Call<CropList> getCropList(@Query("lang") String lang);

    @GET("/api/vegetable/list/culture%20annuelle/")
    Call<AnnualCropList> getAnnualCropList(@Query("lang") String lang);
}
