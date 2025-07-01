package com.example.weatherapp.repo

import com.example.weatherapp.data.ApiInterface
import javax.inject.Inject

class WeatherRepo @Inject constructor(
    private val api: ApiInterface
) {
    suspend fun getCurrentWeather(city: String, apiKey: String, units: String) =
        api.getCurrentWeather(city, apiKey, units)
}

