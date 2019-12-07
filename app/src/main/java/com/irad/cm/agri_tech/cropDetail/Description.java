package com.irad.cm.agri_tech.cropDetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Description {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("type_of_culture")
    @Expose
    private String typeOfCulture;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("favorable_zone")
    @Expose
    private List<String> favorableZone = null;
    @SerializedName("fiche_technique")
    @Expose
    private String ficheTechnique;
    @SerializedName("campaigns")
    @Expose
    private List<Campaign> campaigns = null;
    @SerializedName("market_price")
    @Expose
    private List<MarketPrice> marketPrice = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTypeOfCulture() {
        return typeOfCulture;
    }

    public void setTypeOfCulture(String typeOfCulture) {
        this.typeOfCulture = typeOfCulture;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getFavorableZone() {
        return favorableZone;
    }

    public void setFavorableZone(List<String> favorableZone) {
        this.favorableZone = favorableZone;
    }

    public String getFicheTechnique() {
        return ficheTechnique;
    }

    public void setFicheTechnique(String ficheTechnique) {
        this.ficheTechnique = ficheTechnique;
    }

    public List<Campaign> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(List<Campaign> campaigns) {
        this.campaigns = campaigns;
    }

    public List<MarketPrice> getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(List<MarketPrice> marketPrice) {
        this.marketPrice = marketPrice;
    }

}