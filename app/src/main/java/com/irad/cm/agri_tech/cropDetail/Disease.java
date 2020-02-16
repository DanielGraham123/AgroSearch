package com.irad.cm.agri_tech.cropDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Disease {

    @SerializedName("culture")
    @Expose
    private String culture;
    @SerializedName("disease")
    @Expose
    private Disease_ disease;
    @SerializedName("image")
    @Expose
    private String image;

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public Disease_ getDisease() {
        return disease;
    }

    public void setDisease(Disease_ disease) {
        this.disease = disease;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
