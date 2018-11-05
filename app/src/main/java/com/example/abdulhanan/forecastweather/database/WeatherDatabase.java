package com.example.abdulhanan.forecastweather.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.abdulhanan.forecastweather.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;

import static com.example.abdulhanan.forecastweather.MainActivity.day;
import static com.example.abdulhanan.forecastweather.MainActivity.desc;
import static com.example.abdulhanan.forecastweather.MainActivity.temp;

@Database(entities = {WeatherEntry.class}, version = 3)

public abstract class WeatherDatabase extends RoomDatabase{



    private static WeatherDatabase instance;
    public abstract WeatherDao weatherDao();

    public static synchronized WeatherDatabase getInstance(Context context)
    {
        if(instance==null)
        {
            instance=Room.databaseBuilder(context.getApplicationContext(),WeatherDatabase.class,"weather_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


}
