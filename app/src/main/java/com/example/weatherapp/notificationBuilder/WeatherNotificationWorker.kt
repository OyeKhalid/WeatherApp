package com.example.weatherapp.notificationBuilder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.example.weatherapp.services.ForegroundWeatherService

class WeatherAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val cityName = intent?.getStringExtra("cityName") ?: return

        val serviceIntent = Intent(context, ForegroundWeatherService::class.java).apply {
            putExtra("cityName", cityName)
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(context,serviceIntent)
        } else {
            context.startService(serviceIntent)
        }
    }
}
