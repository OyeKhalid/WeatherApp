package com.example.weatherapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.Constants
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.repo.WeatherRepo
import com.example.weatherapp.viewModel.WeatherViewModel
//import com.example.weatherapp.viewmodel.WeatherViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val weatherViewModel : WeatherViewModel by viewModels()
    //private lateinit var weatherViewModel : WeatherViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val repository = WeatherRepo()
//        val viewModelFactory = WeatherViewModelFactory(repository)
//        weatherViewModel = ViewModelProvider(this, viewModelFactory)[WeatherViewModel::class.java]


        observeWeather()

        binding.btnSearch.setOnClickListener {
            val cityName = binding.etCity.text.toString()

            if (cityName.isNotEmpty()){
                binding.progressBar.visibility = android.view.View.VISIBLE
                binding.weatherDataLayout.visibility = android.view.View.GONE

                weatherViewModel.fetchWeather(cityName,Constants.Api_Key,Constants.Units)
            }
            else{
                Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun observeWeather() {
        weatherViewModel.weather.observe(this) { response ->
            binding.progressBar.visibility = android.view.View.GONE
            if (response != null && response.isSuccessful) {
                val weather = response.body()
                binding.weatherDataLayout.visibility = android.view.View.VISIBLE
                binding.tvTemperature.text = "${weather?.main?.temp?.toInt()} °C"
                binding.tvCity.text = weather?.name?: "N/A"
            } else {
                binding.weatherDataLayout.visibility = android.view.View.GONE
                Toast.makeText(this, "The City Name Is Not Correct", Toast.LENGTH_SHORT).show()
            }
        }
    }
}