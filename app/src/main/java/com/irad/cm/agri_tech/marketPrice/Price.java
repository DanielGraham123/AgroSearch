package com.irad.cm.agri_tech.marketPrice;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Price {

    @SerializedName("culture")
    @Expose
    private String culture;
    @SerializedName("market_price")
    @Expose
    private List<MarketPrice> marketPrice = null;
    @SerializedName("seed_price")
    @Expose
    private List<SeedPrice> seedPrice = null;

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public List<MarketPrice> getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(List<MarketPrice> marketPrice) {
        this.marketPrice = marketPrice;
    }

    public List<SeedPrice> getSeedPrice() {
        return seedPrice;
    }

    public void setSeedPrice(List<SeedPrice> seedPrice) {
        this.seedPrice = seedPrice;
    }

}
