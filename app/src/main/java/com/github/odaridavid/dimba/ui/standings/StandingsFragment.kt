package com.github.odaridavid.dimba.ui.standings

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.odaridavid.dimba.R
import com.github.odaridavid.dimba.base.BaseFragment
import com.github.odaridavid.dimba.utils.Error
import com.github.odaridavid.dimba.utils.Success
import com.github.odaridavid.dimba.utils.isVisible
import com.github.odaridavid.dimba.models.standings.TeamStanding
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.fragment_standings.*
import org.koin.androidx.viewmodel.ext.android.viewModel
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
class StandingsFragment : BaseFragment<List<List<TeamStanding>>>(R.layout.fragment_standings) {

    private val standingsViewModel: StandingsViewModel by viewModel()
    private val args: StandingsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val leagueId = args.leagueId
        if (leagueId == -1) throw IllegalArgumentException("Invalid League Id Received")
        standingsViewModel.getLeagueStanding(leagueId)
        observeNetworkChanges(leagueId)
    }

    private fun observeNetworkChanges(leagueId: Int) {
        onNetworkChange { isConnected ->
            if (isConnected && standingsViewModel.leagueStanding.value is Error) {
                standingsViewModel.getLeagueStanding(leagueId)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        observeLeagueStanding()
    }

    private fun observeLeagueStanding() {
        standingsViewModel.leagueStanding.observe(this, Observer {
            handleState(it)
        })
    }

    override fun showLoading(isLoading: Boolean) {
        super.showLoading(isLoading)
        no_standings_text_view.isVisible(false)
    }

    override fun showOnSuccess(result: Success<List<List<TeamStanding>>>) {
        super.showOnSuccess(result)
        val teamStandings = result.data
        if (teamStandings.isEmpty()) showOnEmpty() else setupRecyclerView(teamStandings)
    }

    private fun showOnEmpty() {
        no_standings_text_view.isVisible(true)
    }

    private fun setupRecyclerView(teamStandings: List<List<TeamStanding>>) {
        val fullList = mutableListOf<TeamStanding>()
        createTeamStandingList(teamStandings, fullList)
        team_standing_recycler_view.layoutManager = LinearLayoutManager(context)
        val adapter = setupTeamStandingAdapter(fullList)
        team_standing_recycler_view.adapter = ScaleInAnimationAdapter(adapter)
    }

    private fun setupTeamStandingAdapter(teamStandings: List<TeamStanding>): TeamStandingAdapter {
        return TeamStandingAdapter().apply { submitList(teamStandings) }
    }

    private fun createTeamStandingList(
        teamStandings: List<List<TeamStanding>>,
        fullList: MutableList<TeamStanding>
    ) {
        for (teamStanding in teamStandings) {
            teamStanding.forEach {
                fullList.add(it)
            }
        }
    }

}
