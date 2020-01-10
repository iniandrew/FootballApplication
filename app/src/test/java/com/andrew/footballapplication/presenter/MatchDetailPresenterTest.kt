package com.andrew.footballapplication.presenter

import com.andrew.footballapplication.model.match.MatchItem
import com.andrew.footballapplication.model.match.MatchResponse
import com.andrew.footballapplication.model.team.TeamItem
import com.andrew.footballapplication.model.team.TeamResponse
import com.andrew.footballapplication.network.ApiRepository
import com.andrew.footballapplication.ui.match.detail.MatchDetailPresenter
import com.andrew.footballapplication.ui.match.detail.MatchDetailUI
import com.andrew.footballapplication.utils.TestContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class MatchDetailPresenterTest {
    @Mock private lateinit var view: MatchDetailUI.View
    @Mock private lateinit var gson: Gson
    @Mock private lateinit var apiRepository: ApiRepository
    @Mock private lateinit var apiResponse: Deferred<String>
    private lateinit var presenter: MatchDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchDetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetMatchDetail() {
        val eventId = "441613"
        val match: MutableList<MatchItem> = mutableListOf()
        val response = MatchResponse(match,match)

        runBlocking {
            `when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            `when`(apiResponse.await()).thenReturn("")
            `when`(
                gson.fromJson("", MatchResponse::class.java)
            ).thenReturn(response)

            presenter.getMatchDetail(eventId)
            verify(view).showLoading()
            for (item in match) {
                verify(view).showMatchDetail(item)
            }
            verify(view).hideLoading()
        }

    }

    @Test
    fun testGetHomeTeamBadge() {
        val homeTeamId = "133624"
        val teamBadge: MutableList<TeamItem> = mutableListOf()
        val response = TeamResponse(teamBadge)

        runBlocking {
            `when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            `when`(apiResponse.await()).thenReturn("")
            `when`(
                gson.fromJson("", TeamResponse::class.java)
            ).thenReturn(response)

            presenter.getHomeTeamBadge(homeTeamId)
            verify(view).showHomeTeamBadge(teamBadge)
        }
    }

    @Test
    fun testGetAwayTeamBadge() {
        val homeTeamId = "133612"
        val teamBadge: MutableList<TeamItem> = mutableListOf()
        val response = TeamResponse(teamBadge)

        runBlocking {
            `when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            `when`(apiResponse.await()).thenReturn("")
            `when`(
                gson.fromJson("", TeamResponse::class.java)
            ).thenReturn(response)

            presenter.getAwayTeamBadge(homeTeamId)
            verify(view).showAwayTeamBadge(teamBadge)
        }
    }
}