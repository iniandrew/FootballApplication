package com.andrew.footballapplication.ui.league

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.andrew.footballapplication.R
import com.andrew.footballapplication.adapter.LeagueAdapter
import com.andrew.footballapplication.model.league.LeagueItem
import com.andrew.footballapplication.ui.league.detail.LeagueDetailActivity
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class LeagueFragment : Fragment() {

    private var leagueList: MutableList<LeagueItem> = mutableListOf()
    private lateinit var rvLeague: RecyclerView
    private lateinit var adapter: LeagueAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_league, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvLeague = view.findViewById(R.id.rv_league)

        initData()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        rvLeague.layoutManager = GridLayoutManager(context, 2)
        rvLeague.setHasFixedSize(true)

        adapter = LeagueAdapter { data ->
            startActivity<LeagueDetailActivity>(LeagueDetailActivity.EXTRA_LEAGUE_ID to data.leagueId)
        }

        adapter.setData(leagueList)
        rvLeague.adapter = adapter
    }

    private fun initData() {
        val leagueId = resources.getIntArray(R.array.league_id)
        val dataBadge = resources.getStringArray(R.array.league_badge)
        val dataName = resources.getStringArray(R.array.league_name)

        for (i in dataName.indices) {
            leagueList.add(
                LeagueItem(
                    leagueId[i],
                    dataBadge[i],
                    dataName[i]
                )
            )
        }
    }

}
