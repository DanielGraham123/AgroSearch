package com.irad.cm.agri_tech.plantAndDisease;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Plant {

    @SerializedName("culture")
    @Expose
    private String culture;
    @SerializedName("disease")
    @Expose
    private PlantDisease disease;
    @SerializedName("image")
    @Expose
    private String image;

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public PlantDisease getDisease() {
        return disease;
    }

    public void setDisease(PlantDisease disease) {
        this.disease = disease;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}