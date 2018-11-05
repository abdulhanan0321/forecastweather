package com.example.abdulhanan.forecastweather.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "weather_table")
public class WeatherEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String day;

    private String description;

    private String temp;

    private String img;



    public WeatherEntry(String day, String description, String temp, String img) {
        this.day = day;
        this.description = description;
        this.temp = temp;
        this.img = img;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTemp() {
        return temp;
    }

    public void setImg(String img) {this.img=img; }

    public String getImg() {return  img;}
}
