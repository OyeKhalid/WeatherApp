package com.example.weatherapp.data

import com.example.weatherapp.data.model.CurrentWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather?")

    suspend fun getCurrentWeather(
        @Query("q") city : String,
        @Query("appid") apiKey : String,
        @Query("units") unit : String
    ) : Response<CurrentWeather>
}