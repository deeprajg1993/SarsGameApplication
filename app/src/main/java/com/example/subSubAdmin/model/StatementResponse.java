package com.example.subSubAdmin.model;

import java.util.List;

public class StatementResponse {
    String Status, Message;
    List<StatementDataResponse> Data;

    public String getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public List<StatementDataResponse> getData() {
        return Data;
    }

    public class StatementDataResponse {
        String id, user, type, chips, date_time ,user_id;

        public String getId() {
            return id;
        }

        public String getUser() {
            return user;
        }

        public String getType() {
            return type;
        }

        public String getChips() {
            return chips;
        }

        public String getDate_time() {
            return date_time;
        }

        public String getUser_id() {
            return user_id;
        }
    }
}
