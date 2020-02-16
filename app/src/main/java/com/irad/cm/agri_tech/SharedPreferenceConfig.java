package com.irad.cm.agri_tech;

import android.content.Context;
import android.content.SharedPreferences;


import java.text.SimpleDateFormat;
import java.util.Date;

public class SharedPreferenceConfig {

    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferenceConfig(Context context) {
        this.context = context;

        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.lastUpdatePrefs), Context.MODE_PRIVATE);
    }

    public String getLastUpdateTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("E, dd.MM.yy");
        Date date = new Date();

        String updateTime = sharedPreferences.getString(context.getResources().getString(R.string.lastUpdateTime), sdf.format(date));

        return updateTime;
    }

    public String getLastLocation() {
        String location = sharedPreferences.getString(context.getResources().getString(R.string.lastLocation), "");

        return location;
    }

    public String getLastHumidity() {

        String humidity = sharedPreferences.getString(context.getResources().getString(R.string.lastHumidity), "");

        return humidity;

    }

    public String getLastPressure() {
        String pressure = sharedPreferences.getString(context.getResources().getString(R.string.lastPressure), "");

        return pressure;
    }

    public String getLastWind() {

        String wind = sharedPreferences.getString(context.getResources().getString(R.string.lastWind), "");

        return wind;
    }

    public String getLastDescription() {
        String desc = sharedPreferences.getString(context.getResources().getString(R.string.lastDescription), "");

        return desc;
    }

    public String getLastTemperature() {
        String temp = sharedPreferences.getString(context.getResources().getString(R.string.lastTemperature), "");

        return temp;
    }

    public void setLastUpdateTime(String updateTime) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(context.getResources().getString(R.string.lastUpdateTime), updateTime);

        editor.commit();
    }

    public void setLastHumidity(String humidity) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(context.getResources().getString(R.string.lastHumidity), humidity);

        editor.commit();
    }

    public void setLastPressure(String pressure) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(context.getResources().getString(R.string.lastPressure), pressure);

        editor.commit();
    }

    public void setLastWind(String wind) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(context.getResources().getString(R.string.lastWind), wind);

        editor.commit();
    }

    public void setLastDescription(String description) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(context.getResources().getString(R.string.lastDescription), description);

        editor.commit();
    }

    public void setLastTemperature(String temperature) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(context.getResources().getString(R.string.lastTemperature), temperature);

        editor.commit();
    }

    public void setLastLocation(String location) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(context.getResources().getString(R.string.lastLocation), location);

        editor.commit();
    }
}
