package com.example.weatherapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {


    TextView textTemp;

    String details;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textTemp = findViewById(R.id.textTemp);


        getData();
        setData();
    }

    private void getData(){
        if(getIntent().hasExtra("details")){

            details = getIntent().getStringExtra("details");

        }else{
            Toast.makeText(this,"No Data" ,Toast.LENGTH_LONG).show();
        }
    }

    private void setData(){
        textTemp.setText(details);


    }
}
