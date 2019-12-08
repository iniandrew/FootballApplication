package com.andrew.footballapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andrew.footballapplication.R
import com.andrew.footballapplication.getDateFormat
import com.andrew.footballapplication.getTimeFormat
import com.andrew.footballapplication.model.match.MatchFavorite
import kotlinx.android.synthetic.main.match_item.view.*

class MatchFavoriteAdapter(private val listener: (MatchFavorite) -> Unit) : RecyclerView.Adapter<MatchFavoriteAdapter.MatchFavoriteViewHolder>() {

    private var matchs: List<MatchFavorite> = listOf()

    fun setData(items: List<MatchFavorite>) {
        matchs = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchFavoriteViewHolder {
        return MatchFavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.match_item, parent, false)
        )
    }

    override fun getItemCount(): Int = matchs.size

    override fun onBindViewHolder(holder: MatchFavoriteViewHolder, position: Int) {
        holder.bindItem(matchs[position], listener)
    }

    inner class MatchFavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(items: MatchFavorite, listener: (MatchFavorite) -> Unit) {
            with(itemView) {
                tv_date_event.text = getDateFormat(items.dateEvent)
                tv_time_event.text = getTimeFormat(items.timeEvent)
                tv_home_score.text = items.homeScore ?: "-"
                tv_home_team.text = items.homeTeam
                tv_away_score.text = items.awayScore ?: "-"
                tv_away_team.text = items.awayTeam

                itemView.setOnClickListener {
                    listener(items)
                }
            }
        }
    }
}