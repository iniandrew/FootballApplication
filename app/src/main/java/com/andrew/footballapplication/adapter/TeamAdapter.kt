package com.andrew.footballapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andrew.footballapplication.R
import com.andrew.footballapplication.model.team.TeamItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.team_item.view.*

class TeamAdapter (private val listener: (TeamItem) -> Unit) : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    private var teams: List<TeamItem> = mutableListOf()

    fun setData(items: List<TeamItem>) {
        teams = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TeamViewHolder {
        return TeamViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.team_item, parent, false)
        )
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

    inner class TeamViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(items: TeamItem, listener: (TeamItem) -> Unit) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(items.teamBadge)
                    .apply(RequestOptions().override(500, 550))
                    .into(img_team_logo)
                tv_team_name.text = items.strTeam
                tv_team_desc.text = items.strDescription

                itemView.setOnClickListener {
                    listener(items)
                }
            }

        }
    }

}