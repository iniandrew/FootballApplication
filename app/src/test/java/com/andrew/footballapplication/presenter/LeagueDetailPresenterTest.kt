package com.andrew.footballapplication.presenter

import com.andrew.footballapplication.model.league.LeagueItem
import com.andrew.footballapplication.utils.TestContextProvider
import com.andrew.footballapplication.model.league.LeagueResponse
import com.andrew.footballapplication.network.ApiRepository
import com.andrew.footballapplication.ui.league.detail.LeagueDetailPresenter
import com.andrew.footballapplication.ui.league.detail.LeagueDetailUI
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

class LeagueDetailPresenterTest {
    @Mock private lateinit var view: LeagueDetailUI.View
    @Mock private lateinit var gson: Gson
    @Mock private lateinit var apiRepository: ApiRepository
    @Mock private lateinit var apiResponse: Deferred<String>
    private lateinit var presenter: LeagueDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LeagueDetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetLeagueDetail() {
        val leagueId = 4328
        val leagues: MutableList<LeagueItem> = mutableListOf()
        val response = LeagueResponse(leagues)

        runBlocking {
            `when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            `when`(apiResponse.await()).thenReturn("")
            `when`(
                gson.fromJson("", LeagueResponse::class.java)
            ).thenReturn(response)

            presenter.getLeagueDetail(leagueId)
            verify(view).showLoading()
            for (item in leagues) {
                verify(view).showLeagueDetail(item)
            }
            verify(view).hideLoading()
        }
    }
}