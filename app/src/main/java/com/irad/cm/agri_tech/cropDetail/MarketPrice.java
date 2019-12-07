package com.irad.cm.agri_tech.cropDetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarketPrice {

    @SerializedName("culture")
    @Expose
    private String culture;
    @SerializedName("region")
    @Expose
    private List<String> region = null;
    @SerializedName("measure")
    @Expose
    private String measure;
    @SerializedName("price")
    @Expose
    private Integer price;

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public List<String> getRegion() {
        return region;
    }

    public void setRegion(List<String> region) {
        this.region = region;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}