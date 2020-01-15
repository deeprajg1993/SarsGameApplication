package com.example.subSubAdmin.model;


import java.util.List;

public class ProfitLossResponse {
    String Status, Message;
    List<ProfitLossData> Data;

    public String getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public List<ProfitLossData> getData() {
        return Data;
    }

    public class ProfitLossData {
        String number, chips, profit_type, date, start_time, end_time;

        public String getNumber() {
            return number;
        }

        public String getChips() {
            return chips;
        }

        public String getProfit_type() {
            return profit_type;
        }

        public String getDate() {
            return date;
        }

        public String getStart_time() {
            return start_time;
        }

        public String getEnd_time() {
            return end_time;
        }
    }
}
