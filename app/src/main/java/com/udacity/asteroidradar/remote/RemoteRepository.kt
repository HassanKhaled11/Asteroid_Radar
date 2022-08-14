package com.udacity.asteroidradar.remote

import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.model.Asteroid
import retrofit2.Call
import retrofit2.Response

interface RemoteRepository {

    suspend fun getData(): List<Asteroid>
    suspend fun getPictureOfTheDay() : PictureOfDay

}