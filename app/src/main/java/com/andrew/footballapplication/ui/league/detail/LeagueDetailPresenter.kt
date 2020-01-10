package com.andrew.footballapplication.ui.league.detail

import com.andrew.footballapplication.model.league.LeagueResponse
import com.andrew.footballapplication.network.ApiRepository
import com.andrew.footballapplication.network.TheSportDBApi
import com.andrew.footballapplication.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class LeagueDetailPresenter(
    private val view: LeagueDetailUI.View,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
): LeagueDetailUI.Presenter {

    override fun getLeagueDetail(leagueId: Int) {
        GlobalScope.launch(context.main) {
            view.showLoading()
            try {
                val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getLeagueDetail(leagueId)).await(),
                    LeagueResponse::class.java
                )

                for (item in data.results) {
                    view.showLeagueDetail(item)
                }
                view.hideLoading()
            } catch (e: IOException) {
                view.showFailedLoad()
            }

        }
    }

}