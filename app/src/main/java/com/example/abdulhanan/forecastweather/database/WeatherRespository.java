package com.example.abdulhanan.forecastweather.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.Date;
import java.util.List;

public class WeatherRespository {

    private WeatherDao weatherDao;
    private LiveData<List<WeatherEntry>> allWeather;

    public WeatherRespository(Application application) {

        WeatherDatabase database = WeatherDatabase.getInstance(application);
        weatherDao = database.weatherDao();
        allWeather = weatherDao.getAllWeather();

    }

    public void insert(WeatherEntry weatherEntry){

        new InsertWeatherAsynkTask(weatherDao).execute(weatherEntry);
    }

    public void update(WeatherEntry weatherEntry){

        new UpdateWeatherAsynkTask(weatherDao).execute(weatherEntry);
    }

    public void delete(WeatherEntry weatherEntry){

        new DeleteWeatherAsynkTask(weatherDao).execute(weatherEntry);
    }

    public void deleteAllWeather(){

        new DeleteAllWeatherAsynkTask(weatherDao).execute();
    }

    public LiveData<List<WeatherEntry>> getAllWeather(){
        return allWeather;
    }

    public static class InsertWeatherAsynkTask extends AsyncTask<WeatherEntry,Void,Void>{

        private WeatherDao weatherDao;

        private InsertWeatherAsynkTask(WeatherDao weatherDao){
            this.weatherDao=weatherDao;
        }

        @Override
        protected Void doInBackground(WeatherEntry... weatherEntries) {
            weatherDao.insert(weatherEntries[0]);
            return null;
        }
    }

    public static class UpdateWeatherAsynkTask extends AsyncTask<WeatherEntry,Void,Void>{

        private WeatherDao weatherDao;

        private UpdateWeatherAsynkTask(WeatherDao weatherDao){
            this.weatherDao=weatherDao;
        }

        @Override
        protected Void doInBackground(WeatherEntry... weatherEntries) {
            weatherDao.update(weatherEntries[0]);
            return null;
        }
    }

    public static class DeleteWeatherAsynkTask extends AsyncTask<WeatherEntry,Void,Void>{

        private WeatherDao weatherDao;

        private DeleteWeatherAsynkTask(WeatherDao weatherDao){
            this.weatherDao=weatherDao;
        }

        @Override
        protected Void doInBackground(WeatherEntry... weatherEntries) {
            weatherDao.delete(weatherEntries[0]);
            return null;
        }
    }

    public static class DeleteAllWeatherAsynkTask extends AsyncTask<Void,Void,Void>{

        private WeatherDao weatherDao;

        private DeleteAllWeatherAsynkTask(WeatherDao weatherDao){
            this.weatherDao=weatherDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            weatherDao.deleteAllWeather();
            return null;
        }
    }
}
