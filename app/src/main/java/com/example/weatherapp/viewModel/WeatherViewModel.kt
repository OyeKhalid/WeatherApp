package com.example.weatherapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.CurrentWeather
import com.example.weatherapp.repo.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepo) :
    ViewModel() {

    private val _weather = MutableLiveData<Response<CurrentWeather>>()
    val weather: LiveData<Response<CurrentWeather>> = _weather

    fun fetchWeather(city: String, apiKey: String, units: String) {
        viewModelScope.launch {
            try {
                val response = repository.getCurrentWeather(city, apiKey, units)
                _weather.postValue(response)
            } catch (e: Exception) {
                _weather.postValue(null) // You can handle errors more cleanly
            }
        }
    }
}
