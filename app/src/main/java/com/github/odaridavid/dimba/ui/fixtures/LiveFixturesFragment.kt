package com.github.odaridavid.dimba.ui.fixtures

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.odaridavid.dimba.R
import com.github.odaridavid.dimba.commons.Constants
import kotlinx.android.synthetic.main.fragment_live_fixtures.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class LiveFixturesFragment : Fragment() {

    //TODO Check Live Fixture Status Periodically
    //TODO Display Live Fixture Events
    private val fixturesViewModel: FixturesViewModel by viewModel()
    private val sharedPreferences: SharedPreferences by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_live_fixtures, container, false)
    }


    override fun onResume() {
        super.onResume()
        val isConnected = sharedPreferences.getBoolean(Constants.PREF_KEY_NETWORK_AVAILABLE, false)
        if (isConnected)
            observeLiveFixtures()
        else
            displayNoInternetConnection()
    }

    private fun displayNoInternetConnection() {
        live_fixtures_progress_bar.visibility = View.GONE
        no_live_fixtures_text_view.text = getString(R.string.no_internet_connection)
        no_live_fixtures_text_view.visibility = View.VISIBLE
    }

    private fun observeLiveFixtures() {
        fixturesViewModel.fixtures.observe(this, Observer {
            live_fixtures_progress_bar.visibility = View.GONE
            if (it.isEmpty()) no_live_fixtures_text_view.visibility = View.VISIBLE
            else {
                live_fixtures_recycler_view.layoutManager = LinearLayoutManager(context)
                live_fixtures_recycler_view.adapter = LiveFixturesAdapter().apply {
                    submitList(it)
                }
            }
        })
    }

}
