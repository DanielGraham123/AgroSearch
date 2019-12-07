package com.irad.cm.agri_tech.cropDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Distribution {

    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("available")
    @Expose
    private Boolean available;
    @SerializedName("updated")
    @Expose
    private String updated;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

}