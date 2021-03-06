package com.github.odaridavid.dimba.interactors

import com.github.odaridavid.dimba.models.standings.TeamStanding
import com.github.odaridavid.dimba.repositories.StandingsRepository
import com.github.odaridavid.dimba.utils.ResultState

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
class GetStandingsUseCase(val standingsRepository: StandingsRepository) {

    suspend operator fun invoke(leagueId: Int): ResultState<List<List<TeamStanding>>> {
        return standingsRepository.getLeagueStandings(leagueId)
    }
}