package com.udacity.asteroidradar.remote

import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.end_date
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.api.start_date
import com.udacity.asteroidradar.database.AsteroidDAO
import com.udacity.asteroidradar.database.AsteroidDataBase
import com.udacity.asteroidradar.model.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class RemoteRepositoryImp(private val api : ServiceAPI) : RemoteRepository{



    override suspend fun getData(): List<Asteroid> {
        return withContext(Dispatchers.IO) {
            val result = api.getData(start_date, end_date,
                Constants.api_key
            )

            val list = parseAsteroidsJsonResult(result)

            return@withContext list
        }
    }

    override suspend fun getPictureOfTheDay(): PictureOfDay {
        return  withContext(Dispatchers.IO){
           return@withContext ServiceAPI.AsteroidAPI.retroService.getPictureOfTheDay(Constants.api_key)
        }

    }




}