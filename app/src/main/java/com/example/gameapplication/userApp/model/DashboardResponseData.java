package com.example.gameapplication.userApp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardResponseData {

    @SerializedName("Status")
    @Expose
    public String status;
    @SerializedName("Message")
    @Expose
    public String message;
    @SerializedName("last_betting")
    @Expose
    public List<LastBetting> lastBetting;

    @SerializedName("win_betting")
    @Expose
    public List<WinBetting> winBetting;

    @SerializedName("betting_time")
    @Expose
    public List<BettingTime> bettingTime;


    public class LastBetting {
        @SerializedName("betting_id")
        @Expose
        public String betting_id;
        @SerializedName("number")
        @Expose
        public String number;
        @SerializedName("chips")
        @Expose
        public String chips;
        @SerializedName("start_time")
        @Expose
        public String startTime;
        @SerializedName("betting_status")
        @Expose
        public String bettingStatus;
        @SerializedName("date")
        @Expose
        public String date;

    }

    public class WinBetting {

        @SerializedName("win_number")
        @Expose
        public String win_number;
        @SerializedName("date")
        @Expose
        public String date;
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("time")
        @Expose
        public String time;

    }

    public class BettingTime {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("betting_start_time")
        @Expose
        public String bettingStartTime;
        @SerializedName("start_am_pm")
        @Expose
        public String startAmPm;
        @SerializedName("betting_end_time")
        @Expose
        public String bettingEndTime;
        @SerializedName("end_am_pm")
        @Expose
        public String endAmPm;
        @SerializedName("left_time")
        @Expose
        public String leftTime;
        @SerializedName("start_time")
        @Expose
        public String startTime;

    }
}
