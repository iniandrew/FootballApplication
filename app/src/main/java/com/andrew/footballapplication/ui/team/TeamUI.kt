package com.andrew.footballapplication.ui.team

import com.andrew.footballapplication.model.team.TeamResponse

interface TeamUI {

    interface View {
        fun setupRecyclerView()
        fun showFailedLoad()
        fun showTeamList(teamResponse: TeamResponse)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun getTeamList(leagueId: Int)
        fun getTeamByQuery(query: String)
    }
}