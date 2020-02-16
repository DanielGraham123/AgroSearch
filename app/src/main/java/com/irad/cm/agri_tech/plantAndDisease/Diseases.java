package com.irad.cm.agri_tech.plantAndDisease;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Diseases {

    @SerializedName("disease")
    @Expose
    private List<Disease> diseases = null;

    public List<Disease> getDisease() {
        return diseases;
    }

    public void setDisease(List<Disease> disease) {
        this.diseases = disease;
    }

}