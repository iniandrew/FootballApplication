package com.andrew.footballapplication.ui.league.detail

import com.andrew.footballapplication.model.league.LeagueItem

interface LeagueDetailUI {
    interface View {
        fun showFailedLoad(error: String)
        fun showLeagueDetail(item: LeagueItem)
    }

    interface Presenter {
        fun getLeagueDetail(leagueId: Int?)
    }
}