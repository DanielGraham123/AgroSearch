package com.irad.cm.agri_tech;

import com.irad.cm.agri_tech.climate.WeatherService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofit;
    private static Retrofit retrofit2;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(MainActivity.SITE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static Retrofit getWeatherRetrofitInstance() {
        if (retrofit2 == null) {
            retrofit2 = new retrofit2.Retrofit.Builder()
                    .baseUrl(WeatherService.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit2;
    }
}
