package com.example.webprog26.taskcrud.interfaces;

import com.example.webprog26.taskcrud.models.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by webprog26 on 28.11.2016.
 */

public interface UserApiInterface {

    @GET("task_network/read.php")
    Call<UserResponse> getUsers();
}
