package com.example.gameapplication.userApp.ui.home;

public class BannerModel {

    String tvValue, tvName, tvUserId;

    public BannerModel(String tvValue, String tvName, String tvUserId) {
        this.tvValue = tvValue;
        this.tvName = tvName;
        this.tvUserId = tvUserId;
    }

    public String getTvValue() {
        return tvValue;
    }

    public void setTvValue(String tvValue) {
        this.tvValue = tvValue;
    }

    public String getTvName() {
        return tvName;
    }

    public void setTvName(String tvName) {
        this.tvName = tvName;
    }

    public String getTvUserId() {
        return tvUserId;
    }

    public void setTvUserId(String tvUserId) {
        this.tvUserId = tvUserId;
    }
}
