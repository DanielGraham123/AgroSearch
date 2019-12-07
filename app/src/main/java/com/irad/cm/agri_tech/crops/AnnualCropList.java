package com.irad.cm.agri_tech.crops;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnnualCropList {

    @SerializedName("culture annuelle")
    @Expose
    private List<CultureAnnuelle> cultureAnnuelle = null;

    public List<CultureAnnuelle> getCultureAnnuelle() {
        return cultureAnnuelle;
    }

    public void setCultureAnnuelle(List<CultureAnnuelle> cultureAnnuelle) {
        this.cultureAnnuelle = cultureAnnuelle;
    }

}