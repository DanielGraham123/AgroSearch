package com.irad.cm.agri_tech.crops;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CropList {

    @SerializedName("all")
    @Expose
    private List<All> all = null;

    public List<All> getAll() {
        return all;
    }

    public void setAll(List<All> all) {
        this.all = all;
    }

}
