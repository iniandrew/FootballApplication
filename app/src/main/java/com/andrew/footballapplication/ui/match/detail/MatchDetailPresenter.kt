package com.andrew.footballapplication.ui.match.detail

import com.andrew.footballapplication.model.team.TeamResponse
import com.andrew.footballapplication.model.match.MatchResponse
import com.andrew.footballapplication.network.ApiRepository
import com.andrew.footballapplication.network.TheSportDBApi
import com.andrew.footballapplication.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class MatchDetailPresenter(
    private val view: MatchDetailUI.View,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : MatchDetailUI.Presenter {

    override fun getMatchDetail(eventId: String) {
        view.showLoading()

        GlobalScope.launch(context.main ) {
            try {
                val data = gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getMatchDetail(eventId)).await(),
                    MatchResponse::class.java
                )
                for (item in data.results) {
                    view.showMatchDetail(item)
                }
                view.hideLoading()
            } catch (e: IOException) {
                view.showFailedLoad()
            }
        }
    }

    override fun getHomeTeamBadge(teamId: String) {
        GlobalScope.launch(context.main ) {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getTeamBadge(teamId)).await(),
                TeamResponse::class.java
            )
            view.showHomeTeamBadge(data.results)
        }
    }

    override fun getAwayTeamBadge(teamId: String) {
        GlobalScope.launch(context.main ) {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getTeamBadge(teamId)).await(),
                TeamResponse::class.java
            )
            view.showAwayTeamBadge(data.results)
        }
    }
}