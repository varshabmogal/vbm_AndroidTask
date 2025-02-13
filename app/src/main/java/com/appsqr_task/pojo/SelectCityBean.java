package com.appsqr_task.pojo;

public class SelectCityBean {
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public SelectCityBean(String city, String country) {
        this.city = city;
        this.country = country;
    }
    public SelectCityBean(){}


    String city;
     String country;

}
