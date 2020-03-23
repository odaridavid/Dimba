package com.github.odaridavid.dimba.ui.fixtures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.odaridavid.dimba.R
import com.github.odaridavid.dimba.commons.*
import com.github.odaridavid.dimba.models.fixtures.LiveFixture
import kotlinx.android.synthetic.main.fragment_live_fixtures.*
import org.koin.androidx.viewmodel.ext.android.viewModel


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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NetworkUtils.getNetworkStatus(context!!).observe(this, Observer { isConnected ->
            if (isConnected) {
                if (fixturesViewModel.fixtures.value is Error) {
                    fixturesViewModel.getFixtures()
                }
            } else fixturesViewModel.setError()
        })
    }

    override fun onResume() {
        super.onResume()
        observeLiveFixtures()
    }

    private fun showOnError(message: String?) {
        showLoading(false)
        error_text_view.text = message
        error_text_view.isVisible(true)
    }

    private fun observeLiveFixtures() {
        fixturesViewModel.fixtures.observe(this, Observer {
            when (it) {
                is Loading -> showLoading(true)
                is Success -> showOnSuccess(it)
                is Error -> showOnError(ExceptionHandler(context!!).parse(it.e))
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        live_fixtures_progress_bar.isVisible(isLoading)
        no_live_fixtures_text_view.isVisible(false)
        error_text_view.isVisible(false)
    }

    private fun showOnSuccess(result: Success<List<LiveFixture>>) {
        showLoading(false)
        val liveFixtures = result.data
        if (liveFixtures.isEmpty()) {
            no_live_fixtures_text_view.isVisible(true)
        } else setupRecyclerView(liveFixtures)
    }

    private fun setupRecyclerView(liveFixtures: List<LiveFixture>) {
        live_fixtures_recycler_view.layoutManager = LinearLayoutManager(context)
        live_fixtures_recycler_view.adapter = LiveFixturesAdapter().apply {
            submitList(liveFixtures)
        }
    }

}
