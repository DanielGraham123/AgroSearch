package com.irad.cm.agri_tech.climate;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.irad.cm.agri_tech.MainActivity;
import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.RetrofitClientInstance;
import com.irad.cm.agri_tech.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodayFragment extends Fragment {

    RecyclerView mRecyclerView;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    RecyclerAdapter recyclerAdapter;
    List<WeatherForeCast.ForeCastList> todayForeCastList, today_srchForeCastList;
    Utilities utilities;
    public static String CITY = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);

        mRecyclerView = view.findViewById(R.id.wthr_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = view.findViewById(R.id.progressBar);

        todayForeCastList = new ArrayList<>();

        setTodayForecast();
        return view;
    }

    private void setTodayForecast() {
        progressDialog = ProgressDialog.show(getContext(), "Please wait", "Loading...");

        new Thread() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (CITY.equals("")) {
                            generateTodayForecast();
                        } else {
                            generateTodayForecast(CITY);
                        }
                    }
                });
                progressBar.setVisibility(View.GONE);
            }
        }.start();
    }

    private void generateTodayForecast() {
        WeatherService service = RetrofitClientInstance.getWeatherRetrofitInstance().create(WeatherService.class);
        Call<WeatherForeCast> call = service.getForeCastWeatherData(MainActivity.latitude, MainActivity.longitude, WeatherService.AppId);
        call.enqueue(new Callback<WeatherForeCast>() {
            @Override
            public void onResponse(Call<WeatherForeCast> call, Response<WeatherForeCast> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    List<WeatherForeCast.ForeCastList> weatherForeCast = response.body().getList();

                    for (int i = 0; i < weatherForeCast.size(); i++) {
                        String dt_text = weatherForeCast.get(i).getDtTxt();

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar calendar = Calendar.getInstance(); // defaults to ''now''

                        try {
                            Date date_text = sdf.parse(dt_text);
                            Date today = sdf.parse(sdf.format(calendar.getTime()));

                            if (date_text.compareTo(today) == 0) {
                                todayForeCastList.add(weatherForeCast.get(i));
                                System.out.println("FORECAST: " +todayForeCastList);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    recyclerAdapter = new RecyclerAdapter(todayForeCastList, getContext());
                    mRecyclerView.setAdapter(recyclerAdapter);
                }

            }

            @Override
            public void onFailure(Call<WeatherForeCast> call, Throwable t) {
                progressDialog.dismiss();
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Long Term parsing failed!");
                dialog.setMessage(t.getMessage());
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog mDialog = dialog.create();
                mDialog.show();
            }
        });
        new ProgressBar(getContext());

    }

    public void generateTodayForecast(String city) {
        WeatherService service = RetrofitClientInstance.getWeatherRetrofitInstance().create(WeatherService.class);
        Call<WeatherForeCast> call = service.getSearchForeCastWeatherData(city, WeatherService.AppId);
        call.enqueue(new Callback<WeatherForeCast>() {
            @Override
            public void onResponse(Call<WeatherForeCast> call, Response<WeatherForeCast> response) {
//                progressDialog.dismiss();
                if (response.code() == 200) {
                    List<WeatherForeCast.ForeCastList> weatherForeCast = response.body().getList();
                    today_srchForeCastList = new ArrayList<>();

                    for (int i = 0; i < weatherForeCast.size(); i++) {
                        String dt_text = weatherForeCast.get(i).getDtTxt();

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar calendar = Calendar.getInstance(); // defaults to ''now''

                        try {
                            Date date_text = sdf.parse(dt_text);
                            Date today = sdf.parse(sdf.format(calendar.getTime()));

                            if (date_text.compareTo(today) == 0) {
                                today_srchForeCastList.add(weatherForeCast.get(i));
                                System.out.println("SEARCH-FORECAST: " +today_srchForeCastList);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    recyclerAdapter = new RecyclerAdapter(today_srchForeCastList, getContext());
                    mRecyclerView.setAdapter(recyclerAdapter);
//                    recyclerAdapter.updateList(today_srchForeCastList);
                }

            }

            @Override
            public void onFailure(Call<WeatherForeCast> call, Throwable t) {
                progressDialog.dismiss();
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Long Term parsing failed!");
                dialog.setMessage(t.getMessage());
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog mDialog = dialog.create();
                mDialog.show();
            }
        });
//        new ProgressBar(getContext());

    }
}
