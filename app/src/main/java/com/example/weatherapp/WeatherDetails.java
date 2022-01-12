package com.example.weatherapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherDetails {
    @SerializedName("main")
    Main main;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    @SerializedName("sys")
    Sys sys;

    @SerializedName("name")
    String name;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    @Override
    public String toString() {
        return "     Weather Details " + '\n'+
                '\n' +
                " City Name : " + name + '\n' +
                " Temperature : " + main.getTemp() +"\n"+
                " Country name : " + sys.getCountry() +"\n";
    }
}
