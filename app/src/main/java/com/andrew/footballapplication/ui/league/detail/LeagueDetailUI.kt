package com.andrew.footballapplication.ui.league.detail

import com.andrew.footballapplication.model.league.LeagueItem

interface LeagueDetailUI {
    interface View {
        fun showFailedLoad()
        fun showLeagueDetail(item: LeagueItem)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun getLeagueDetail(leagueId: Int)
    }
}