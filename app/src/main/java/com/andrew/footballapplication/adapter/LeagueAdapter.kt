package com.andrew.footballapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andrew.footballapplication.R
import com.andrew.footballapplication.model.league.LeagueItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.league_item.view.*

class LeagueAdapter(private val listener: (LeagueItem) -> Unit) : RecyclerView.Adapter<LeagueAdapter.LeagueViewHolder>() {

    private var leagues: List<LeagueItem> = listOf()

    fun setData(items: List<LeagueItem>) {
        leagues = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        return LeagueViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.league_item, parent, false)
        )
    }

    override fun getItemCount(): Int = leagues.size

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(leagues[position], listener)
    }

    inner class LeagueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItem(items: LeagueItem, listener: (LeagueItem) -> Unit) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(items.leagueBadge)
                    .apply(RequestOptions().override(500, 550))
                    .into(img_league_badge)
                tv_league_name.text = items.leagueName

                itemView.setOnClickListener {
                    listener(items)
                }
            }

        }
    }
}