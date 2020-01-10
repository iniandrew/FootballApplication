package com.andrew.footballapplication.ui.team

import com.andrew.footballapplication.model.team.TeamResponse
import com.andrew.footballapplication.network.ApiRepository
import com.andrew.footballapplication.network.TheSportDBApi
import com.andrew.footballapplication.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception

class TeamPresenter (
    private val view: TeamUI.View,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()) : TeamUI.Presenter {

    override fun getTeamList(leagueId: Int) {
        view.showLoading()
        GlobalScope.launch(context.main ) {
            try {
                val data = gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getListTeam(leagueId)).await(),
                    TeamResponse::class.java
                )
                view.showTeamList(data)
                view.hideLoading()
            } catch (e: IOException) {
                view.showFailedLoad()
            }
        }
    }

    override fun getTeamByQuery(query: String) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            try {
                val data = gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getTeamByQuery(query)).await(),
                    TeamResponse::class.java
                )
                try {
                    view.showTeamList(data)
                } catch (e: Exception) {
                    view.showFailedLoad()
                }
                view.hideLoading()
            } catch (e: IOException) {
                view.showFailedLoad()
            }
        }
    }

}
