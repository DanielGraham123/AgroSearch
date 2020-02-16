package com.irad.cm.agri_tech.plantAndDisease;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Disease {

    @SerializedName("culture")
    @Expose
    private String crop;
    @SerializedName("disease")
    @Expose
    private List<Plant> plant_diseases = null;

    public String getCulture() {
        return crop;
    }

    public void setCulture(String crop) {
        this.crop = crop;
    }

    public List<Plant> getDisease() {
        return plant_diseases;
    }

    public void setDisease(List<Plant> disease) {
        this.plant_diseases = disease;
    }

}