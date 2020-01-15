package com.example.gameapplication.userApp.ui.battinglist;

public class BattingListViewModel{

   String tvListName ,tvTime ,tvCountValue ;

    public String getTvListName() {
        return tvListName;
    }

    public String getTvTime() {
        return tvTime;
    }

    public String getTvCountValue() {
        return tvCountValue;
    }

    public BattingListViewModel(String tvListName, String tvTime, String tvCountValue) {
        this.tvListName = tvListName;
        this.tvTime = tvTime;
        this.tvCountValue = tvCountValue;
    }
}