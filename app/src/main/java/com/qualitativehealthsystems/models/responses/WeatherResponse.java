package com.qualitativehealthsystems.models.responses;

import com.qualitativehealthsystems.models.Weather;

/**
 * Created by bryan on 11/21/2015.
 */
public class WeatherResponse {
    public int cod;
    public String base;
    public main main;
    public Weather[] weather;

    // default constructor, getters and setters
    public Weather getWeather() {
        return weather[0];
    }

    public class main {
        public String temp;
        public String pressure;
        public String humidity;
        public String temp_min;
        public String temp_max;
        public String sea_level;
        public String grnd_level;
    }
}
