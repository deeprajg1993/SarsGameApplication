package com.example.gameapplication.userApp.ui.home;

public class HomeDashboardModel {
    String tvBattingClose, tvAm_Pm, tvTime, tvLeftTime;

    public HomeDashboardModel(String tvBattingClose, String tvAm_Pm, String tvTime, String tvLeftTime) {
        this.tvBattingClose = tvBattingClose;
        this.tvAm_Pm = tvAm_Pm;
        this.tvTime = tvTime;
        this.tvLeftTime = tvLeftTime;
    }

    public String getTvBattingClose() {
        return tvBattingClose;
    }

    public void setTvBattingClose(String tvBattingClose) {
        this.tvBattingClose = tvBattingClose;
    }

    public String getTvAm_Pm() {
        return tvAm_Pm;
    }

    public void setTvAm_Pm(String tvAm_Pm) {
        this.tvAm_Pm = tvAm_Pm;
    }

    public String getTvTime() {
        return tvTime;
    }

    public void setTvTime(String tvTime) {
        this.tvTime = tvTime;
    }

    public String getTvLeftTime() {
        return tvLeftTime;
    }

    public void setTvLeftTime(String tvLeftTime) {
        this.tvLeftTime = tvLeftTime;
    }
}
