package com.irad.cm.agri_tech.plantAndDisease;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DiseaseService {

    @GET("/api/vegetable/disease/")
    Call<Diseases> getDiseaseList(@Query("lang") String lang);

}
