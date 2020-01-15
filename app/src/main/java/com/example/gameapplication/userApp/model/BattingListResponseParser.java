package com.example.gameapplication.userApp.model;

import java.util.List;

public class BattingListResponseParser {

    String Status, Message;
    List<BettingTime> betting_time;

    public String getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public List<BettingTime> getBetting_time() {
        return betting_time;
    }

    class BettingTime {
        String id, betting_start_time, betting_end_time, left_time, start_time;

        public String getId() {
            return id;
        }

        public String getBetting_start_time() {
            return betting_start_time;
        }

        public String getBetting_end_time() {
            return betting_end_time;
        }

        public String getLeft_time() {
            return left_time;
        }

        public String getStart_time() {
            return start_time;
        }
    }

}
