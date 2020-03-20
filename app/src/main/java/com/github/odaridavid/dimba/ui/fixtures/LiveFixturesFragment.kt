package com.github.odaridavid.dimba.ui.fixtures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.odaridavid.dimba.R
import org.koin.androidx.viewmodel.ext.android.viewModel


class LiveFixturesFragment : Fragment() {

    private val fixturesViewModel: FixturesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_live_fixtures, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fixturesViewModel.fixtures.observe(this, Observer {

        })
    }

}
