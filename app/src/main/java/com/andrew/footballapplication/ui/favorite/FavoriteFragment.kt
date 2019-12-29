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
import com.andrew.footballapplication.adapter.MatchFavoriteAdapter
import com.andrew.footballapplication.database.database
import com.andrew.footballapplication.utils.gone
import com.andrew.footballapplication.model.match.MatchFavorite
import com.andrew.footballapplication.ui.match.detail.MatchDetailActivity
import com.andrew.footballapplication.utils.visible
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {

    private lateinit var tvEmpty: TextView
    private lateinit var adapter: MatchFavoriteAdapter
    private lateinit var rvFavorite: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
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
        adapter = MatchFavoriteAdapter { data ->
            startActivity<MatchDetailActivity>(MatchDetailActivity.EXTRA_MATCH to data.idEvent)
        }
        rvFavorite.adapter = adapter
    }

    private fun showFavorite() {
        context?.database?.use {
            val result = select(MatchFavorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<MatchFavorite>())
            adapter.setData(favorite)
            if (favorite.isEmpty()) tvEmpty.visible() else tvEmpty.gone()
        }
    }

}
