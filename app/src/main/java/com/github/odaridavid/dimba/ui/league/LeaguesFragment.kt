package com.github.odaridavid.dimba.ui.league

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.odaridavid.dimba.R
import com.github.odaridavid.dimba.base.BaseFragment
import com.github.odaridavid.dimba.commons.Error
import com.github.odaridavid.dimba.commons.Success
import com.github.odaridavid.dimba.commons.isVisible
import com.github.odaridavid.dimba.commons.showToast
import com.github.odaridavid.dimba.models.leagues.League
import kotlinx.android.synthetic.main.fragment_leagues.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LeaguesFragment : BaseFragment<List<League>>() {

    //TODO Navigate from league to team standings in league
    private val leaguesViewModel: LeaguesViewModel by viewModel()

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        rootView = inflater.inflate(R.layout.fragment_leagues, container, false)
        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = rootView.findNavController()
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        league_progress_bar.isVisible(isLoading)
        error_text_view.isVisible(false)
        no_leagues_text_view.isVisible(false)
    }

    override fun showOnError(message: String) {
        super.showOnError(message)
        error_text_view.text = message
        error_text_view.isVisible(true)
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
        leagues_recycler_view.adapter = LeagueAdapter().apply {
            submitList(availableLeagues)
        }
    }
}
