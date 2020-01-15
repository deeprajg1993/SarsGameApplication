package com.example.subSubAdmin.model;

import java.util.List;

public class UserDetailsResponse {
    String Status, Message;
    List<UserDataResponse> Data;

    public String getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public List<UserDataResponse> getData() {
        return Data;
    }

    public class UserDataResponse {
        String id, user_id, chip_point, UB, BB, AD;

        public String getId() {
            return id;
        }

        public String getUB() {
            return UB;
        }

        public String getBB() {
            return BB;
        }

        public String getAD() {
            return AD;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getChip_point() {
            return chip_point;
        }
    }
}
