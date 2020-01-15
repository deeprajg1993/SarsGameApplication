package com.example.gameapplication.userApp.model;

import java.util.List;

public class BattingChipsResponse {
    String Status, Message;
    List<BettingChipsResponse> Data;

    public String getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public List<BettingChipsResponse> getData() {
        return Data;
    }

    public class BettingChipsResponse {
        String id, chips, type, date, time;

        public String getId() {
            return id;
        }

        public String getChips() {
            return chips;
        }

        public String getType() {
            return type;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }


    }


}
