package com.irad.cm.agri_tech.marketPrice;

import java.util.ArrayList;

public class SeedSectionModel {
    public String header;
    public ArrayList<SeedPrice> seedPrice;

    public SeedSectionModel(String header, ArrayList<SeedPrice> seedPrice) {
        this.header = header;
        this.seedPrice = seedPrice;
    }

    public String getHeader() {
        return header;
    }

    public ArrayList<SeedPrice> getSeedPrice() {
        return seedPrice;
    }
}
