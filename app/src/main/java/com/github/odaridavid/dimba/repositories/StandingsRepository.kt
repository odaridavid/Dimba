package com.github.odaridavid.dimba.repositories

import com.github.odaridavid.dimba.mappers.toEntity
import com.github.odaridavid.dimba.models.standings.TeamStanding
import com.github.odaridavid.dimba.network.FootballApiService
import com.github.odaridavid.dimba.utils.ResultState
import com.github.odaridavid.dimba.utils.Success
import com.github.odaridavid.dimba.utils.executeNonBlocking

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
class StandingsRepository(val api: FootballApiService) {

    suspend fun getLeagueStandings(leagueId: Int): ResultState<List<List<TeamStanding>>> {
        return executeNonBlocking {
            val response = api.getLeagueStandings(leagueId)
            if (response.api.results == 0)
                Success(emptyList<List<TeamStanding>>())
            else {
                val standings =
                    response.api.standings.map { it.map { standings -> standings.toEntity() } }
                Success(standings)
            }
        }
    }
}