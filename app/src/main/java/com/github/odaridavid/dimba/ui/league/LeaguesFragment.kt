package com.github.odaridavid.dimba.ui.league

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.odaridavid.dimba.R
import com.github.odaridavid.dimba.base.BaseFragment
import com.github.odaridavid.dimba.utils.Error
import com.github.odaridavid.dimba.utils.Success
import com.github.odaridavid.dimba.utils.isVisible
import com.github.odaridavid.dimba.models.leagues.League
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.fragment_leagues.*
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
class LeaguesFragment : BaseFragment<List<League>>(R.layout.fragment_leagues) {

    private val leaguesViewModel: LeaguesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = view!!.findNavController()
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNetworkChanges()
    }

    private fun observeNetworkChanges() {
        onNetworkChange { isConnected ->
            if (isConnected && leaguesViewModel.leagues.value is Error)
                leaguesViewModel.getAllAvailableLeagues()
        }
    }

    override fun onResume() {
        super.onResume()
        observeLeagues()
    }

    private fun observeLeagues() {
        leaguesViewModel.leagues.observe(this, Observer {
            handleState(it)
        })
    }

    override fun showLoading(isLoading: Boolean) {
        super.showLoading(isLoading)
        no_leagues_text_view.isVisible(false)
    }

    override fun showOnSuccess(result: Success<List<League>>) {
        super.showOnSuccess(result)
        val availableLeagues = result.data
        if (availableLeagues.isEmpty()) showOnEmpty() else setupRecyclerView(availableLeagues)
    }

    private fun showOnEmpty() {
        no_leagues_text_view.isVisible(true)
    }

    private fun setupRecyclerView(availableLeagues: List<League>) {
        leagues_recycler_view.layoutManager = LinearLayoutManager(context)
        val adapter = setupLeaguesAdapter(availableLeagues)
        leagues_recycler_view.adapter = ScaleInAnimationAdapter(adapter)
    }

    private fun setupLeaguesAdapter(availableLeagues: List<League>): LeagueAdapter {
        return LeagueAdapter { leagueId ->
            val destination = LeaguesFragmentDirections.toTeamStandingsFragment(leagueId)
            view!!.findNavController().navigate(destination)
        }.apply { submitList(availableLeagues) }
    }
}
