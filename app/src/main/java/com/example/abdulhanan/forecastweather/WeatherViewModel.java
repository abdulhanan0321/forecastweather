package com.example.abdulhanan.forecastweather;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.abdulhanan.forecastweather.database.WeatherEntry;
import com.example.abdulhanan.forecastweather.database.WeatherRespository;

import java.util.List;

public class WeatherViewModel extends AndroidViewModel {

    private WeatherRespository respository;
    private LiveData<List<WeatherEntry>> allWeather;


    public WeatherViewModel(@NonNull Application application) {
        super(application);

        respository = new WeatherRespository(application);
        allWeather = respository.getAllWeather();
    }

    public void insert(WeatherEntry weatherEntry){
        respository.insert(weatherEntry);
    }

    public void update(WeatherEntry weatherEntry){
        respository.update(weatherEntry);
    }

    public void delete(WeatherEntry weatherEntry){
        respository.delete(weatherEntry);
    }

    public void deleteAllWeather(){
        respository.deleteAllWeather();
    }

    public LiveData<List<WeatherEntry>> getAllWeather(){
        return allWeather;
    }
}
