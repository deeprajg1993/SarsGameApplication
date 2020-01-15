package com.example.gameapplication.userApp.model;

public class BattingListingViewModel {

    String tvNextDate, tvChipsTime, tvChipsDate, tvNumber;

    public BattingListingViewModel(String tvNextDate, String tvChipsTime, String tvChipsDate, String tvNumber) {
        this.tvNextDate = tvNextDate;
        this.tvChipsTime = tvChipsTime;
        this.tvChipsDate = tvChipsDate;
        this.tvNumber = tvNumber;
    }

    public String getTvNextDate() {
        return tvNextDate;
    }

    public void setTvNextDate(String tvNextDate) {
        this.tvNextDate = tvNextDate;
    }

    public String getTvChipsTime() {
        return tvChipsTime;
    }

    public void setTvChipsTime(String tvChipsTime) {
        this.tvChipsTime = tvChipsTime;
    }

    public String getTvChipsDate() {
        return tvChipsDate;
    }

    public void setTvChipsDate(String tvChipsDate) {
        this.tvChipsDate = tvChipsDate;
    }

    public String getTvNumber() {
        return tvNumber;
    }

    public void setTvNumber(String tvNumber) {
        this.tvNumber = tvNumber;
    }
}
