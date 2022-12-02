package com.fdj.football.ui.main

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fdj.football.R

class LeagueViewHolder(itemView: View, val onClick: (League) -> Unit) : RecyclerView.ViewHolder(itemView) {
    private val leagueIdTextView: TextView = itemView.findViewById(R.id.league_id)
    private val leagueNameTextView: TextView = itemView.findViewById(R.id.league_name)
    private var currentLeague: League? = null

    init {
        itemView.setOnClickListener {
            currentLeague?.let {
                onClick(it)
            }
        }
    }

    fun bind(league: League) {
        currentLeague = league
        leagueIdTextView.text = league.id
        leagueNameTextView.text = league.league
    }
}