package com.example.weatherapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonApiWeather {
    @GET("weather")
    Call<WeatherDetails> getCurrentWeatherData(
            @Query("q") String name,
            @Query("appid") String appId);


}
