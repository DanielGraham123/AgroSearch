package com.irad.cm.agri_tech;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;

import java.io.IOException;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class BackgroundTasks implements LocationListener {

    private FusedLocationProviderClient client;
    private String locationInfo = "";
    private Context context;
    private Activity activity;

    LocationManager locationManager;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    //    Marker marker;
    LocationListener locationListener;

    public BackgroundTasks(Context context, Activity activity) {
        this.context = context.getApplicationContext();
        this.activity = activity;
    }

    public String geoLocate() {

//        client = LocationServices.getFusedLocationProviderClient(context);
//LOCAT
//        if (ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
////            return;
//        }
//        client.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                locationInfo += location;
//            }
//        });
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
//            return TODO;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

        onLocationChanged(location);

        return locationInfo;

    }

    public void requestPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        //get the location name from latitude and longitude
        Geocoder geocoder = new Geocoder(context);
        try {
            List<Address> addresses =
                    geocoder.getFromLocation(latitude, longitude, 1);
            locationInfo = addresses.get(0).getLocality() + ";";
            locationInfo += addresses.get(0).getCountryName();
            locationInfo += "\n(" + location.getLatitude() + ", " + location.getLongitude() + ")";
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
}
