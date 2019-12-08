package com.andrew.footballapplication.ui.league.detail
import com.andrew.footballapplication.APIEndpoint
import com.andrew.footballapplication.BuildConfig
import com.andrew.footballapplication.model.league.LeagueResponse
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener

class LeagueDetailPresenter(private val view: LeagueDetailUI.View) : LeagueDetailUI.Presenter {
    override fun getLeagueDetail(leagueId: Int?) {
        AndroidNetworking.get(APIEndpoint.LEAGUE_DETAIL)
            .addPathParameter("api_key", BuildConfig.TSDB_API_KEY)
            .addPathParameter("leagueId", leagueId.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsObject(LeagueResponse::class.java, object: ParsedRequestListener<LeagueResponse> {
                override fun onResponse(response: LeagueResponse) {
                    for (item in response.results) {
                        view.showLeagueDetail(item)
                    }
                }

                override fun onError(anError: ANError?) {
                    view.showFailedLoad()
                }
            })
    }

}