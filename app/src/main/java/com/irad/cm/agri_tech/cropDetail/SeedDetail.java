package com.irad.cm.agri_tech.cropDetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeedDetail {

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
    private List<Object> distribution = null;
    @SerializedName("price")
    @Expose
    private Price price;

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

    public List<Object> getDistribution() {
        return distribution;
    }

    public void setDistribution(List<Object> distribution) {
        this.distribution = distribution;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

}
