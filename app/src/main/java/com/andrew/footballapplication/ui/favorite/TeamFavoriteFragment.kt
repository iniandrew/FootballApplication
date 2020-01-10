package com.andrew.footballapplication.ui.favorite


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.andrew.footballapplication.R
import com.andrew.footballapplication.adapter.TeamFavoriteAdapter
import com.andrew.footballapplication.database.database
import com.andrew.footballapplication.model.favorite.TeamFavorite
import com.andrew.footballapplication.ui.team.detail.TeamDetailActivity
import com.andrew.footballapplication.utils.gone
import com.andrew.footballapplication.utils.visible
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class TeamFavoriteFragment : Fragment() {

    private lateinit var tvEmpty: TextView
    private lateinit var adapter: TeamFavoriteAdapter
    private lateinit var rvFavorite: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvFavorite = view.findViewById(R.id.rv_favorite)
        tvEmpty = view.findViewById(R.id.tv_empty)
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    private fun setupRecyclerView() {
        rvFavorite.layoutManager = LinearLayoutManager(context)
        rvFavorite.setHasFixedSize(true)
        adapter = TeamFavoriteAdapter { data ->
            startActivity<TeamDetailActivity>(TeamDetailActivity.EXTRA_TEAM to data.idTeam)
        }
        rvFavorite.adapter = adapter
    }

    private fun showFavorite() {
        context?.database?.use {
            val result = select(TeamFavorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<TeamFavorite>())
            adapter.setData(favorite)
            if (favorite.isEmpty()) tvEmpty.visible() else tvEmpty.gone()
        }
    }
}
