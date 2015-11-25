package com.qualitativehealthsystems.interfaces;

import com.qualitativehealthsystems.models.responses.WeatherResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by bryan on 11/21/2015.
 */
public interface WeatherInterface {

    @GET("/weather")
    void getWeather(@Query("q") String cityName,
                    @Query("appid") String appId,
                    Callback<WeatherResponse> callback);
}