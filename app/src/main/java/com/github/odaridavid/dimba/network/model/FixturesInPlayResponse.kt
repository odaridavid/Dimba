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
data class FixturesInPlayResponse(val api: FixturesInPlayApiResult)

data class FixturesInPlayApiResult(val results: Int, val fixtures: List<LiveFixturesResponse>)

data class LiveFixturesResponse(
    @field:Json(name = "fixture_id") val fixtureId: Int,
    @field:Json(name = "league_id") val leagueId: Int,
    val league: LiveFixtureLeagueResponse,
    @field:Json(name = "event_date") val eventDate: String,
    @field:Json(name = "event_timestamp") val eventTimestamp: Long,
    val firstHalfStart: Long?,
    val secondHalfStart: Long?,
    val round: String,
    val status: String,
    val statusShort: String,
    val elapsed: Int?,
    val venue: String,
    val referee: String?,
    val homeTeam: TeamResponse,
    val awayTeam: TeamResponse,
    val goalsHomeTeam: Byte,
    val goalsAwayTeam: Byte,
    val score: ScoresResponse,
    val events: List<EventsResponse>
)

data class LiveFixtureLeagueResponse(
    val name: String,
    val country: String,
    val logo: String?,
    val flag: String?
)

data class TeamResponse(
    @field:Json(name = "team_id") val id: Int,
    @field:Json(name = "team_name") val name: String,
    val logo: String
)

data class ScoresResponse(
    @field:Json(name = "halftime") val halfTime: String,
    @field:Json(name = "fulltime") val fullTime: String?,
    @field:Json(name = "extratime") val extraTime: String?,
    val penalty: String?
)

data class EventsResponse(
    val elapsed: Int?,
    @field:Json(name = "elapsed_plus") val elapsedPlus: Int?,
    @field:Json(name = "team_id") val teamId: Int,
    val teamName: String,
    @field:Json(name = "player_id") val playerId: Int?,
    val player: String?,
    @field:Json(name = "assist_id") val assistId: Int?,
    val assist: String?,
    val type: String,
    val detail: String,
    val comments: String?
)