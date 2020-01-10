package com.andrew.footballapplication.ui.match.detail

import com.andrew.footballapplication.model.team.TeamItem
import com.andrew.footballapplication.model.match.MatchItem

interface MatchDetailUI {
    interface View {
        fun showFailedLoad()
        fun showMatchDetail(matchItem: MatchItem)
        fun showHomeTeamBadge(homeTeamBadge: List<TeamItem>)
        fun showAwayTeamBadge(awayTeamBadge: List<TeamItem>)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun getMatchDetail(eventId: String)
        fun getHomeTeamBadge(teamId: String)
        fun getAwayTeamBadge(teamId: String)
    }
}