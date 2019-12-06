package com.andrew.footballapplication.model.league

import com.google.gson.annotations.SerializedName

data class LeagueResponse(
    @SerializedName("leagues")
    val results: MutableList<LeagueItem>
)