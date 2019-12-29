package com.andrew.footballapplication.model.team

import com.google.gson.annotations.SerializedName

data class TeamItem (
    @SerializedName("strTeamBadge")
    val teamBadge: String? = null
)