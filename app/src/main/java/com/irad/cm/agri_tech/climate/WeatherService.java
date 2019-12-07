package com.irad.cm.agri_tech.climate;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    String BaseUrl = "http://api.openweathermap.org/";
    String AppId = "6809f8076fac81158c560ec3cd7ed5d6";

    @GET("data/2.5/weather?units=metric")
//    Call<WeatherResponse> getCurrentWeatherData(@Query("q") String city, @Query("APPID") String app_id);
    Call<WeatherResponse> getCurrentWeatherData(@Query("lat") double lat, @Query("lon") double lon, @Query("APPID") String app_id);

    @GET("data/2.5/weather?units=metric")
    Call<WeatherResponse> getSearchWeatherData(@Query("q") String city, @Query("APPID") String app_id);

    @GET("data/2.5/forecast?units=metric")
    Call<WeatherForeCast> getSearchForeCastWeatherData(@Query("q") String city, @Query("APPID") String app_id);

    @GET("data/2.5/forecast?units=metric")
    Call<WeatherForeCast> getForeCastWeatherData(@Query("lat") double lat, @Query("lon") double lon, @Query("APPID") String app_id);

}
