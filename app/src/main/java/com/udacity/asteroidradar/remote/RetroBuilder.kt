package com.udacity.asteroidradar.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetroBuilder  {

    companion object {
        private val moshi  = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        fun getRetrofitBuilder() : Retrofit {
         return Retrofit.Builder().baseUrl(BASE_URL)
             .addConverterFactory(ScalarsConverterFactory.create())
             .addConverterFactory(MoshiConverterFactory.create(moshi))
             .build()

        }
    }
}