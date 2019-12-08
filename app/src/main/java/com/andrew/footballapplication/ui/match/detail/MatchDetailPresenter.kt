package com.andrew.footballapplication.ui.match.detail

import com.andrew.footballapplication.APIEndpoint
import com.andrew.footballapplication.BuildConfig
import com.andrew.footballapplication.model.match.MatchResponse
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.androidnetworking.interfaces.ParsedRequestListener
import org.json.JSONObject

class MatchDetailPresenter(private val view: MatchDetailUI.View) : MatchDetailUI.Presenter {

    override fun getMatchDetail(eventId: String) {
        AndroidNetworking.get(APIEndpoint.MATCH_DETAIL)
            .addPathParameter("api_key", BuildConfig.TSDB_API_KEY)
            .addPathParameter("eventId", eventId)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsObject(MatchResponse::class.java, object: ParsedRequestListener<MatchResponse> {
                override fun onResponse(response: MatchResponse) {
                    for (item in response.results) {
                        view.showMatchDetail(item)
                    }
                }

                override fun onError(anError: ANError?) {
                    view.showFailedLoad()
                }

            })
    }

    override fun getTeamDetail(idHomeTeam: String, idAwayTeam: String) {
        AndroidNetworking.get(APIEndpoint.TEAM_DETAIL)
            .addPathParameter("api_key", BuildConfig.TSDB_API_KEY)
            .addPathParameter("teamId", idHomeTeam)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    val jsonArray = response.getJSONArray("teams")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObj = jsonArray.getJSONObject(i)
                        view.showHomeTeamBadge(jsonObj.optString("strTeamBadge"))
                    }
                }

                override fun onError(anError: ANError?) {
                    view.showFailedLoad()
                }
            })

        AndroidNetworking.get(APIEndpoint.TEAM_DETAIL)
            .addPathParameter("api_key", BuildConfig.TSDB_API_KEY)
            .addPathParameter("teamId", idAwayTeam)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    val jsonArray = response.getJSONArray("teams")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObj = jsonArray.getJSONObject(i)
                        view.showAwayTeamBadge(jsonObj.optString("strTeamBadge"))
                    }
                }

                override fun onError(anError: ANError?) {
                    view.showFailedLoad()
                }
            })
    }
}