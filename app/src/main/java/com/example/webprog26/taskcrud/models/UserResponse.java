package com.example.webprog26.taskcrud.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by webprog26 on 28.11.2016.
 */

public class UserResponse {

    @SerializedName("users")
    private List<User> mUserList = null;

    public List<User> getUserList() {
        return mUserList;
    }

    public void setUserList(List<User> userList) {
        this.mUserList = userList;
    }
}
