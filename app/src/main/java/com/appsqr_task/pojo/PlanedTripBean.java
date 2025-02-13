package com.appsqr_task.pojo;


public class PlanedTripBean {

    private String id;
    private String city;
    private String date;
    private String img;
    private String title;
    private String duration;
    public PlanedTripBean() {
    }
    public PlanedTripBean(String city, String date, String img, String title, String duration) {
        this.city = city;
        this.date = date;
        this.img = img;
        this.title = title;
        this.duration = duration;
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getcity() {
        return city;
    }
    public void setcity(String city) {
        this.city = city;
    }
    public String getdate() {
        return date;
    }
    public void setdate(String date) {
        this.date = date;
    }
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
}
