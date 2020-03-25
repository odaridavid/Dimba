package com.github.odaridavid.dimba.models.fixtures

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
data class LiveFixture(
    val fixtureId: Int,
    val leagueId: Int,
    val leagueInfo: LeagueInfo,
    val eventDate: String,
    val eventTimestamp: Long,
    val firstHalfStart: Long,
    val secondHalfStart: Long,
    val round: String,
    val status: String,
    val statusShort: String,
    val elapsed: Int,
    val venue: String,
    val referee: String?,
    val homeTeam: Team,
    val awayTeam: Team,
    val goalsHomeTeam: Byte,
    val goalsAwayTeam: Byte,
    val score: Scores,
    val events: List<MatchEvents>
)