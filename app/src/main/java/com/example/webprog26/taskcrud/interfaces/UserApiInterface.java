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

    /**
     * These methods used with Retrofit library
     */

    /**
     * Creates new user data in remote database via POST request
     * @param userName {@link String}
     * @param userSecondName {@link String}
     * @param userCity {@link String}
     * @return Call<UserResponse>
     */
    @FormUrlEncoded
    @POST("task_network/add.php")
    Call<UserResponse> addUser(@Field("user_name") String userName,
                               @Field("user_second_name") String userSecondName,
                               @Field("user_city") String userCity);


    /**
     * Reads data from remote database via GET request
     * @return Call<UserResponse>
     */
    @GET("task_network/read.php")
    Call<UserResponse> getUsers();

    /**
     * Updates existing user, stored in remote database, with the new data via POST request
     * @param userName {@link String}
     * @param userSecondName {@link String}
     * @param userCity {@link String}
     * @return Call<UserResponse>
     */
    @FormUrlEncoded
    @POST("task_network/edit.php")
    Call<UserResponse> editUser(@Field("user_id") long userId,
                                @Field("user_name") String userName,
                                @Field("user_second_name") String userSecondName,
                                @Field("user_city") String userCity);


    /**
     * Deletes existing user data from remote database
     * @param userId long
     * @return Call<UserResponse>
     */
    @FormUrlEncoded
    @POST("task_network/delete.php")
    Call<UserResponse> deleteUser(@Field("user_id") long userId);
}
