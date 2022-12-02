package com.fdj.football.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.fdj.football.R
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

        val recyclerView: RecyclerView? = view.findViewById(R.id.recyclerViewListOfLeagues)
        recyclerView?.adapter = adapter

        viewModel.leaguesLiveData.observe(viewLifecycleOwner) {
            it.let {
                adapter.submitList(it as ArrayList<League>)
                if (viewModel.currentSearchText != "") {
                    filter(viewModel.currentSearchText)
                }
            }
        }

        val searchView: SearchView? = view.findViewById(R.id.searchText)
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.currentSearchText = newText.trim()
                filter(viewModel.currentSearchText)
                return false
            }
        })

    }

    private fun filter(text: String) {
        if (viewModel.initialLeagues.isEmpty() && adapter.currentList.size>0) {
            viewModel.initialLeagues = adapter.currentList.toList() as ArrayList<League>
        } else {
            if (viewModel.initialLeagues.isNotEmpty()) {
                val filteredLeagues = ArrayList<League>()
                viewModel.initialLeagues.forEach {
                    if (it.league.lowercase().contains(text.lowercase())) {
                        filteredLeagues.add(it)
                    }
                }
                adapter.submitList(filteredLeagues)
            }
         }
    }

}

private fun adapterOnClick(league: League) {
//        val intent = Intent(this, LeagueDetailActivity()::class.java)
//        intent.putExtra(LEAGUE_ID, league.id)
//        startActivity(intent)
}


