package com.irad.cm.agri_tech.cropDetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Variety {

    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("other_name")
    @Expose
    private String otherName;
    @SerializedName("adaptation_zone")
    @Expose
    private List<String> adaptationZone = null;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public List<String> getAdaptationZone() {
        return adaptationZone;
    }

    public void setAdaptationZone(List<String> adaptationZone) {
        this.adaptationZone = adaptationZone;
    }

}