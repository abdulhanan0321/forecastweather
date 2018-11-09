package com.example.abdulhanan.forecastweather;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abdulhanan.forecastweather.database.WeatherEntry;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private List<WeatherEntry> weatherEntryList = new ArrayList<>();



    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weather_list,viewGroup,false);

        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder weatherViewHolder, int i) {

        WeatherEntry weather = weatherEntryList.get(i);
        weatherViewHolder.daytext.setText(weather.getDay());
        weatherViewHolder.daytext.setTextColor(Color.parseColor("#ffffff"));

        weatherViewHolder.daydesc.setText(weather.getDescription());
        weatherViewHolder.daydesc.setTextColor(Color.parseColor("#ffffff"));

        weatherViewHolder.daytemp.setText(String.valueOf(weather.getTemp()));
        weatherViewHolder.daytemp.setTextColor(Color.parseColor("#ffffff"));

        Picasso.get()
                .load("http://openweathermap.org/img/w/" + weather.getImg() + ".png")
                .into(weatherViewHolder.imageview);

    }

    @Override
    public int getItemCount() {
        return weatherEntryList.size();
    }

    public void setWeather(List<WeatherEntry> weatherEntries){
        this.weatherEntryList= weatherEntries;
        notifyDataSetChanged();
    }

   // public WeatherEntry getWeatherAt(int position){
    //    return weatherEntryList.get(position);
   // }

    class WeatherViewHolder extends RecyclerView.ViewHolder{

        private TextView daytext, daydesc, daytemp;
        private ImageView imageview;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            daytext = itemView.findViewById(R.id.text_view_day);
            daytemp = itemView.findViewById(R.id.text_view_temp);
            daydesc = itemView.findViewById(R.id.text_view_desc);
            imageview = itemView.findViewById(R.id.imageview);

            Typeface a = Typeface.createFromAsset(itemView.getContext().getAssets(),"Helvetica-Thin.ttf");
            this.daytemp.setTypeface(a);
            this.daytext.setTypeface(a);
            this.daydesc.setTypeface(a);

        }
    }
}
