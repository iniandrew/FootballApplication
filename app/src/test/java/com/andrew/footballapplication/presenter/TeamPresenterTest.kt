package com.andrew.footballapplication.presenter

import com.andrew.footballapplication.model.team.TeamItem
import com.andrew.footballapplication.model.team.TeamResponse
import com.andrew.footballapplication.network.ApiRepository
import com.andrew.footballapplication.ui.team.TeamPresenter
import com.andrew.footballapplication.ui.team.TeamUI
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

class TeamPresenterTest {
    @Mock private lateinit var view: TeamUI.View
    @Mock private lateinit var apiRepository: ApiRepository
    @Mock private lateinit var gson: Gson
    @Mock private lateinit var apiResponse: Deferred<String>
    private lateinit var presenter: TeamPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetTeamList() {
        val leagueId = 4328
        val teams: MutableList<TeamItem> = mutableListOf()
        val response = TeamResponse(teams)

        runBlocking {
            `when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            `when`(apiResponse.await()).thenReturn("")
            `when`(gson.fromJson("", TeamResponse::class.java)).thenReturn(response)

            presenter.getTeamList(leagueId)

            verify(view).showLoading()
            verify(view).showTeamList(response)
            verify(view).hideLoading()
        }
    }

    @Test
    fun testGetTeamByQuery() {
        val query = "Arsenal"
        val teams: MutableList<TeamItem> = mutableListOf()
        val response = TeamResponse(teams)

        runBlocking {
            `when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            `when`(apiResponse.await()).thenReturn("")
            `when`(gson.fromJson("", TeamResponse::class.java)).thenReturn(response)

            presenter.getTeamByQuery(query)

            verify(view).showLoading()
            verify(view).showTeamList(response)
            verify(view).hideLoading()
        }
    }
}