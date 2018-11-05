package com.example.abdulhanan.forecastweather;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.abdulhanan.forecastweather.database.WeatherDao;
import com.example.abdulhanan.forecastweather.database.WeatherDatabase;
import com.example.abdulhanan.forecastweather.database.WeatherEntry;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static WeatherViewModel weatherViewModel;
    RecyclerView recyclerView;

    private List<WeatherEntry> weatherList;
    WeatherAdapter adapter = new WeatherAdapter();

    static String WEATHER_KEY = "2675d734e1064430ee393c9693e122b5";

    static String cityname = "London";


    static public String day,desc;
    static public int temp;
    FloatingActionButton btn;
    Toolbar toolbar;
    MaterialSearchView materialSearchView;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn = findViewById(R.id.btn);
        btn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download(cityname);
                //Toast.makeText(MainActivity.this,"updated",Toast.LENGTH_LONG).show();
            }
        });

        name = findViewById(R.id.cityname);
        Typeface t = Typeface.createFromAsset(this.getAssets(),"Helvetica-Thin.ttf");
        name.setTypeface(t);
        name.setText(cityname);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Weather");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        materialSearchView = findViewById(R.id.searchview);
        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                cityname =query.toUpperCase();
                download(cityname);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        recyclerView.setAdapter(adapter);





        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        weatherViewModel.getAllWeather().observe(this, new Observer<List<WeatherEntry>>() {
            @Override
            public void onChanged(@Nullable final List<WeatherEntry> weatherEntries) {

                adapter.setWeather(weatherEntries);

            }
        }

        );


        download(cityname);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.searchhere);
        materialSearchView.setMenuItem(item);
        return true;
    }


    public void  download(String query){

        if (Funtionality.isNetworkAvailable(this)){
            weatherViewModel.deleteAllWeather();
            name.setText(cityname);
            DownloadWeather task = new DownloadWeather();
            task.execute(query);
        } else {
            Toast.makeText(this,"No Connection",Toast.LENGTH_LONG).show();
        }

    }


    static class DownloadWeather extends AsyncTask<String,Void, String> {


        @Override
        protected String doInBackground(String... args) {
            String link = Funtionality.excuteGet("http://api.openweathermap.org/data/2.5/forecast?q=" + args[0] +
                    "&units=metric&appid=" + WEATHER_KEY);
            return link;
        }

        @Override
        protected void onPostExecute(String link) {

            try{
                JSONObject json = new JSONObject(link);
                if (json!=null)
                {



                   // setcityname.setText(json.getJSONObject("city").getString("name")+","+json.getJSONObject("city").getString("country"));
                    JSONObject detail_d1 = json.getJSONArray("list").getJSONObject(0).getJSONObject("main");
                    JSONObject time_d1 = json.getJSONArray("list").getJSONObject(0);
                    JSONObject desc_d1 = json.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0);
                    String daytempmax1 =(String.format("%.0f",detail_d1.getDouble("temp_max")) + "°");

                    //daytempmin1.setText(String.format("%.0f",detail_d1.getDouble("temp_min")) + "°");
                    //DateFormat a = DateFormat.getDateInstance();
                    String daytime1=("Today");
                    String daydesc1=(desc_d1.getString("description"));
                    String icon11 = desc_d1.getString("icon");
                    //String iconUrl11 = "http://openweathermap.org/img/w/" + icon11 + ".png";
                    //Picasso.get().load(iconUrl11).into(dayimg1);


                    WeatherEntry weatherEntry = new WeatherEntry(daytime1,daydesc1,daytempmax1,icon11);
                    weatherViewModel.insert(weatherEntry);


                    JSONObject detail_d2 = json.getJSONArray("list").getJSONObject(8).getJSONObject("main");
                    JSONObject time_d2 = json.getJSONArray("list").getJSONObject(8);
                    JSONObject desc_d2 = json.getJSONArray("list").getJSONObject(8).getJSONArray("weather").getJSONObject(0);
                    String daytempmax2=(String.format("%.0f", detail_d2.getDouble("temp_max")) + "°");
                   // daytempmin2.setText(String.format("%.0f",detail_d2.getDouble("temp_min")) + "°");
                    DateFormat b = DateFormat.getDateInstance();
                    String daytime2=(b.format(new Date(time_d2.getLong("dt") * 1000)));
                    String daydesc2=(desc_d2.getString("description"));
                    String icon22 = desc_d2.getString("icon");
                    //String iconUrl22 = "http://openweathermap.org/img/w/" + icon22 + ".png";
                    //Picasso.get().load(iconUrl22).into(dayimg2);

                    WeatherEntry weatherEntry2 = new WeatherEntry(daytime2,daydesc2,daytempmax2,icon22);
                    weatherViewModel.insert(weatherEntry2);


                    JSONObject detail_d3 = json.getJSONArray("list").getJSONObject(16).getJSONObject("main");
                    JSONObject time_d3 = json.getJSONArray("list").getJSONObject(16);
                    JSONObject desc_d3 = json.getJSONArray("list").getJSONObject(16).getJSONArray("weather").getJSONObject(0);
                    String daytempmax3=(String.format("%.0f", detail_d3.getDouble("temp_max")) + "°");
                    //daytempmin3.setText(String.format("%.0f",detail_d3.getDouble("temp_min")) + "°");
                    DateFormat c = DateFormat.getDateInstance();
                    String daytime3=(c.format(new Date(time_d3.getLong("dt") * 1000)));
                    String daydesc3=(desc_d3.getString("description"));
                    String icon33 = desc_d3.getString("icon");
                    //String iconUrl33 = "http://openweathermap.org/img/w/" + icon33 + ".png";
                    //Picasso.get().load(iconUrl33).into(dayimg3);

                    WeatherEntry weatherEntry3 = new WeatherEntry(daytime3,daydesc3,daytempmax3,icon33);
                    weatherViewModel.insert(weatherEntry3);

                    JSONObject detail_d4 = json.getJSONArray("list").getJSONObject(24).getJSONObject("main");
                    JSONObject time_d4 = json.getJSONArray("list").getJSONObject(24);
                    JSONObject desc_d4 = json.getJSONArray("list").getJSONObject(24).getJSONArray("weather").getJSONObject(0);
                    String daytempmax4=(String.format("%.0f", detail_d4.getDouble("temp_max")) + "°");
                    //daytempmin4.setText(String.format("%.0f",detail_d4.getDouble("temp_min")) + "°");
                    DateFormat d = DateFormat.getDateInstance();
                    String daytime4=(d.format(new Date(time_d4.getLong("dt") * 1000)));
                    String daydesc4=(desc_d4.getString("description"));
                    String icon44 = desc_d4.getString("icon");
                    //String iconUrl44 = "http://openweathermap.org/img/w/" + icon44 + ".png";
                    //Picasso.get().load(iconUrl44).into(dayimg4);

                    WeatherEntry weatherEntry4 = new WeatherEntry(daytime4,daydesc4,daytempmax4,icon44);
                    weatherViewModel.insert(weatherEntry4);

                    JSONObject detail_d5 = json.getJSONArray("list").getJSONObject(32).getJSONObject("main");
                    JSONObject time_d5 = json.getJSONArray("list").getJSONObject(32);
                    JSONObject desc_d5 = json.getJSONArray("list").getJSONObject(32).getJSONArray("weather").getJSONObject(0);
                    String daytempmax5=(String.format("%.0f", detail_d5.getDouble("temp_max")) + "°");
                    //daytempmin5.setText(String.format("%.0f",detail_d5.getDouble("temp_min")) + "°");
                    DateFormat g = DateFormat.getDateInstance();
                    String daytime5=(g.format(new Date(time_d5.getLong("dt") * 1000)));
                    String daydesc5=(desc_d5.getString("description"));
                    String icon55 = desc_d5.getString("icon");
                    //String iconUrl55 = "http://openweathermap.org/img/w/" + icon55 + ".png";
                    //Picasso.get().load(iconUrl55).into(dayimg5);

                    WeatherEntry weatherEntry5 = new WeatherEntry(daytime5,daydesc5,daytempmax5,icon55);
                    weatherViewModel.insert(weatherEntry5);

                }
            } catch (JSONException e) {
                //Toast.makeText(getContext(), "Error, Check City", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
