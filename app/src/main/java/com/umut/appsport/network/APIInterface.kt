package com.umut.appsport.network

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import com.umut.appsport.model.FixtureResponse
import com.umut.appsport.model.RoundsResponse
import com.umut.appsport.model.StandingResponse
import com.umut.appsport.model.StatsResponse


interface APIInterface {

    @GET("fixtures")
    fun getFixtures(
        @Query("league") league: String,
        @Query("season") season: String,
    ):Call<FixtureResponse>

    @GET("fixtures/rounds")
    fun getRounds(
        @Query("league") league: String,
        @Query("season") season: String
    ): Call<RoundsResponse>

    @GET("fixtures")
    fun getFixturesByRound(
        @Query("league") league: String,
        @Query("season") season: String,
        @Query("round") round: String
    ): Call<FixtureResponse>

   @GET("players/topscorers")
   fun getTopScorers(
       @Query("league") league: String,
       @Query("season") season: String,
   ): Call<StatsResponse>

    @GET("players/topassists")
    fun getTopAssists(
        @Query("league") league: String,
        @Query("season") season: String,
    ): Call<StatsResponse>

    @GET("standings")
    fun getStandings(
        @Query("league") leagueId: String,
        @Query("season") season: String
    ): Call<StandingResponse>
}