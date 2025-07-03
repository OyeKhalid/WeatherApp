package com.example.weatherapp.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.weatherapp.R

object NotificationUtils {

    fun showWeatherUpdatedNotification(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "weather_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Weather Updates",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Weather Updated")
            .setContentText("Latest weather info is now available.")
            .setSmallIcon(R.drawable.cloude) // Replace with your own icon
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1001, notification)
    }
}
