package com.andrew.footballapplication.ui.match

import com.andrew.footballapplication.model.match.MatchResponse
import com.andrew.footballapplication.network.ApiRepository
import com.andrew.footballapplication.network.TheSportDBApi
import com.andrew.footballapplication.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception

class MatchPresenter(
    private val view: MatchUI.View,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()) : MatchUI.Presenter {

    override fun getPreviousMatch(leagueId: Int) {
        view.showLoading()
        GlobalScope.launch(context.main ) {
            try {
                val data = gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getPreviousMatch(leagueId)).await(),
                    MatchResponse::class.java
                )
                view.showListMatch(data)
                view.hideLoading()
            } catch (e: IOException) {
                view.showFailedLoad()
            }
        }
    }

    override fun getNextMatch(leagueId: Int) {
        view.showLoading()

        GlobalScope.launch(context.main ) {
            try {
                val data = gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getNextMatch(leagueId)).await(),
                    MatchResponse::class.java
                )
                view.showListMatch(data)
                view.hideLoading()
            } catch (e: IOException) {
                view.showFailedLoad()
            }
        }
    }

    override fun getMatchByQuery(query: String) {
        view.showLoading()

        GlobalScope.launch(context.main ) {
            try {
                val data = gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getMatchByQuery(query)).await(),
                    MatchResponse::class.java
                )
                try {
                    view.showListMatch(data)
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