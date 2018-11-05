package com.example.abdulhanan.forecastweather.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import static android.icu.text.MessagePattern.ArgType.SELECT;
@Dao
public interface WeatherDao {


    @Insert
     void insert(WeatherEntry weatherEntry);

    @Update
    void update(WeatherEntry weatherEntry);

    @Delete
    void delete(WeatherEntry weatherEntry);

    @Query("DELETE FROM weather_table")
    void deleteAllWeather();

    @Query("SELECT * FROM weather_table")
    LiveData<List<WeatherEntry>> getAllWeather();
}
