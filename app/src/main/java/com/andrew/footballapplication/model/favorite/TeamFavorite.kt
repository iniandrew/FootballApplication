package com.andrew.footballapplication.model.favorite

data class TeamFavorite(
    val id: Long?,
    val idTeam: String?,
    val teamName: String?,
    val teamDesc: String?,
    val teamBadge: String?
) {
    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE_TEAM"
        const val ID: String = "ID_"
        const val ID_TEAM: String = "ID_TEAM"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_DESC: String = "TEAM_DESC"
        const val TEAM_BADGE: String = "TEAM_BADGE"
    }
}