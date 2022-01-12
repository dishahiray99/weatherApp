package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<WeatherDetails> weatherDetailsList;
    Context context;
    View view;
    String urls = "https://openweathermap.org/img/wn/10d@2x.png";
    String details;


    public MyAdapter(Context context, ArrayList<WeatherDetails> weatherDetailsList  ){
        this.weatherDetailsList = weatherDetailsList;
        this.context = context;


    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.weather_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        WeatherDetails weatherDetail = weatherDetailsList.get(position);
        holder.tempText.setText(String.valueOf(weatherDetailsList.get(position).getMain().getTemp()));
        holder.cityText.setText(weatherDetailsList.get(position).getName());
        holder.countryText.setText(weatherDetailsList.get(position).getSys().getCountry());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(context)
                .load(urls)
                .apply(options)
                .into(holder.imageView);

        holder.input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeatherDetails weatherDetails = weatherDetailsList.get(position);
                Intent intent = new Intent(context,SecondActivity.class);
                intent.putExtra("details",weatherDetails.toString());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return weatherDetailsList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tempText;
        TextView cityText;
        TextView countryText;
        ConstraintLayout input;
        TextView textTemp;
        private ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tempText = itemView.findViewById(R.id.tempText);
            cityText = itemView.findViewById(R.id.cityText);
            countryText = itemView.findViewById(R.id.countryText);
            imageView = itemView.findViewById(R.id.imageView);
            input = itemView.findViewById(R.id.input);
            textTemp = itemView.findViewById(R.id.textTemp);
        }
    }


}
