package com.andrew.footballapplication.model.match

import com.google.gson.annotations.SerializedName

data class MatchResponse(
    @SerializedName("events")
    val results: MutableList<MatchItem>,

    @SerializedName("event")
    val result: MutableList<MatchItem>
)