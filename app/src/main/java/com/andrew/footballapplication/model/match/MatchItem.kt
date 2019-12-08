package com.andrew.footballapplication.model.match

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MatchItem(
    @SerializedName("idEvent")
    val idEvent: String? = null,

    @SerializedName("strSport")
    val strSport: String? = null,

    @SerializedName("strEvent")
    val eventName: String? = null,

    @SerializedName("intRound")
    val matchRound: String? = null,

    @SerializedName("dateEvent")
    val dateEvent: String? = null,

    @SerializedName("dateEventLocal")
    val dateEventLocal: String? = null,

    @SerializedName("strTime")
    val eventTime: String? = null,

    // HOME
    @SerializedName("idHomeTeam")
    val idHomeTeam: String? = null,

    @SerializedName("strHomeTeam")
    val homeTeam: String? = null,

    @SerializedName("intHomeScore")
    val homeScore: String? = null,

    @SerializedName("strHomeGoalDetails")
    val homeGoalDetails: String? = null,

    @SerializedName("strHomeRedCards")
    val homeRedCards: String? = null,

    @SerializedName("strHomeYellowCards")
    val homeYellowCards: String? = null,

    @SerializedName("strHomeLineupGoalkeeper")
    val homeLineUpGoalkeeper: String? = null,

    @SerializedName("strHomeLineupDefense")
    val homeLineupDefense: String? = null,

    @SerializedName("strHomeLineupMidfield")
    val homeLineupMidfield: String? = null,

    @SerializedName("strHomeLineupForward")
    val homeLineupForward: String? = null,

    @SerializedName("strHomeLineupSubstitutes")
    val homeLineupSubstitutes: String? = null,

    @SerializedName("strHomeFormation")
    val homeFormation: String? = null,

    @SerializedName("intHomeShots")
    val homeShots: String? = null,
    // END HOME

    // AWAY
    @SerializedName("idAwayTeam")
    val idAwayTeam: String? = null,

    @SerializedName("strAwayTeam")
    val awayTeam: String? = null,

    @SerializedName("intAwayScore")
    val awayScore: String? = null,

    @SerializedName("strAwayGoalDetails")
    val awayGoalDetails: String? = null,

    @SerializedName("strAwayRedCards")
    val awayRedCards: String? = null,

    @SerializedName("strAwayYellowCards")
    val awayYellowCards: String? = null,

    @SerializedName("strAwayLineupGoalkeeper")
    val awayLineUpGoalkeeper: String? = null,

    @SerializedName("strAwayLineupDefense")
    val awayLineupDefense: String? = null,

    @SerializedName("strAwayLineupMidfield")
    val awayLineupMidfield: String? = null,

    @SerializedName("strAwayLineupForward")
    val awayLineupForward: String? = null,

    @SerializedName("strAwayLineupSubstitutes")
    val awayLineupSubstitutes: String? = null,

    @SerializedName("strAwayFormation")
    val awayFormation: String? = null,

    @SerializedName("intAwayShots")
    val awayShots: String? = null
    // END AWAY

) : Parcelable