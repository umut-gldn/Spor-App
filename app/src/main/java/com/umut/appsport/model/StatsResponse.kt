package com.umut.appsport.model

data class StatsResponse(
    val response: List<PlayerStats>
)

data class PlayerStats(
    val player: Player,
    val statistics: List<Statistic>
)

data class Player(
    val name: String,
    val photo: String
)

data class Statistic(
    val team: PlayerTeam,
    val goals: PlayerGoals?,
    val assists: PlayerAssists?
)

data class PlayerTeam(
    val id: Int,
    val name: String,
    val logo: String
)

data class PlayerGoals(
    val total: Int?,
    val assists: Int?
)

data class PlayerAssists(
    val total: Int?
)
