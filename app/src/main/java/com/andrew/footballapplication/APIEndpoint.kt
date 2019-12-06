package com.andrew.footballapplication

class APIEndpoint  {
    companion object {
        const val LEAGUE_DETAIL = BuildConfig.BASE_URL + "api/v1/json/{api_key}/lookupleague.php?id={leagueId}"
        const val MATCH_DETAIL = BuildConfig.BASE_URL + "api/v1/json/1/lookupevent.php?id={eventId}"
        const val TEAM_DETAIL = BuildConfig.BASE_URL + "api/v1/json/1/lookupteam.php?id={teamId}"
        const val PREVIOUS_MATCH = BuildConfig.BASE_URL + "api/v1/json/{api_key}/eventspastleague.php?id={leagueId}"
        const val NEXT_MATCH = BuildConfig.BASE_URL + "api/v1/json/{api_key}/eventsnextleague.php?id={leagueId}"
        const val SEARCH_MATCH = BuildConfig.BASE_URL + "api/v1/json/{api_key}/searchevents.php?e={query}"
    }
}