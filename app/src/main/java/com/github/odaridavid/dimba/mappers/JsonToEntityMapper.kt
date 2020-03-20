package com.github.odaridavid.dimba.mappers

import com.github.odaridavid.dimba.models.*
import com.github.odaridavid.dimba.network.model.*

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
fun LiveFixturesResponse.toEntity(): LiveFixture {
    return LiveFixture(
        fixtureId,
        leagueId,
        league.toEntity(),
        eventDate,
        eventTimestamp,
        firstHalfStart ?: 0,
        secondHalfStart ?: 0,
        round,
        status,
        statusShort,
        elapsed ?: 0,
        venue,
        referee,
        homeTeam.toEntity(),
        awayTeam.toEntity(),
        goalsHomeTeam,
        goalsAwayTeam,
        score.toEntity(),
        events.map { it.toEntity() }
    )
}

fun LeagueResponse.toEntity(): League {
    return League(name, country, logo, flag)
}

fun TeamResponse.toEntity(): Team = Team(id, name, logo)

fun ScoresResponse.toEntity(): Scores = Scores(halfTime, fullTime, extraTime, penalty)

fun EventsResponse.toEntity(): MatchEvents {
    return MatchEvents(
        elapsed ?: 0,
        elapsedPlus ?: 0,
        teamId,
        teamName,
        playerId ?: 0,
        player ?: "Unknown",
        assistId ?: 0,
        assist,
        type,
        detail,
        comments
    )
}