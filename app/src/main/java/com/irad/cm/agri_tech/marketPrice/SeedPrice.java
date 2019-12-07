package com.irad.cm.agri_tech.marketPrice;


import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeedPrice implements Serializable {

    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("other_name")
    @Expose
    private String otherName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("distribution")
    @Expose
    private List<Distribution> distribution = null;
    @SerializedName("seed_price")
    @Expose
    private SeedPrice_ seedPrice;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Distribution> getDistribution() {
        return distribution;
    }

    public void setDistribution(List<Distribution> distribution) {
        this.distribution = distribution;
    }

    public SeedPrice_ getSeedPrice() {
        return seedPrice;
    }

    public void setSeedPrice(SeedPrice_ seedPrice) {
        this.seedPrice = seedPrice;
    }

}
