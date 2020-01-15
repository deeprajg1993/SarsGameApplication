package com.example.gameapplication.userApp.model;

public class LoginResponse {
    String Status, Message, id, user_id, chip_point ,user_type ,total_user;

    public String getStatus() {
        return Status;
    }

    public String getUser_type() {
        return user_type;
    }

    public String getTotal_user() {
        return total_user;
    }

    public String getMessage() {
        return Message;
    }

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
