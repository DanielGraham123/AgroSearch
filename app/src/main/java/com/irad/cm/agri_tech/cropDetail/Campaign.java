package com.irad.cm.agri_tech.cropDetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Campaign {

    @SerializedName("campaign_name")
    @Expose
    private String campaignName;
    @SerializedName("regions")
    @Expose
    private List<String> regions = null;
    @SerializedName("du")
    @Expose
    private String du;
    @SerializedName("au")
    @Expose
    private String au;

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public String getDu() {
        return du;
    }

    public void setDu(String du) {
        this.du = du;
    }

    public String getAu() {
        return au;
    }

    public void setAu(String au) {
        this.au = au;
    }

}