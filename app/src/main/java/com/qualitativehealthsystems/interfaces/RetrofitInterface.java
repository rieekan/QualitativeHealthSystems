package com.qualitativehealthsystems.interfaces;

import com.qualitativehealthsystems.models.User;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by bryan on 11/21/2015.
 */
public interface RetrofitInterface {

    // asynchronously with a callback
    @GET("/api/user")
    User getUser(@Query("user_id") int userId, Callback<User> callback);

    // synchronously
    @POST("/api/user/register")
    User registerUser(@Body User user);
}
