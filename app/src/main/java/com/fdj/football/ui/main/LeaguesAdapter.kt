package com.fdj.football.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.fdj.football.R

class LeaguesAdapter(private val onClick: (League) -> Unit) : ListAdapter<League, LeagueViewHolder>(LeagueDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_league, parent, false)
        return LeagueViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        val league = getItem(position)
        holder.bind(league)
    }
}