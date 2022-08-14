package com.udacity.asteroidradar.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.model.Asteroid

interface LocalRepository {


    suspend fun insertAsteroids(asteroidlist: List<Asteroid>)

    suspend fun deleteAsteroids()

    suspend fun getSavedAsteroids():List<Asteroid>

    suspend fun getWeekAsteroids():List<Asteroid>

    suspend fun getTodayAsteroids():List<Asteroid>


}