package com.github.odaridavid.dimba.ui.standings;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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

        fun bind(teamStanding: TeamStanding) {
               //TODO Create Teamstanding layout
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