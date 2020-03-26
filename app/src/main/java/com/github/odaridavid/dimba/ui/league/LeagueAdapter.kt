package com.github.odaridavid.dimba.ui.league;

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
import com.github.odaridavid.dimba.models.leagues.League

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
class LeagueAdapter(val onItemClick: (Int) -> Unit) :
    ListAdapter<League, LeagueAdapter.LeagueViewHolder>(LeagueDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_league, parent, false)
        return LeagueViewHolder(view)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int): Unit =
        getItem(position).let { holder.bind(it) }

    inner class LeagueViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(league: League) {
            with(view) {
                view.setOnClickListener {
                    onItemClick(league.leagueId)
                }
                val ivLeagueIcon = findViewById<ImageView>(R.id.league_icon_image_view)
                ivLeagueIcon.contentDescription = "${league.name} icon"
                ivLeagueIcon.load(league.logo) {
                    placeholder(R.drawable.ic_league)
                }

                val tvLeagueName = findViewById<TextView>(R.id.league_name_text_view)
                tvLeagueName.text = league.name
            }
        }
    }

    companion object {
        val LeagueDiffUtil = object : DiffUtil.ItemCallback<League>() {
            override fun areItemsTheSame(oldItem: League, newItem: League): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: League, newItem: League): Boolean {
                return oldItem.leagueId == newItem.leagueId && oldItem.seasonStart == newItem.seasonStart
            }
        }
    }
}