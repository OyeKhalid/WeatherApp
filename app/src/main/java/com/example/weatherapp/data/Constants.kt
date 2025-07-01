package com.example.weatherapp.data

class Constants{
    companion object My_Constants {
        const val City_Name : String = ""
        const val Api_Key : String = "9ed4f01b2f231014cf90952bfe26ef22"
        const val Units : String = "metric"

        fun createInstance(): Constants{
            return Constants()
        }
    }
}