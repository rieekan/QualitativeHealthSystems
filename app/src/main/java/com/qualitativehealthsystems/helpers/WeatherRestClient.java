package com.qualitativehealthsystems.helpers;

import com.qualitativehealthsystems.interfaces.WeatherInterface;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by bryan on 11/21/2015.
 */
public class WeatherRestClient {

    private static WeatherInterface REST_CLIENT;
    private static String ROOT = "http://api.openweathermap.org/data/2.5";

    static {
        setupRestClient();
    }

    private WeatherRestClient() {}

    public static WeatherInterface get() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {
        RestAdapter.Builder builder = new RestAdapter.Builder().setServer(ROOT);
        builder.setClient(new OkClient(new OkHttpClient()));
        RestAdapter restAdapter = builder.build();
        REST_CLIENT = restAdapter.create(WeatherInterface.class);
    }
}