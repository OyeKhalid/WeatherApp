package com.example.weatherapp.repo

import com.example.weatherapp.di.RetrofitInstance

class WeatherRepo {
    suspend fun getCurrentWeather(city: String, apiKey: String, units: String) =
        RetrofitInstance.api.getCurrentWeather(city, apiKey, units)
}
