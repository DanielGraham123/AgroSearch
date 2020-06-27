package com.irad.cm.agri_tech;

public class LocationInfo {

    private static  String city, country, region;

    public LocationInfo() {

    }

    public static String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public static String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
