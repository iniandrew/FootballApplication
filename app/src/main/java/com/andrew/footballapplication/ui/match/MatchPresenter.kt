package com.andrew.footballapplication.ui.match

import com.andrew.footballapplication.APIEndpoint
import com.andrew.footballapplication.BuildConfig
import com.andrew.footballapplication.model.match.MatchResponse
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import java.lang.Exception

class MatchPresenter(private val view: MatchUI.View) : MatchUI.Presenter{

    override fun getPreviousMatch(leagueId: Int) {
        AndroidNetworking.get(APIEndpoint.PREVIOUS_MATCH)
            .addPathParameter("api_key", BuildConfig.TSDB_API_KEY)
            .addPathParameter("leagueId", leagueId.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsObject(MatchResponse::class.java, object: ParsedRequestListener<MatchResponse> {
                override fun onResponse(response: MatchResponse) {
                    view.showListMatch(response)
                }

                override fun onError(anError: ANError?) {
                    view.showFailedLoad()
                }

            })
    }

    override fun getNextMatch(leagueId: Int) {
        AndroidNetworking.get(APIEndpoint.NEXT_MATCH)
            .addPathParameter("api_key", BuildConfig.TSDB_API_KEY)
            .addPathParameter("leagueId", leagueId.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsObject(MatchResponse::class.java, object: ParsedRequestListener<MatchResponse> {
                override fun onResponse(response: MatchResponse) {
                    view.showListMatch(response)
                }

                override fun onError(anError: ANError?) {
                    view.showFailedLoad()
                }

            })
    }

    override fun getMatchByQuery(query: String) {
        AndroidNetworking.get(APIEndpoint.SEARCH_MATCH)
            .addPathParameter("api_key", BuildConfig.TSDB_API_KEY)
            .addPathParameter("query", query)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsObject(MatchResponse::class.java, object : ParsedRequestListener<MatchResponse> {
                override fun onResponse(response: MatchResponse?) {
                    try {
                        view.showListMatch(response!!)
                    } catch (e: Exception) {
                        view.showFailedLoad()
                    }
                }

                override fun onError(anError: ANError?) {
                    view.showFailedLoad()
                }

            })
    }
}