package com.irad.cm.agri_tech;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.irad.cm.agri_tech.fragments.HomeFragment;
import com.irad.cm.agri_tech.fragments.LocateMeFragment;
import com.irad.cm.agri_tech.fragments.SignupFragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private ConnectivityManager connectivityManager;
    private static AlertDialog mDialog;
    private static AlertDialog.Builder dialogBuilder;
    private BackgroundTasks backgroundTasks;
    private Context context;
    public static String locationInfo;
    public static LocationManager locationManager;
    private static Runnable runnable;
    private static Handler handler = new Handler();
    BackgroundTask bgTask;
    public static FragmentManager fragmentManager;
    private Toolbar toolbar;

    public static boolean NETWORK_STATUS = false;

    public static double latitude, longitude;

    public Criteria criteria;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);

        toolbar= findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setBackgroundColor(fetchColor(R.color.colorPrimary, this));
        bottomNav.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
        bottomNav.setItemIconTintList(ColorStateList.valueOf(Color.WHITE));
        bottomNav.setItemTextAppearanceActive(getColor(R.color.activeAppearance));
        bottomNav.setItemTextAppearanceInactive(Color.WHITE);

        bottomNav.setSelectedItemId(R.id.action_home);

        bottomNav.setOnNavigationItemSelectedListener(navListener);

        context = getApplicationContext();

//        progressBar = new ProgressDialog(this);

        dialogBuilder = new AlertDialog.Builder(this);

        backgroundTasks = new BackgroundTasks(context, MainActivity.this);

        fragmentManager = getSupportFragmentManager();

        criteria = new Criteria();

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            fragmentManager.beginTransaction().add(R.id.fragment_container, new HomeFragment()).commit();


            connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

            runnable = new Runnable() {
                @Override
                public void run() {
                    checkOnlineStatus();
                    handler.postDelayed(this, 2000);
                }
            };
            handler.post(runnable);

        }

    }

    private void checkOnlineStatus() {
        if (checkNetworkStatus()) { // if user is connected

            NETWORK_STATUS = true;

            bgTask = new BackgroundTask();

            bgTask.execute(context);

            Log.i("CONNECTED", "User connected!");

        } else {

            NETWORK_STATUS = false;


        }
    }

    public static void showNetworkErrorDialog() {
        dialogBuilder.setTitle(R.string.status_title);

        dialogBuilder.setMessage(R.string.status);

        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                handler.removeCallbacks(runnable);
            }
        });

        mDialog = dialogBuilder.create();
        mDialog.show();
    }

    public boolean checkNetworkStatus() {

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            return true;
        } else {
            return false;
        }

    }

    public int fetchColor(@ColorRes int color, Context context) {
        return ContextCompat.getColor(context, color);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.header_menu1, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.language:
                showChangeLanguageDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void showChangeLanguageDialog() {
        final String[] listItems = {"Français", "English"};

        AlertDialog.Builder builder = new AlertDialog.Builder((this));
        builder.setTitle(R.string.choose_language);

        builder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    setLocale("fr");
                    recreate();
                } else if (which == 1) {
                    setLocale("en");
                    recreate();
                }
                dialog.dismiss();
            }
        });

        mDialog = builder.create();
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // save data to shared preferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }


    // load language saved in shared preferences
    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.action_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.action_signup:
                            selectedFragment = new SignupFragment();
                            break;
                        case R.id.action_location:
                            selectedFragment = new LocateMeFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        //get the location name from latitude and longitude
        Geocoder geocoder = new Geocoder(context);
        try {
            List<Address> addresses =
                    geocoder.getFromLocation(latitude, longitude, 1);
            locationInfo = addresses.get(0).getLocality() + ", ";
            locationInfo += addresses.get(0).getCountryName();
//            locationInfo += "\n(" + location.getLatitude() + ", " + location.getLongitude() + ")";
            System.out.println("Location info: " +locationInfo);

//                    LatLng latLng = new LatLng(latitude, longitude);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class BackgroundTask extends AsyncTask<Context, Integer, String> {

        @Override
        protected void onPreExecute() {

//            String msg = "Connecting...";
//            progressBar.setCancelable(true);
//            progressBar.setMessage(msg);
//            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressBar.setProgress(5);
//            progressBar.setMax(100);
//            progressBar.show();
        }


        @Override
        protected String doInBackground(Context... contexts) {

            backgroundTasks.requestPermission(MainActivity.this);

//            message = backgroundTasks.geoLocate();
            locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
//            return TODO;
            }

            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            onLocationChanged(location);

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

//            progressBar.incrementProgressBy(progressIncr);
        }

        @Override
        protected void onPostExecute(String s) {
//            Log.i("LOCATION", MainActivity.locationInfo);
//            Toast.makeText(getApplicationContext(), locationInfo, Toast.LENGTH_LONG).show();
        }

    }

}

