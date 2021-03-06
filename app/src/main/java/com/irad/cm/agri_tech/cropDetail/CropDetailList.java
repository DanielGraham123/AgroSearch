package com.irad.cm.agri_tech.cropDetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CropDetailList {

    @SerializedName("Description")
    @Expose
    private Description description;
    @SerializedName("Seed")
    @Expose
    private List<Seed> seed = null;
    @SerializedName("Disease")
    @Expose
    private List<Disease> disease = null;

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public List<Seed> getSeed() {
        return seed;
    }

    public void setSeed(List<Seed> seed) {
        this.seed = seed;
    }

    public List<Disease> getDisease() {
        return disease;
    }

    public void setDisease(List<Disease> disease) {
        this.disease = disease;
    }

}