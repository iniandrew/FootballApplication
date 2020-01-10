package com.andrew.footballapplication.presenter

import com.andrew.footballapplication.model.team.TeamItem
import com.andrew.footballapplication.model.team.TeamResponse
import com.andrew.footballapplication.network.ApiRepository
import com.andrew.footballapplication.ui.team.detail.TeamDetailPresenter
import com.andrew.footballapplication.ui.team.detail.TeamDetailUI
import com.andrew.footballapplication.utils.TestContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class TeamDetailPresenterTest {
    @Mock private lateinit var view: TeamDetailUI.View
    @Mock private lateinit var apiRepository: ApiRepository
    @Mock private lateinit var gson: Gson
    @Mock private lateinit var apiResponse: Deferred<String>
    private lateinit var presenter: TeamDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamDetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetPresenter() {
        val teamId = 133604
        val teams: MutableList<TeamItem> = mutableListOf()
        val response = TeamResponse(teams)

        runBlocking {
            `when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            `when`(apiResponse.await()).thenReturn("")
            `when`(gson.fromJson("", TeamResponse::class.java)).thenReturn(response)

            presenter.getTeamDetail(teamId)

            verify(view).showLoading()
            for (item in teams) {
                verify(view).showTeamDetail(item)
            }
            verify(view).hideLoading()
        }
    }
}