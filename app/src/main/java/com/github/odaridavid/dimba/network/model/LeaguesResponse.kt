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
data class LeaguesResponse(val api: LeaguesApiResult)

data class LeaguesApiResult(val results: Int, val leagues: List<LeagueResponse>)

data class LeagueResponse(
    @field:Json(name = "league_id") val leagueId: Int,
    val name: String,
    val type: String,
    val country: String,
    @field:Json(name = "country_code") val countryCode: String?,
    val season: Int,
    @field:Json(name = "season_start") val seasonStart: String,
    @field:Json(name = "season_end") val seasonEnd: String,
    val logo: String?,
    val flag: String?,
    val standings: Int,
    @field:Json(name = "is_current") val isCurrent: Int,
    val coverage: CoverageResponse
)

data class CoverageResponse(
    val standings: Boolean,
    val fixtures: FixturesResponse,
    val players: Boolean,
    val topScorers: Boolean,
    val predictions: Boolean,
    val odds: Boolean
)

data class FixturesResponse(
    val events: Boolean,
    val lineups: Boolean,
    val statistics: Boolean,
    @field:Json(name = "player_statistics") val playersStatistics: Boolean
)