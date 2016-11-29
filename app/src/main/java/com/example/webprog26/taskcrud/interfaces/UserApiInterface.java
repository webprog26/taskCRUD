package com.example.webprog26.taskcrud.interfaces;

import com.example.webprog26.taskcrud.models.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by webprog26 on 28.11.2016.
 */

public interface UserApiInterface {

    @GET("task_network/read.php")
    Call<UserResponse> getUsers();

    @FormUrlEncoded
    @POST("task_network/add.php")
    Call<UserResponse> addUser(@Field("user_name") String userName,
                               @Field("user_second_name") String userSecondName,
                               @Field("user_city") String userCity);

    @FormUrlEncoded
    @POST("task_network/edit.php")
    Call<UserResponse> editUser(@Field("user_id") long userId,
                                @Field("user_name") String userName,
                                @Field("user_second_name") String userSecondName,
                                @Field("user_city") String userCity);

    @FormUrlEncoded
    @POST("task_network/delete.php")
    Call<UserResponse> deleteUser(@Field("user_id") long userId);
}
