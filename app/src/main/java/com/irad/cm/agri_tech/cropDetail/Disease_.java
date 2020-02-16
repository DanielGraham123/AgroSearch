package com.irad.cm.agri_tech.cropDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Disease_ {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("agent")
    @Expose
    private String agent;
    @SerializedName("symptom")
    @Expose
    private String symptom;
    @SerializedName("solution")
    @Expose
    private String solution;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

}

