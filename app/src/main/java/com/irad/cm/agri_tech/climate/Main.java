package com.irad.cm.agri_tech.climate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("humidity")
    @Expose
    private Float humidity;
    @SerializedName("pressure")
    @Expose
    private Float pressure;
    @SerializedName("temp")
    @Expose
    private Float temp;
    @SerializedName("temp_max")
    @Expose
    private Float tempMax;
    @SerializedName("temp_min")
    @Expose
    private Float tempMin;

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    public Float getPressure() {
        return pressure;
    }

    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }

    public Float getTemp() {
        return temp;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }

    public Float getTempMax() {
        return tempMax;
    }

    public void setTempMax(Float tempMax) {
        this.tempMax = tempMax;
    }

    public Float getTempMin() {
        return tempMin;
    }

    public void setTempMin(Float tempMin) {
        this.tempMin = tempMin;
    }

}