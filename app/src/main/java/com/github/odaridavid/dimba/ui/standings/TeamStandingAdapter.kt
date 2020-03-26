package com.github.odaridavid.dimba.ui.standings;

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
import com.github.odaridavid.dimba.models.standings.TeamStanding

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
class TeamStandingAdapter :
    ListAdapter<TeamStanding, TeamStandingAdapter.TeamStandingViewHolder>(TeamStandingDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamStandingViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_team_standing, parent, false)
        return TeamStandingViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamStandingViewHolder, position: Int): Unit =
        getItem(position).let { holder.bind(it) }

    inner class TeamStandingViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        //TODO Change background color for nth even rows
        fun bind(teamStanding: TeamStanding) {
            with(view) {
                val tvRank = findViewById<TextView>(R.id.rank_text_view)
                val ivTeamLogo = findViewById<ImageView>(R.id.team_logo_image_view)
                val tvTeamName = findViewById<TextView>(R.id.team_name_text_view)
                val tvForm = findViewById<TextView>(R.id.form_text_view)
                val tvPoints = findViewById<TextView>(R.id.points_text_view)

                tvRank.text = teamStanding.rank.toString()
                ivTeamLogo.load(teamStanding.logo) {
                    placeholder(R.drawable.ic_league)
                }
                tvTeamName.text = teamStanding.teamName
                tvForm.text = teamStanding.form
                tvPoints.text = teamStanding.points.toString()
            }
        }
    }

    companion object {
        val TeamStandingDiffUtil = object : DiffUtil.ItemCallback<TeamStanding>() {
            override fun areItemsTheSame(oldItem: TeamStanding, newItem: TeamStanding): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: TeamStanding, newItem: TeamStanding): Boolean {
                return oldItem.teamId == newItem.teamId && oldItem.teamName == newItem.teamName
            }
        }
    }
}