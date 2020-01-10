package com.andrew.footballapplication.ui.match.classement

import com.andrew.footballapplication.model.classement.ClassementResponse

interface ClassementUI {
    interface View {
        fun setupRecyclerView()
        fun showFailedLoad()
        fun showClassement(classementResponse: ClassementResponse)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun getClassement(leagueId: Int)
    }
}