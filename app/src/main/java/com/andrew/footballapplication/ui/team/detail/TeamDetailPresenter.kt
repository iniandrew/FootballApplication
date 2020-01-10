package com.andrew.footballapplication.ui.team.detail

import com.andrew.footballapplication.model.team.TeamResponse
import com.andrew.footballapplication.network.ApiRepository
import com.andrew.footballapplication.network.TheSportDBApi
import com.andrew.footballapplication.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class TeamDetailPresenter (
    private val view: TeamDetailUI.View,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : TeamDetailUI.Presenter {

    override fun getTeamDetail(leagueId: Int) {
        GlobalScope.launch(context.main) {
            view.showLoading()
            try {
                val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamDetail(leagueId)).await(),
                    TeamResponse::class.java
                )

                for (item in data.results) {
                    view.showTeamDetail(item)
                }
                view.hideLoading()
            } catch (e: IOException) {
                view.showFailedLoad()
            }

        }
    }
}