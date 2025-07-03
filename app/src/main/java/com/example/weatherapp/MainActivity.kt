package com.example.weatherapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.data.Constants
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.services.ForegroundWeatherService
import com.example.weatherapp.viewModel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener {
            val cityName = binding.etCity.text.toString()

            if (cityName.isNotEmpty()) {
                binding.progressBar.visibility = android.view.View.VISIBLE
                binding.weatherDataLayout.visibility = android.view.View.GONE

                weatherViewModel.fetchWeather(cityName, Constants.Api_Key, Constants.Units)
                startRepeatingWeatherAlarm(this, cityName)

                // 🔥 Start foreground service to show weather notification every 2 min
                val serviceIntent = Intent(this, ForegroundWeatherService::class.java).apply {
                    putExtra("cityName", cityName)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(serviceIntent)
                } else {
                    startService(serviceIntent)
                }

            } else {
                Toast.makeText(applicationContext, "Please enter a city name", Toast.LENGTH_SHORT)
                    .show()
            }
        }



        observeWeather()
    }

    private fun observeWeather() {
        weatherViewModel.weather.observe(this) { response ->
            binding.progressBar.visibility = View.GONE
            if (response != null && response.isSuccessful) {
                val weather = response.body()
                binding.weatherDataLayout.visibility = View.VISIBLE
                binding.tvTemperature.text = "${weather?.main?.temp?.toInt()} °C"
                binding.tvCity.text = weather?.name ?: "N/A"
            } else {
                binding.weatherDataLayout.visibility = View.GONE
                Toast.makeText(this, "The City Name Is Not Correct", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun startRepeatingWeatherAlarm(context: Context, cityName: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, com.example.weatherapp.notificationBuilder.WeatherAlarmReceiver::class.java).apply {
            putExtra("cityName", cityName)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Start 2 minutes from now, repeat every 2 minutes
        val startTime = System.currentTimeMillis() + 1 * 60 * 1000
        val interval = 1 * 60 * 1000L

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            startTime,
            interval,
            pendingIntent
        )
    }

}
