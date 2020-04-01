package com.github.odaridavid.dimba.repositories

import com.github.odaridavid.dimba.db.LeaguesDao
import com.github.odaridavid.dimba.mappers.toEntity
import com.github.odaridavid.dimba.models.leagues.League
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
class LeaguesRepository(val api: FootballApiService, val leaguesDao: LeaguesDao) {

    //TODO Update Leagues either periodically or force refresh on request or if data is stale based on last update date
    suspend fun getAvailableLeagues(): ResultState<List<League>> {
        return executeNonBlocking {
            if (leaguesDao.loadAllLeagues().isEmpty()) {
                val response = api.getAvailableLeagues()
                if (response.api.results == 0)
                    Success(emptyList<League>())
                else {
                    val leagues = response.api.leagues
                    leaguesDao.insertLeagues(leagues.map { it.toEntity() })
                    Success(leaguesDao.loadAllLeagues())
                }
            } else Success(leaguesDao.loadAllLeagues())
        }
    }

}