package com.qualitativehealthsystems.interfaces;

import com.qualitativehealthsystems.models.Poll;
import com.qualitativehealthsystems.models.User;
import com.qualitativehealthsystems.models.requests.AuthenticationRequest;
import com.qualitativehealthsystems.models.responses.AuthenticationResponse;
import com.qualitativehealthsystems.models.responses.PollResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;


public interface QHSInterface {

//    // asynchronously with a callback
//    @GET("/api/user")
//    User getUser(@Query("user_id") int userId, Callback<User> callback);
//
//    // synchronously
//    @POST("/api/user/register")
//    User registerUser(@Body User user);

    // asynchronously with a callback
    @POST("/api/user/login")
    void login(@Body AuthenticationRequest request, Callback<AuthenticationResponse> callback);

    ///api/Poll"
    // asynchronously with a callback
    @POST("/api/poll")
    void submitPoll(@Body Poll poll, Callback<PollResponse> callback);
}
