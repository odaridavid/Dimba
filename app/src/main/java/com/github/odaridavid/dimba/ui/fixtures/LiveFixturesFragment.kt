package com.github.odaridavid.dimba.ui.fixtures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.odaridavid.dimba.R
import kotlinx.android.synthetic.main.fragment_live_fixtures.*
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
