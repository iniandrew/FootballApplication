package com.andrew.footballapplication.ui.match.detail

import com.andrew.footballapplication.model.match.MatchItem

interface MatchDetailUI {
    interface View {
        fun showFailedLoad()
        fun showMatchDetail(matchItem: MatchItem)
        fun showHomeTeamBadge(homeTeamBadge: String?)
        fun showAwayTeamBadge(awayTeamBadge: String?)
    }

    interface Presenter {
        fun getMatchDetail(eventId: String)
        fun getTeamDetail(idHomeTeam: String, idAwayTeam: String)
    }
}