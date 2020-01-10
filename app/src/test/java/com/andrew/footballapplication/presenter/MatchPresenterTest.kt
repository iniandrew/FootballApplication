package com.andrew.footballapplication.presenter

import com.andrew.footballapplication.model.match.MatchItem
import com.andrew.footballapplication.model.match.MatchResponse
import com.andrew.footballapplication.network.ApiRepository
import com.andrew.footballapplication.ui.match.MatchPresenter
import com.andrew.footballapplication.ui.match.MatchUI
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

class MatchPresenterTest {
    @Mock private lateinit var view: MatchUI.View
    @Mock private lateinit var apiRepository: ApiRepository
    @Mock private lateinit var gson: Gson
    @Mock private lateinit var apiResponse: Deferred<String>
    private lateinit var presenter: MatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetPreviousMatch() {
        val leagueId = 4328
        val matchs: MutableList<MatchItem> = mutableListOf()
        val response = MatchResponse(matchs, matchs)

        runBlocking {
            `when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            `when`(apiResponse.await()).thenReturn("")
            `when`(gson.fromJson("", MatchResponse::class.java)).thenReturn(response)

            presenter.getPreviousMatch(leagueId)

            verify(view).showLoading()
            verify(view).showListMatch(response)
            verify(view).hideLoading()
        }
    }

    @Test
    fun testGetNextMatch() {
        val leagueId = 4328
        val matchs: MutableList<MatchItem> = mutableListOf()
        val response = MatchResponse(matchs, matchs)

        runBlocking {
            `when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            `when`(apiResponse.await()).thenReturn("")
            `when`(gson.fromJson("", MatchResponse::class.java)).thenReturn(response)

            presenter.getNextMatch(leagueId)

            verify(view).showLoading()
            verify(view).showListMatch(response)
            verify(view).hideLoading()
        }
    }

    @Test
    fun testGetMatchByQuery() {
        val query = "Arsenal"
        val matchs: MutableList<MatchItem> = mutableListOf()
        val response = MatchResponse(matchs, matchs)

        runBlocking {
            `when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            `when`(apiResponse.await()).thenReturn("")
            `when`(gson.fromJson("", MatchResponse::class.java)).thenReturn(response)

            presenter.getMatchByQuery(query)

            verify(view).showLoading()
            verify(view).showListMatch(response)
            verify(view).hideLoading()
        }
    }
}