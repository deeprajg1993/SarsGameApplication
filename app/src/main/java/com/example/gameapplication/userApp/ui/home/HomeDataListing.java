package com.example.gameapplication.userApp.ui.home;

public class HomeDataListing {
    String tvChipsNumber, tvChipsName;

    public HomeDataListing(String tvChipsNumber, String tvChipsName) {
        this.tvChipsNumber = tvChipsNumber;
        this.tvChipsName = tvChipsName;
    }

    public String getTvChipsNumber() {
        return tvChipsNumber;
    }

    public void setTvChipsNumber(String tvChipsNumber) {
        this.tvChipsNumber = tvChipsNumber;
    }

    public String getTvChipsName() {
        return tvChipsName;
    }

    public void setTvChipsName(String tvChipsName) {
        this.tvChipsName = tvChipsName;
    }
}
