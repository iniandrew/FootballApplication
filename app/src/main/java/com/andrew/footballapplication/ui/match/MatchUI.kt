package com.andrew.footballapplication.ui.match

import com.andrew.footballapplication.model.match.MatchResponse

interface MatchUI  {
    interface View {
        fun setupRecyclerView()
        fun showFailedLoad()
        fun showListMatch(matchResponse: MatchResponse)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun getPreviousMatch(leagueId: Int)
        fun getNextMatch(leagueId: Int)
        fun getMatchByQuery(query: String)
    }
}