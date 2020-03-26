package com.github.odaridavid.dimba.network.model

import com.squareup.moshi.Json

/**
 *
 * Copyright 2020 David Odari
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *            http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 **/
data class StandingsResponse(val api: StandingsApiResult)

data class StandingsApiResult(val results: Int, val standings: List<List<TeamStandingResponse>>)

data class TeamStandingResponse(
    val rank: Int,
    @field:Json(name = "team_id") val teamId: Int?,
    val teamName: String,
    val logo: String,
    val group: String?,
    @field:Json(name = "forme") val form: String?,
    val status: String?,
    val description: String?,
    val all: AllMatchesResponse,
    val home: HomeMatchesResponse,
    val away: AwayMatchesResponse,
    val goalsDiff: Int,
    val points: Int,
    val lastUpdate: String
)

class AwayMatchesResponse(
    matchesPlayed: Int,
    win: Int,
    draw: Int,
    lose: Int,
    goalsFor: Int,
    goalsAgainst: Int
) : MatchesResponse(matchesPlayed, win, draw, lose, goalsFor, goalsAgainst)

class HomeMatchesResponse(
    matchesPlayed: Int,
    win: Int,
    draw: Int,
    lose: Int,
    goalsFor: Int,
    goalsAgainst: Int
) : MatchesResponse(matchesPlayed, win, draw, lose, goalsFor, goalsAgainst)

class AllMatchesResponse(
    matchesPlayed: Int,
    win: Int,
    draw: Int,
    lose: Int,
    goalsFor: Int,
    goalsAgainst: Int
) : MatchesResponse(matchesPlayed, win, draw, lose, goalsFor, goalsAgainst)

open class MatchesResponse(
    @field:Json(name = "matchsPlayed") val matchesPlayed: Int,
    val win: Int,
    val draw: Int,
    val lose: Int,
    val goalsFor: Int,
    val goalsAgainst: Int
)