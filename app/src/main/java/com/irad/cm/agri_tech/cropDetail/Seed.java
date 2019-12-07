package com.irad.cm.agri_tech.cropDetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Seed {

    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("culture")
    @Expose
    private String culture;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("other_name")
    @Expose
    private String otherName;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("rendement")
    @Expose
    private String rendement;
    @SerializedName("maturity")
    @Expose
    private String maturity;
    @SerializedName("particularity")
    @Expose
    private String particularity;
    @SerializedName("adaptation_zone")
    @Expose
    private List<String> adaptationZone = null;
    @SerializedName("distribution")
    @Expose
    private List<Distribution> distribution = null;
    @SerializedName("seed_price")
    @Expose
    private SeedPrice seedPrice;
    @SerializedName("adapted")
    @Expose
    private Boolean adapted;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRendement() {
        return rendement;
    }

    public void setRendement(String rendement) {
        this.rendement = rendement;
    }

    public String getMaturity() {
        return maturity;
    }

    public void setMaturity(String maturity) {
        this.maturity = maturity;
    }

    public String getParticularity() {
        return particularity;
    }

    public void setParticularity(String particularity) {
        this.particularity = particularity;
    }

    public List<String> getAdaptationZone() {
        return adaptationZone;
    }

    public void setAdaptationZone(List<String> adaptationZone) {
        this.adaptationZone = adaptationZone;
    }

    public List<Distribution> getDistribution() {
        return distribution;
    }

    public void setDistribution(List<Distribution> distribution) {
        this.distribution = distribution;
    }

    public SeedPrice getSeedPrice() {
        return seedPrice;
    }

    public void setSeedPrice(SeedPrice seedPrice) {
        this.seedPrice = seedPrice;
    }

    public Boolean getAdapted() {
        return adapted;
    }

    public void setAdapted(Boolean adapted) {
        this.adapted = adapted;
    }

}