package com.andrew.footballapplication.model.league

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LeagueItem(
    @SerializedName("idLeague")
    val leagueId: Int? = null,

    @SerializedName("strBadge")
    val leagueBadge: String? = null,

    @SerializedName("strLeague")
    var leagueName: String? = null,

    @SerializedName("strDescriptionEN")
    var leagueDescription: String? = null,

    @SerializedName("strFanart1")
    val leagueBanner: String? = null,

    @SerializedName("intFormedYear")
    val leagueFormedYear: String? = null,

    @SerializedName("strWebsite")
    val leagueWebsite: String? = null,

    @SerializedName("strCountry")
    val leagueCountry: String? = null
) : Parcelable