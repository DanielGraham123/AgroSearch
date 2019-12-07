package com.irad.cm.agri_tech.crops;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CultureAnnuelle {

    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;

    public CultureAnnuelle(String cropName, String res) {
        name = cropName;
        image = res;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
