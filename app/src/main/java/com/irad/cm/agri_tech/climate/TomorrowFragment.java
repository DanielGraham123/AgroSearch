package com.irad.cm.agri_tech.climate;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.irad.cm.agri_tech.MainActivity;
import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.RetrofitClientInstance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TomorrowFragment extends Fragment {

    RecyclerView mRecyclerView;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    RecyclerAdapter recyclerAdapter;
    List<WeatherForeCast.ForeCastList> tomorrowForeCastList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tomorrow, container, false);

        mRecyclerView = view.findViewById(R.id.wthr_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = view.findViewById(R.id.progressBar);

        tomorrowForeCastList = new ArrayList<>();

        setTomorrowForecast();
        return view;
    }

    private void setTomorrowForecast() {
        progressDialog = ProgressDialog.show(getContext(), "Please wait", "Loading...");

        new Thread() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        generateTomorrowForecast();
                    }
                });
                progressBar.setVisibility(View.GONE);
            }
        }.start();
    }

    private void generateTomorrowForecast() {
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
                        calendar.add(Calendar.DAY_OF_YEAR, 1);
                        try {
                            Date date_text = sdf.parse(dt_text);
                            Date tomorrow = sdf.parse(sdf.format(calendar.getTime()));

                            if (date_text.compareTo(tomorrow) == 0) {
                                tomorrowForeCastList.add(weatherForeCast.get(i));
                                System.out.println("FORECAST: " +tomorrowForeCastList);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    recyclerAdapter = new RecyclerAdapter(tomorrowForeCastList, getContext());
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

}
