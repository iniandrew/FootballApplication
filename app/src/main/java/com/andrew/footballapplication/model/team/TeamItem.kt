package com.andrew.footballapplication.model.team

import com.google.gson.annotations.SerializedName

data class TeamItem (
    @SerializedName("idTeam")
    val idTeam: String? = null,

    @SerializedName("strTeamBadge")
    val teamBadge: String? = null,

    @SerializedName("strTeamBanner")
    val teamBanner: String? = null,

    @SerializedName("strTeam")
    val strTeam: String? = null,

    @SerializedName("intFormedYear")
    val intFormedYear: String? = null,

    @SerializedName("strSport")
    val strSport: String? = null,

    @SerializedName("strStadiumThumb")
    val strStadiumThumb: String? = null,

    @SerializedName("strWebsite")
    val strWebsite: String? = null,

    @SerializedName("strDescriptionEN")
    val strDescription: String? = null,

    @SerializedName("strCountry")
    val strCountry: String? = null
)