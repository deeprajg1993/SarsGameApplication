package com.example.gameapplication.userApp.model;

import java.util.List;

public class BattingHistoryResponse {
    String Status, Message;
    List<ListData> Data;

    public String getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public List<ListData> getData() {
        return Data;
    }

    public class ListData {
        String id, number, chips, chip_status, start_time, date;

        public String getId() {
            return id;
        }

        public String getNumber() {
            return number;
        }

        public String getChips() {
            return chips;
        }

        public String getChip_status() {
            return chip_status;
        }

        public String getStart_time() {
            return start_time;
        }

        public String getDate() {
            return date;
        }
    }
}
