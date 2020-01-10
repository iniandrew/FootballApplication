package com.andrew.footballapplication.ui.match.classement

import com.andrew.footballapplication.model.classement.ClassementResponse
import com.andrew.footballapplication.network.ApiRepository
import com.andrew.footballapplication.network.TheSportDBApi
import com.andrew.footballapplication.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class ClassementPresenter (
    private val view: ClassementUI.View,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : ClassementUI.Presenter {

    override fun getClassement(leagueId: Int) {
        view.showLoading()
        GlobalScope.launch(context.main ) {
            try {
                val data = gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getClassement(leagueId)).await(),
                    ClassementResponse::class.java
                )
                view.showClassement(data)
                view.hideLoading()
            } catch (e: IOException) {
                view.showFailedLoad()
            }
        }
    }

}