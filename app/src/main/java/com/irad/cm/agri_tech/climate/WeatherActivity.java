package com.irad.cm.agri_tech.climate;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.irad.cm.agri_tech.fragments.LocateMeFragment;
import com.irad.cm.agri_tech.MainActivity;
import com.irad.cm.agri_tech.OptionsMenuActivity;
import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.SharedPreferenceConfig;
import com.irad.cm.agri_tech.fragments.SignupFragment;
import com.irad.cm.agri_tech.Utilities;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends OptionsMenuActivity {

    MainActivity mainActivity;
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    TextView dateView, tempView, windView, pressureView, humidityView, descView;
    ImageView cloudView;
    ProgressDialog progressDialog;

    SharedPreferenceConfig sharedPreferenceConfig;

//    final String[] titleViews = {getResources().getString(R.string.today), getResources().getString(R.string.tomorrow), getResources().getString(R.string.other)};

    private List<WeatherForeCast.ForeCastList> longTermTodayWeather = new ArrayList<>();
    private List<WeatherForeCast.ForeCastList> longTermTomorrowWeather = new ArrayList<>();
    private List<WeatherForeCast.ForeCastList> longTermOtherWeather = new ArrayList<>();


    public static int weatherListlength;
    private String city;

    Date date;
    SimpleDateFormat sdf;

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferenceConfig = new SharedPreferenceConfig(this);

        setContentView(R.layout.activity_weather);

        toolbar = findViewById(R.id.weather_toolbar);
        toolbar.setTitle(MainActivity.locationInfo);
        setSupportActionBar(toolbar);


        viewPager = findViewById(R.id.pager);

        tabLayout = findViewById(R.id.tabs);

        mainActivity = new MainActivity();

        dateView = findViewById(R.id.dateView);
        tempView = findViewById(R.id.tempView);
        windView = findViewById(R.id.windView);
        humidityView = findViewById(R.id.humidityView);
        pressureView = findViewById(R.id.pressureView);
        descView = findViewById(R.id.descView);
        cloudView = findViewById(R.id.cloudView);

        Utilities utilities = new Utilities(this, this);
        sdf = new SimpleDateFormat("E, dd.MM.yy");

        int comma;

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setBackgroundColor(mainActivity.fetchColor(R.color.colorPrimary, this));
        bottomNav.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
        bottomNav.setItemIconTintList(ColorStateList.valueOf(Color.WHITE));
        bottomNav.setItemTextAppearanceActive(getColor(R.color.activeAppearance));
        bottomNav.setItemTextAppearanceInactive(Color.WHITE);

        bottomNav.setSelectedItemId(R.id.action_home);

        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (MainActivity.NETWORK_STATUS) {
//            comma = MainActivity.locationInfo.indexOf(",");
//            city = MainActivity.locationInfo.substring(0, comma);

//            refreshWeather();
            new TodayWeather().execute();
            new LongTermWeather(city).execute();
            preloadWeather();

        }

    }

    public void preloadWeather() {
        new LastWeatherData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//        refreshWeather();
//        new TodayWeather().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//        new LongTermWeather(city).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void refreshWeather() {
        updateTodayWeather();
        updateLongTermUI();
    }

    public void searchCityData(String city) {
        new SearchCityWeather().execute(city);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.action_home:
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                            break;
                        case R.id.action_signup:
                            selectedFragment = new SignupFragment();
                            break;
                        case R.id.action_location:
                            selectedFragment = new LocateMeFragment();
                            break;
                    }
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };


    private class TodayWeather extends AsyncTask<WeatherResponse, Void, String> {

        WeatherResponse weatherResponse;

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(final WeatherResponse... weatherResponses) {

            if (MainActivity.NETWORK_STATUS) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(WeatherService.BaseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                WeatherService service = retrofit.create(WeatherService.class);
                Call<WeatherResponse> call = service.getCurrentWeatherData(MainActivity.latitude, MainActivity.longitude, WeatherService.AppId);

                call.enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if (response.code() == 200) {
                            weatherResponse = response.body();
//                        assert weatherResponse != null;

                            date = new Date((long) weatherResponse.getDt() * 1000);

                            String location = weatherResponse.getName() + ", " + weatherResponse.getSys().getCountry();
                            String temp = weatherResponse.getMain().getTemp() + " °C";
                            String desc = StringUtils.capitalize(weatherResponse.getWeather().get(0).getDescription());
                            String wind = getResources().getString(R.string.wind) + " " + weatherResponse.getWind().getSpeed() + " m/s";
                            String pressure = getResources().getString(R.string.pressure) + " " + weatherResponse.getMain().getPressure() + " hPa";
                            String humidity = getResources().getString(R.string.humidity) + " " + weatherResponse.getMain().getHumidity() + " %";
                            String dateStr = StringUtils.capitalize(sdf.format(date));

                            toolbar.setTitle(location);
                            sharedPreferenceConfig.setLastLocation(location);

                            tempView.setText(temp);
                            sharedPreferenceConfig.setLastTemperature(temp);

                            descView.setText(desc);
                            sharedPreferenceConfig.setLastDescription(desc);

                            windView.setText(wind);
                            sharedPreferenceConfig.setLastWind(wind);

                            pressureView.setText(pressure);
                            sharedPreferenceConfig.setLastPressure(pressure);

                            humidityView.setText(humidity);
                            sharedPreferenceConfig.setLastHumidity(humidity);

                            dateView.setText(dateStr);
                            sharedPreferenceConfig.setLastUpdateTime(dateStr);

                            String iconUrl = "http://openweathermap.org/img/w/" + weatherResponse.getWeather().get(0).getIcon() + ".png";
                            Picasso.get().load(iconUrl)
                                    .resize(300, 340)
                                    .into(cloudView);

                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        Toast.makeText(WeatherActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                return "OK";

            } else {

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) dateView.getLayoutParams();

                lp.setMargins(40, 20, 0, 10);
                dateView.setLayoutParams(lp);
                return "BAD";
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(WeatherActivity.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            if (string.equals("OK")) {
                Toast.makeText(WeatherActivity.this, "City found!", Toast.LENGTH_LONG).show();
                progressDialog.hide();
            } else {
                progressDialog.hide();
            }
        }
    }

    private class LastWeatherData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            cloudView.setImageResource(R.drawable.cloud_test);
            toolbar.setTitle(sharedPreferenceConfig.getLastLocation());
            tempView.setText(sharedPreferenceConfig.getLastTemperature());
            descView.setText(sharedPreferenceConfig.getLastDescription());
            windView.setText(sharedPreferenceConfig.getLastWind());
            pressureView.setText(sharedPreferenceConfig.getLastPressure());
            humidityView.setText(sharedPreferenceConfig.getLastHumidity());
            dateView.setText(sharedPreferenceConfig.getLastUpdateTime());

            return "OK";
        }
    }

    private class SearchCityWeather extends AsyncTask<String, Void, Void> {

        WeatherResponse weatherResponse;

        @Override
        protected Void doInBackground(String... strings) {

            if (strings[0] != "" && strings[0] != null) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(WeatherService.BaseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                String city = StringUtils.capitalize(strings[0]);

                WeatherService service = retrofit.create(WeatherService.class);
                Call<WeatherResponse> call = service.getSearchWeatherData(city, WeatherService.AppId);

                call.enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if (response.code() == 200) {
                            weatherResponse = response.body();
//                        assert weatherResponse != null;
                            date = new Date((long) weatherResponse.getDt() * 1000);

                            toolbar.setTitle(weatherResponse.getName() + ", " + weatherResponse.getSys().getCountry());
                            System.out.println("Country: " +weatherResponse.getName() + ", " + weatherResponse.getSys().getCountry());
                            tempView.setText(String.format("%.1f",
                                    weatherResponse.getMain().getTemp()) + " °C");
                            descView.setText(StringUtils.capitalize(weatherResponse.getWeather().get(0).getDescription()));
                            windView.setText(getResources().getString(R.string.wind) + " " +
                                    String.format("%.1f", weatherResponse.getWind().getSpeed()) + " m/s");
                            pressureView.setText(getResources().getString(R.string.pressure) + " " +
                                    String.format("%.1f", weatherResponse.getMain().getPressure()) + " hPa");
                            humidityView.setText(getResources().getString(R.string.humidity) + " " +
                                    String.format("%.1f", weatherResponse.getMain().getHumidity()) + " %");
                            dateView.setText(StringUtils.capitalize(sdf.format(date)));

                            String iconUrl = "http://openweathermap.org/img/w/" + weatherResponse.getWeather().get(0).getIcon() + ".png";
                            Picasso.get().load(iconUrl)
                                    .resize(300, 340)
                                    .into(cloudView);

                        }

                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
//                        Toast.makeText(WeatherActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
//                        Snackbar.make(findViewById(R.id.activity_weather), t.getMessage(), Snackbar.LENGTH_LONG).show();
                        Log.i("ERROR:", t.getMessage());

                        AlertDialog.Builder dialog = new AlertDialog.Builder(WeatherActivity.this);
                        dialog.setTitle("Problem!");
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

//                new LongTermWeather(city).execute();

            } else {
                Snackbar.make(findViewById(R.id.activity_weather), "Incorrect City!", Snackbar.LENGTH_LONG).show();
            }
            return null;
        }
    }

    class LongTermWeather extends AsyncTask<String, Void, String> {

        private String city;
        public List<WeatherForeCast.ForeCastList> foreCastList;
        SimpleDateFormat sdf;

        LongTermWeather(String city) {
            this.city = city;

            this.sdf = new SimpleDateFormat("E dd.MM.yy - HH:mm");
        }


        public void getTodayForeCast() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(WeatherService.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            WeatherService service = retrofit.create(WeatherService.class);
            Call<WeatherForeCast> call = service.getForeCastWeatherData(MainActivity.latitude, MainActivity.longitude, WeatherService.AppId);

            call.enqueue(new Callback<WeatherForeCast>() {
                @Override
                public void onResponse(Call<WeatherForeCast> call, Response<WeatherForeCast> response) {
                    if (response.code() == 200) {
                        List<WeatherForeCast.ForeCastList> weatherForeCast = response.body().getList();
                        foreCastList = weatherForeCast;
                        weatherListlength = foreCastList.size();

                        for (int i = 0; i < weatherListlength; i ++) {

                            final String dateMsString = foreCastList.get(i).getDt() + "000";
                            Calendar cal = Calendar.getInstance();
                            cal.setTimeInMillis(Long.parseLong(dateMsString));
//                        weather.setIcon(formatting.setWeatherIcon(Integer.parseInt(idString), cal.get(Calendar.HOUR_OF_DAY)));

                            Calendar today = Calendar.getInstance();
                            today.set(Calendar.HOUR_OF_DAY, 0);
                            today.set(Calendar.MINUTE, 0);
                            today.set(Calendar.SECOND, 0);
                            today.set(Calendar.MILLISECOND, 0);

                            Calendar tomorrow = (Calendar) today.clone();
                            tomorrow.add(Calendar.DAY_OF_YEAR, 1);

                            Calendar later = (Calendar) today.clone();
                            later.add(Calendar.DAY_OF_YEAR, 2);

                            if (cal.before(tomorrow)) {
//                            longTermTodayWeather.add(foreCastList.get(i));
                                setTodayForecastList(foreCastList.get(i));
                            } else if (cal.before(later)) {
//                            longTermTomorrowWeather.add(foreCastList.get(i));
                                setTomorrowForecastList(foreCastList.get(i));
                            } else {
//                            longTermOtherWeather.add(foreCastList.get(i));
                                setOtherForecastList(foreCastList.get(i));
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<WeatherForeCast> call, Throwable t) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(WeatherActivity.this);
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

        }

        @Override
        protected String doInBackground(String... strings) {
            getTodayForeCast();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
        }
    }

    public void updateLongTermUI() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle bundleToday = new Bundle();
        bundleToday.putInt("day", 0);
        PagerFragment recyclerViewFragmentToday = new PagerFragment();
        recyclerViewFragmentToday.setArguments(bundleToday);
        adapter.addFragment(recyclerViewFragmentToday, getString(R.string.today));

        Bundle bundleTomorrow = new Bundle();
        bundleTomorrow.putInt("day", 1);
        PagerFragment recyclerViewFragmentTomorrow = new PagerFragment();
        recyclerViewFragmentTomorrow.setArguments(bundleTomorrow);
        adapter.addFragment(recyclerViewFragmentTomorrow, getString(R.string.tomorrow));

        Bundle bundle = new Bundle();
        bundle.putInt("day", 2);
        PagerFragment recyclerViewFragment = new PagerFragment();
        recyclerViewFragment.setArguments(bundle);
        adapter.addFragment(recyclerViewFragment, getString(R.string.other));

        int currentPage = viewPager.getCurrentItem();

        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        if (currentPage == 0 && longTermTodayWeather.isEmpty()) {
            currentPage = 1;
        }
        viewPager.setCurrentItem(currentPage, false);

    }

    public RecyclerAdapter getAdapter(int id) {

        RecyclerAdapter weatherRecyclerAdapter;

        if (id == 0)
            weatherRecyclerAdapter = new RecyclerAdapter(getTodayForeCastList(), this);
        else if (id == 1) {
            weatherRecyclerAdapter = new RecyclerAdapter(getTomorrowForeCastList(), this);
        } else {
            weatherRecyclerAdapter = new RecyclerAdapter(getOtherForeCastList(), this);
        }

        return weatherRecyclerAdapter;

    }

    public void setTodayForecastList(WeatherForeCast.ForeCastList forecast) {
        if (!longTermTodayWeather.contains(forecast)) {
            longTermTodayWeather.add(forecast);
        }
    }

    public List<WeatherForeCast.ForeCastList> getTodayForeCastList() {
        return longTermTodayWeather;
    }

    public void setTomorrowForecastList(WeatherForeCast.ForeCastList forecast) {
        if (!longTermTomorrowWeather.contains(forecast)) {
            longTermTomorrowWeather.add(forecast);
        }
    }

    public List<WeatherForeCast.ForeCastList> getTomorrowForeCastList() {
        return longTermTomorrowWeather;
    }

    public void setOtherForecastList(WeatherForeCast.ForeCastList forecast) {
        if (!longTermOtherWeather.contains(forecast)) {
            longTermOtherWeather.add(forecast);
        }
    }

    public List<WeatherForeCast.ForeCastList> getOtherForeCastList() {
        return longTermOtherWeather;
    }

    public void updateTodayWeather() {
        if (MainActivity.NETWORK_STATUS) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(WeatherService.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

//                new LongTermWeather(city).getTodayForeCast();

            WeatherService service = retrofit.create(WeatherService.class);
            Call<WeatherResponse> call = service.getCurrentWeatherData(MainActivity.latitude, MainActivity.longitude, WeatherService.AppId);

            call.enqueue(new Callback<WeatherResponse>() {
                @Override
                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                    if (response.code() == 200) {
                        WeatherResponse weatherResponse = response.body();
//                        assert weatherResponse != null;

                        date = new Date((long) weatherResponse.getDt() * 1000);

                        String location = weatherResponse.getName() + ", " + weatherResponse.getSys().getCountry();
                        String temp = weatherResponse.getMain().getTemp() + " °C";
                        String desc = StringUtils.capitalize(weatherResponse.getWeather().get(0).getDescription());
                        String wind = getResources().getString(R.string.wind) + " " + weatherResponse.getWind().getSpeed() + " m/s";
                        String pressure = getResources().getString(R.string.pressure) + " " + weatherResponse.getMain().getPressure() + " hPa";
                        String humidity = getResources().getString(R.string.humidity) + " " + weatherResponse.getMain().getHumidity() + " %";
                        String dateStr = StringUtils.capitalize(sdf.format(date));

                        toolbar.setTitle(location);
                        sharedPreferenceConfig.setLastLocation(location);

                        tempView.setText(temp);
                        sharedPreferenceConfig.setLastTemperature(temp);

                        descView.setText(desc);
                        sharedPreferenceConfig.setLastDescription(desc);

                        windView.setText(wind);
                        sharedPreferenceConfig.setLastWind(wind);

                        pressureView.setText(pressure);
                        sharedPreferenceConfig.setLastPressure(pressure);

                        humidityView.setText(humidity);
                        sharedPreferenceConfig.setLastHumidity(humidity);

                        dateView.setText(dateStr);
                        sharedPreferenceConfig.setLastUpdateTime(dateStr);

                        String iconUrl = "http://openweathermap.org/img/w/" + weatherResponse.getWeather().get(0).getIcon() + ".png";
                        Picasso.get().load(iconUrl)
                                .resize(300, 340)
                                .into(cloudView);

                    }
                }

                @Override
                public void onFailure(Call<WeatherResponse> call, Throwable t) {
                    Toast.makeText(WeatherActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }

//    public void updateLongTermWeather() {
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(WeatherService.BaseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        WeatherService service = retrofit.create(WeatherService.class);
//        Call<WeatherForeCast> call = service.getForeCastWeatherData(city, WeatherService.AppId);
//
//        call.enqueue(new Callback<WeatherForeCast>() {
//            @Override
//            public void onResponse(Call<WeatherForeCast> call, Response<WeatherForeCast> response) {
//                if (response.code() == 200) {
//                    List<WeatherForeCast.ForeCastList> weatherForeCast = response.body().getList();
//                    weatherListlength = weatherForeCast.size();
//
//                    for (int i = 0; i < weatherListlength; i ++) {
//
//                        final String dateMsString = weatherForeCast.get(i).getDt() + "000";
//                        Calendar cal = Calendar.getInstance();
//                        cal.setTimeInMillis(Long.parseLong(dateMsString));
////                        weather.setIcon(formatting.setWeatherIcon(Integer.parseInt(idString), cal.get(Calendar.HOUR_OF_DAY)));
//
//                        Calendar today = Calendar.getInstance();
//                        today.set(Calendar.HOUR_OF_DAY, 0);
//                        today.set(Calendar.MINUTE, 0);
//                        today.set(Calendar.SECOND, 0);
//                        today.set(Calendar.MILLISECOND, 0);
//
//                        Calendar tomorrow = (Calendar) today.clone();
//                        tomorrow.add(Calendar.DAY_OF_YEAR, 1);
//
//                        Calendar later = (Calendar) today.clone();
//                        later.add(Calendar.DAY_OF_YEAR, 2);
//
//                        if (cal.before(tomorrow)) {
////                            longTermTodayWeather.add(foreCastList.get(i));
//                            setTodayForecastList(weatherForeCast.get(i));
//                        } else if (cal.before(later)) {
////                            longTermTomorrowWeather.add(foreCastList.get(i));
//                            setTomorrowForecastList(weatherForeCast.get(i));
//                        } else {
////                            longTermOtherWeather.add(foreCastList.get(i));
//                            setOtherForecastList(weatherForeCast.get(i));
//                        }
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<WeatherForeCast> call, Throwable t) {
//                AlertDialog.Builder dialog = new AlertDialog.Builder(WeatherActivity.this);
//                dialog.setTitle("Long Term parsing failed!");
//                dialog.setMessage(t.getMessage());
//                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                AlertDialog mDialog = dialog.create();
//                mDialog.show();
//            }
//        });
//
//    }

    @Override
    protected void onStart() {
        super.onStart();
//        updateTodayWeather();
////        updateLongTermWeather();
//        updateLongTermUI();
////        refreshWeather();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MainActivity.NETWORK_STATUS) {
            updateTodayWeather();
//            updateLongTermWeather();
            updateLongTermUI();
        } else {
            MainActivity.showNetworkErrorDialog();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainActivity.locationManager.removeUpdates(mainActivity);
        finish();
    }
}
