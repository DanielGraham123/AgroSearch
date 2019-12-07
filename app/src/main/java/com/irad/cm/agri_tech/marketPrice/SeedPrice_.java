package com.irad.cm.agri_tech.marketPrice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SeedPrice_ implements Serializable {

    @SerializedName("culture")
    @Expose
    private String culture;
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
