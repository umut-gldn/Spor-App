package com.umut.appsport.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object  APIClient {

    private const val  BASE_URL="https://v3.football.api-sports.io/"
    private const val API_KEY=""

    private val client= OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request=chain.request().newBuilder()
                .addHeader("x-apisports-key",API_KEY)
                .build()
            chain.proceed(request)
        }
        .build()

    val retrofit:Retrofit= Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}