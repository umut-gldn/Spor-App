package com.umut.appsport.model

data class FixtureResponse(
    val response: List<Fixture>
)

data class Fixture(
    val fixture: FixtureDetails,
    val teams: Teams,
    val goals: Goals,
    val score: Score
)

data class FixtureDetails(
    val timestamp: Long,
    val referee: String?
)

data class Teams(
    val home: Team,
    val away: Team
)

data class Team(
    val name: String,
    val logo: String
)

data class Goals(
    val home: Int?,
    val away: Int?
)

data class Score(
    val halftime: HalftimeScore?
)

data class HalftimeScore(
    val home: Int?,
    val away: Int?
)
