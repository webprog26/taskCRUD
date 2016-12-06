package com.example.webprog26.taskcrud.clients;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by webprog26 on 28.11.2016.
 */

public class ApiClient {

    /**
     * Simple client to create Retrofit instance with API Url
     */

    public static final String BASE_URL = "http://203855.urock.web.hosting-test.net/";
    private static Retrofit instance = null;

    /**
     * Creates Retrofit instance
     * @return instance Retrofit
     */
    public static Retrofit getClient(){
        if(instance == null){
            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }
}
