package com.example.webprog26.taskcrud.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by webprog26 on 28.11.2016.
 */

public class User implements Serializable{

    @SerializedName("user_id")
    private long mUserId;
    @SerializedName("user_name")
    private String mUserName;
    @SerializedName("user_second_name")
    private String mUserSecondName;
    @SerializedName("user_city")
    private String mUserCity;

    public long getUserId() {
        return mUserId;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getUserSecondName() {
        return mUserSecondName;
    }

    public String getUserCity() {
        return mUserCity;
    }

    public static Builder newBuilder(){
        return new User().new Builder();
    }

    public class Builder{
        public Builder setUserId(long userId){
            User.this.mUserId = userId;
            return this;
        }

        public Builder setUserName(String userName){
            User.this.mUserName = userName;
            return this;
        }

        public Builder setUserSecondName(String userSecondName){
            User.this.mUserSecondName = userSecondName;
            return this;
        }

        public Builder setUserCity(String userCity){
            User.this.mUserCity = userCity;
            return this;
        }

        public User build(){
            return User.this;
        }
    }

    @Override
    public String toString() {
        return "name " + getUserName() + ", second name " + getUserSecondName() + ", city " + getUserCity();
    }
}
