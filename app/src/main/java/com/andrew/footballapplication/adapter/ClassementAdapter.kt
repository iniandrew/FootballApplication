package com.andrew.footballapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andrew.footballapplication.R
import com.andrew.footballapplication.model.classement.ClassementItem
import kotlinx.android.synthetic.main.classement_item.view.*

class ClassementAdapter : RecyclerView.Adapter<ClassementAdapter.ClassementViewHolder>() {

    private var classements: List<ClassementItem> = listOf()

    fun setData(items: List<ClassementItem>) {
        classements = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassementViewHolder {
        return ClassementViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.classement_item, parent, false)
        )
    }

    override fun getItemCount(): Int = classements.size

    override fun onBindViewHolder(holder: ClassementViewHolder, position: Int) {
        holder.bindItem(classements[position], holder)
    }

    inner class ClassementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(items: ClassementItem, holder: ClassementViewHolder) {
            with(itemView) {
                tv_id.text = (holder.adapterPosition + 1).toString()
                tv_team.text = items.name
                tv_played.text = items.played
                tv_goalsFor.text = items.goalsFor
                tv_goalsAgainst.text = items.goalsAgainst
                tv_goalsDifference.text = items.goalsDifference
                tv_win.text = items.Win
                tv_draw.text = items.draw
                tv_loss.text = items.loss
                tv_total.text = items.total
            }
        }
    }

}