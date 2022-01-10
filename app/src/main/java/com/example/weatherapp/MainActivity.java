package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    MyAdapter adapter;
    ArrayList<WeatherDetails> weatherDetailsList;
    ArrayList<String> cityNameList = new ArrayList<String>();
    RecyclerView recyclerView;
    public static String baseUrl ="api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";
    private static String appId = "3081158e0f99954927b3ab3c2a8aee8d";
    private static String name = "London";
    TextView tempText,cityText, countryText;
    EditText editText;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //editText = findViewById(R.id.editText);
        tempText = findViewById(R.id.tempText);
        cityText = findViewById(R.id.cityText);
        countryText = findViewById(R.id.countryText);
        addCitiesToTheList();
        getCurrentWeatherData();

    }



    public void getCurrentWeatherData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org//data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonApiWeather apiWeatherService = retrofit.create(JsonApiWeather.class);

        for (int i =0; i<cityNameList.size(); i++) {


            Call<WeatherDetails> call = apiWeatherService.getCurrentWeatherData("Nagpur", appId);
            call.enqueue(new Callback<WeatherDetails>() {
                @Override
                public void onResponse(Call<WeatherDetails> call, Response<WeatherDetails> response) {
                    if (response.code() == 404) {
                        Toast.makeText(MainActivity.this, "enter valid city", Toast.LENGTH_LONG);
                    } else if (!(response.isSuccessful())) {
                        Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_LONG);
                    }

                    WeatherDetails details = response.body();
//                /*Main main = details.getMain();
//                Double temp = main.getTemp();
//                Integer temperature = (int)(temp - 273.15);
//                tempText.setText(String.valueOf(temperature)+"c");
//                String city = details.getName();
//                cityText.setText(city);
//                Sys sys = details.getSys();
//                String country = sys.getCountry();
//                countryText.setText(country);*/

                    weatherDetailsList = new ArrayList<>();

                    weatherDetailsList.add(details);

                    createRecyclerView();

                }

                @Override
                public void onFailure(Call<WeatherDetails> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "failed to retrieve data\ncheck your internet connection", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

   private void createRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MyAdapter(this, weatherDetailsList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void addCitiesToTheList(){
        cityNameList.add("Nagpur");
        cityNameList.add("Mumbai");
        cityNameList.add("Pune");
    }
}