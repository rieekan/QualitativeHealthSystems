package com.qualitativehealthsystems.models;

import com.google.gson.Gson;

/**
 * Created by bryan on 11/21/2015.
 */
public class Weather {
    private int id;
    public String main;
    public String description;

    // default constructor, getters and setters
    public String getDescription() {
        return description;
    }


}
