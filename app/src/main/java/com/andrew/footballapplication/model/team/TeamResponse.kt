package com.andrew.footballapplication.model.team

import com.google.gson.annotations.SerializedName

data class TeamResponse(
    @SerializedName("teams")
    val results: MutableList<TeamItem>
)