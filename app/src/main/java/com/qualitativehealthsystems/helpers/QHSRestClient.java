package com.qualitativehealthsystems.helpers;

import com.qualitativehealthsystems.interfaces.QHSInterface;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by bryan on 11/21/2015.
 */
public class QHSRestClient {

    private static QHSInterface REST_CLIENT;
    private static String ROOT = "http://23.229.20.102";

    static {
        setupRestClient();
    }

    private QHSRestClient() {}

    public static QHSInterface get() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {
        RestAdapter.Builder builder = new RestAdapter.Builder().setServer(ROOT);
        builder.setClient(new OkClient(new OkHttpClient()));
        RestAdapter restAdapter = builder.build();
        REST_CLIENT = restAdapter.create(QHSInterface.class);
    }
}