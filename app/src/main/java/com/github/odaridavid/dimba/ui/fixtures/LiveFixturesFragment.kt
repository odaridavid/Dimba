package com.github.odaridavid.dimba.ui.fixtures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.odaridavid.dimba.R
import com.github.odaridavid.dimba.commons.Error
import com.github.odaridavid.dimba.commons.NoInternetConnectionException
import com.github.odaridavid.dimba.commons.Success
import com.github.odaridavid.dimba.commons.isVisible
import com.github.odaridavid.dimba.models.LiveFixture
import kotlinx.android.synthetic.main.fragment_live_fixtures.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.ConnectException


class LiveFixturesFragment : Fragment() {

    //TODO Check Live Fixture Status Periodically
    //TODO Display Live Fixture Events
    private val fixturesViewModel: FixturesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_live_fixtures, container, false)
    }


    override fun onResume() {
        super.onResume()
        observeLiveFixtures()
    }

    private fun displayOnNetworkError() {
        live_fixtures_progress_bar.isVisible(false)
        no_live_fixtures_text_view.text = getString(R.string.no_internet_connection)
        no_live_fixtures_text_view.isVisible(true)
    }

    private fun observeLiveFixtures() {
        fixturesViewModel.fixtures.observe(this, Observer {
            live_fixtures_progress_bar.isVisible(false)
            when (it) {
                is Success -> displayOnSuccess(it)
                is Error -> {
                    if (it.e is NoInternetConnectionException || it.e is ConnectException) displayOnNetworkError()
                }
            }
        })
    }

    private fun displayOnSuccess(result: Success<List<LiveFixture>>) {
        val liveFixtures = result.data
        if (liveFixtures.isEmpty()) no_live_fixtures_text_view.isVisible(true)
        else {
            live_fixtures_recycler_view.layoutManager = LinearLayoutManager(context)
            live_fixtures_recycler_view.adapter = LiveFixturesAdapter().apply {
                submitList(liveFixtures)
            }
        }
    }

}
