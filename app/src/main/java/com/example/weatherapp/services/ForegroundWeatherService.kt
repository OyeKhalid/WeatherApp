package com.example.weatherapp.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.weatherapp.R
import kotlinx.coroutines.*
import java.util.*

class ForegroundWeatherService : Service() {

    private val channelId = "WeatherUpdateChannel"
    private val notificationId = 101
    private var cityName: String? = null
    private var timer: Timer? = null
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        cityName = intent?.getStringExtra("cityName")

        val notification = buildNotification("Starting weather updates for $cityName")
        startForeground(notificationId, notification)

// Now start the timer
        startRepeatingWeatherUpdates()

        return START_STICKY
    }

//    override fun onDestroy() {
//        timer?.cancel()
//        coroutineScope.cancel()
//        super.onDestroy()
//    }

    private fun startRepeatingWeatherUpdates() {
        val intervalMillis = 1 * 60 * 1000L // 2 minutes
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                coroutineScope.launch {
                    updateWeather()
                }
            }
        }, 0, intervalMillis)
    }

    private suspend fun updateWeather() {
        // Replace this with real API call
        val temp = (20..40).random() // simulate temp
        val notification = buildNotification("Updated: $temp°C in $cityName")
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)
        Log.d("ForegroundService", "Weather updated for $cityName")
    }

    private fun buildNotification(content: String): Notification {
        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Weather Update")
            .setContentText(content)
            .setSmallIcon(R.drawable.cloude) // use a valid icon
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Weather Updates",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Shows weather updates every 2 minutes"
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
