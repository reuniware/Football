package com.fdj.football.ui.main

import androidx.recyclerview.widget.DiffUtil

object LeagueDiffCallback : DiffUtil.ItemCallback<League>() {
    override fun areItemsTheSame(oldItem: League, newItem: League): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: League, newItem: League): Boolean {
        return oldItem.id == newItem.id
    }
}