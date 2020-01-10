package com.andrew.footballapplication.presenter

import com.andrew.footballapplication.model.classement.ClassementItem
import com.andrew.footballapplication.model.classement.ClassementResponse
import com.andrew.footballapplication.network.ApiRepository
import com.andrew.footballapplication.ui.match.classement.ClassementPresenter
import com.andrew.footballapplication.ui.match.classement.ClassementUI
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

class ClassementPresenterTest {
    @Mock private lateinit var view: ClassementUI.View
    @Mock private lateinit var apiRepository: ApiRepository
    @Mock private lateinit var gson: Gson
    @Mock private lateinit var apiResponse: Deferred<String>
    private lateinit var presenter: ClassementPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = ClassementPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetPresenter() {
        val leagueId = 4328
        val classements: MutableList<ClassementItem> = mutableListOf()
        val response = ClassementResponse(classements)

        runBlocking {
            `when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            `when`(apiResponse.await()).thenReturn("")
            `when`(gson.fromJson("", ClassementResponse::class.java)).thenReturn(response)

            presenter.getClassement(leagueId)

            verify(view).showLoading()
            verify(view).showClassement(response)
            verify(view).hideLoading()
        }
    }
}