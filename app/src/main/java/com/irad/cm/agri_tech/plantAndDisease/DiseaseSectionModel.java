package com.irad.cm.agri_tech.plantAndDisease;

import java.util.ArrayList;

public class DiseaseSectionModel {
    public String header;
    public ArrayList<Disease> diseases;

    public DiseaseSectionModel(String header, ArrayList<Disease> diseases) {
        this.header = header;
        this.diseases = diseases;
    }

    public String getHeader() {
        return header;
    }

    public ArrayList<Disease> getDiseases() {
        return diseases;
    }
}
