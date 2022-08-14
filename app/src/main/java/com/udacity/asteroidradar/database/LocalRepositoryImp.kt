package com.udacity.asteroidradar.database

import com.udacity.asteroidradar.model.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalRepositoryImp(private val db:AsteroidDataBase) : LocalRepository {


    override suspend fun insertAsteroids(asteroidlist: List<Asteroid>) {
       withContext(Dispatchers.IO){
           db.asteroidDAO().insertAsteroid(asteroidlist)
       }
    }

    override suspend fun deleteAsteroids() {
       db.asteroidDAO().deleteAsteroids()
     }


    override suspend fun getSavedAsteroids(): List<Asteroid> = withContext(Dispatchers.IO){
          db.asteroidDAO().getSavedAsteroids()
      }


    override suspend fun getWeekAsteroids(): List<Asteroid> = withContext(Dispatchers.IO)
    {db.asteroidDAO().getWeekAsteroids()}


    override suspend fun getTodayAsteroids(): List<Asteroid> = withContext(Dispatchers.IO)
    {db.asteroidDAO().getWeekAsteroids()}

}