package com.umut.appsport.model

data class StandingResponse(
    val response: List<LeagueResponse>
)

data class LeagueResponse(
    val league: League
)

data class League(
    val standings: List<List<Standing>>
)

data class Standing(
    val rank: Int,
    val team: TeamInfo,
    val points: Int,
    val goalsDiff: Int,
    val group: String,
    val all: MatchStats,
)

data class TeamInfo(
    val name: String,
    val logo: String
)

data class MatchStats(
    val played: Int,
    val win: Int,
    val draw: Int,
    val lose: Int,
    val goals: TeamGoals
)

data class TeamGoals(
    val `for`: Int,
    val against: Int
)
