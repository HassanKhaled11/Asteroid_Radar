package com.udacity.asteroidradar.remote

import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.model.Asteroid
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceAPI {

@GET("neo/rest/v1/feed")
   suspend fun getData(
    @Query("start_date") startDate : String,
    @Query("end_date") endDate : String,
    @Query("api_key") apiKey : String): String


@GET("planetary/apod")
  suspend fun getPictureOfTheDay(
    @Query("api_key") apiKey: String):PictureOfDay



    object AsteroidAPI
    {
        val retroService : ServiceAPI by lazy {
            RetroBuilder.getRetrofitBuilder().create(ServiceAPI::class.java)
        }
    }




}