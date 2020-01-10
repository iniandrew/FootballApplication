package com.andrew.footballapplication.ui.team.detail

import com.andrew.footballapplication.model.team.TeamItem

interface TeamDetailUI {
    interface View {
        fun showFailedLoad()
        fun showTeamDetail(item: TeamItem)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun getTeamDetail(leagueId: Int)
    }
}