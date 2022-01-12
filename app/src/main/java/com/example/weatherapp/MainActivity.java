package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    MyAdapter adapter;
    ArrayList<WeatherDetails> weatherDetailsList;
    RecyclerView recyclerView;
    public static String baseUrl ="https://api.openweathermap.org//data/2.5/";
    private static String appId = "3081158e0f99954927b3ab3c2a8aee8d";
    private static String name = "London";
    TextView tempText,cityText, countryText;
    EditText editText;
    Button button, buttonSort;
    String cityName;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        tempText = findViewById(R.id.tempText);
        cityText = findViewById(R.id.cityText);
        countryText = findViewById(R.id.countryText);
        button = findViewById(R.id.button);
        weatherDetailsList = new ArrayList<>();

        buttonSort = findViewById(R.id.buttonSort);
        buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sortArrayList();
                removeDuplicateCities();
            }
        });
        createRecyclerView();
    }


    public void getCurrentWeatherData(View v){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonApiWeather apiWeatherService = retrofit.create(JsonApiWeather.class);


            cityName = editText.getText().toString();

            Call<WeatherDetails> call = apiWeatherService.getCurrentWeatherData(cityName, appId);

            call.enqueue(new Callback<WeatherDetails>() {
                @Override
                public void onResponse(Call<WeatherDetails> call, Response<WeatherDetails> response) {
                    if (response.code() == 404) {
                        Toast.makeText(MainActivity.this, "enter valid city", Toast.LENGTH_LONG);
                    } else if (!(response.isSuccessful())) {
                        Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_LONG);
                    }

                    WeatherDetails details = response.body();

                    weatherDetailsList.add(details);

                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<WeatherDetails> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "failed to retrieve data\ncheck your internet connection", Toast.LENGTH_LONG).show();
                }
            });
        }



   private void createRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        adapter = new MyAdapter(this, weatherDetailsList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
    }




    private void sortArrayList(){
        Collections.sort(weatherDetailsList, new Comparator<WeatherDetails>() {
            @Override
            public int compare(WeatherDetails weatherDetails, WeatherDetails t1) {
                return weatherDetails.getName().compareTo(t1.getName());
            }
        });
        adapter.notifyDataSetChanged();
    }


    private void removeDuplicateCities(){
        for(int cities= 0; cities < weatherDetailsList.size(); cities++){
            for(int city= cities + 1; city < weatherDetailsList.size(); city++){
                if(weatherDetailsList.get(cities).getName().equals(weatherDetailsList.get(city).getName())){
                    weatherDetailsList.remove(city);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
}