package com.fdj.football.ui.main

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fdj.football.R
import com.google.gson.annotations.SerializedName
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        // TODO: Use the ViewModel
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getAllLeagues()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    private val adapter = LeaguesAdapter { league -> adapterOnClick(league) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_league, parent, false)

        val recyclerView: RecyclerView? = view.findViewById(R.id.recyclerViewListOfLeagues)
        recyclerView?.adapter = adapter

    }

}

private fun adapterOnClick(league: League) {
//        val intent = Intent(this, LeagueDetailActivity()::class.java)
//        intent.putExtra(LEAGUE_ID, league.id)
//        startActivity(intent)
}

class LeaguesAdapter(private val onClick: (League) -> Unit) : ListAdapter<League, LeagueViewHolder>(LeagueDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_league, parent, false)
        return LeagueViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        val information = getItem(position)
        holder.bind(information)
    }
}

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



object LeagueDiffCallback : DiffUtil.ItemCallback<League>() {
    override fun areItemsTheSame(oldItem: League, newItem: League): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: League, newItem: League): Boolean {
        return oldItem.id == newItem.id
    }
}
