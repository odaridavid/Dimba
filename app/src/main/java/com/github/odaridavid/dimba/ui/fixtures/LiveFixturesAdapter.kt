package com.github.odaridavid.dimba.ui.fixtures;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.github.odaridavid.dimba.R
import com.github.odaridavid.dimba.models.fixtures.LiveFixture

/**
 *
 * Copyright 2020 David Odari
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *          http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 **/
class LiveFixturesAdapter :
    ListAdapter<LiveFixture, LiveFixturesAdapter.LiveFixtureViewHolder>(LiveFixtureDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveFixtureViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_live_fixture, parent, false)
        return LiveFixtureViewHolder(view)
    }

    override fun onBindViewHolder(holder: LiveFixtureViewHolder, position: Int): Unit =
        getItem(position).let { holder.bind(it) }

    inner class LiveFixtureViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(fixture: LiveFixture) {
            with(view) {
                val tvHomeTeamName = findViewById<TextView>(R.id.home_team_name_text_view)
                tvHomeTeamName.text = fixture.homeTeam.name

                val ivHomeTeamLogo = findViewById<ImageView>(R.id.home_team_logo)
                ivHomeTeamLogo.load(fixture.homeTeam.logo)
                ivHomeTeamLogo.contentDescription = "${fixture.homeTeam.name} logo"

                val ivAwayTeamLogo = findViewById<ImageView>(R.id.away_team_logo)
                ivAwayTeamLogo.load(fixture.awayTeam.logo)
                ivAwayTeamLogo.contentDescription = "${fixture.awayTeam.name} logo"

                val tvAwayTeamName = findViewById<TextView>(R.id.away_team_name_text_view)
                tvAwayTeamName.text = fixture.awayTeam.name

                val tvGoals = findViewById<TextView>(R.id.goals_text_view)
                tvGoals.text = context.getString(
                    R.string.template_goals,
                    fixture.goalsHomeTeam,
                    fixture.goalsAwayTeam
                )

                val tvElapsedTime = findViewById<TextView>(R.id.elapsed_time_text_view)
                tvElapsedTime.text = "${fixture.elapsed}`"
            }
        }
    }

    companion object {
        val LiveFixtureDiffUtil = object : DiffUtil.ItemCallback<LiveFixture>() {
            override fun areItemsTheSame(oldItem: LiveFixture, newItem: LiveFixture): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: LiveFixture, newItem: LiveFixture): Boolean {
                return oldItem.fixtureId == newItem.fixtureId && oldItem.venue == newItem.venue && oldItem.eventDate == newItem.eventDate && oldItem.eventTimestamp == newItem.eventTimestamp
            }
        }
    }
}