package com.github.odaridavid.dimba.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.github.odaridavid.dimba.R
import com.github.odaridavid.dimba.base.BaseFragment
import com.github.odaridavid.dimba.commons.Success
import com.github.odaridavid.dimba.models.standings.TeamStanding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StandingsFragment : BaseFragment<List<List<TeamStanding>>>() {

    //TODO Layout/View Reuse for loading and error states
    private val standingsViewModel: StandingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_standings, container, false)
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

    }

    override fun showOnError(message: String) {
        super.showOnError(message)
    }

    override fun showOnSuccess(result: Success<List<List<TeamStanding>>>) {
        super.showOnSuccess(result)
    }


}
