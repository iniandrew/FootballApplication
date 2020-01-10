package com.andrew.footballapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andrew.footballapplication.R
import com.andrew.footballapplication.model.favorite.TeamFavorite
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.team_item.view.*

class TeamFavoriteAdapter (private val listener: (TeamFavorite) -> Unit) : RecyclerView.Adapter<TeamFavoriteAdapter.TeamFavoriteViewHolder>() {

    private var teams: List<TeamFavorite> = mutableListOf()

    fun setData(items: List<TeamFavorite>) {
        teams = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TeamFavoriteViewHolder {
        return TeamFavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.team_item, parent, false)
        )
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(
        holder: TeamFavoriteViewHolder,
        position: Int
    ) {
        holder.bindItem(teams[position], listener)
    }

    inner class TeamFavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(items: TeamFavorite, listener: (TeamFavorite) -> Unit) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(items.teamBadge)
                    .apply(RequestOptions().override(500, 550))
                    .into(img_team_logo)
                tv_team_name.text = items.teamName
                tv_team_desc.text = items.teamDesc

                itemView.setOnClickListener {
                    listener(items)
                }
            }
        }
    }
}