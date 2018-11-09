package com.example.abdulhanan.forecastweather;

import com.example.abdulhanan.forecastweather.data.ForcastList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class Api {

    private static final String key = "2675d734e1064430ee393c9693e122b5";
    private static final String url = "http://api.openweathermap.org";
    //private static final String url = "http://api.openweathermap.org/data/2.5/forecast/";

    public static PostService postService =null;

    public static PostService getPostService(){

        if(postService == null){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            postService = retrofit.create(PostService.class);
        }
        return postService;
    }

    public interface PostService{

        //@GET("?q=faisalabad&units=metric&appid=" + key)
        //Call<ForcastList> getForcastList();


        // @GET("?q={cityname}&units=metric&appid=" + key)
        // Call<ForcastList> getPostByName(@Path("cityname") String city);

        @GET("/data/2.5/forecast")
        Call<ForcastList> getPostByName(@Query("q") String city,
                                        @Query("units") String units,
                                        @Query("appid") String apikey);
    }

}
