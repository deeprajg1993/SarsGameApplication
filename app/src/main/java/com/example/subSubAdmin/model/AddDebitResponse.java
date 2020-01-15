package com.example.subSubAdmin.model;

import java.util.List;

public class AddDebitResponse {
    String Status, Message;
    List<AddDebitDetailsResponse> Data;

    public String getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public List<AddDebitDetailsResponse> getData() {
        return Data;
    }

    public class AddDebitDetailsResponse {
        String id, user_id, chip_point;

        public String getId() {
            return id;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getChip_point() {
            return chip_point;
        }
    }
}
