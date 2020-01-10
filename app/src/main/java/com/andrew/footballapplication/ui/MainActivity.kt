package com.andrew.footballapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andrew.footballapplication.R
import com.andrew.footballapplication.ui.favorite.FavoriteFragment
import com.andrew.footballapplication.ui.league.LeagueFragment
import com.andrew.footballapplication.ui.match.MatchFragment
import com.andrew.footballapplication.ui.team.TeamFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.navigation_league -> {
                    loadLeagueFragment()
                }
                R.id.navigation_match -> {
                    loadMatchFragment()
                }
                R.id.navigation_team -> {
                    loadTeamFragment()
                }
                R.id.navigation_favorite -> {
                    loadFavoriteFragment()
                }
            }
            true
        }

        if (savedInstanceState == null) {
            bottomNavigation.selectedItemId = R.id.navigation_league
        }
    }

    private fun loadLeagueFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, LeagueFragment()).commit()
    }

    private fun loadMatchFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, MatchFragment()).commit()
    }

    private fun loadTeamFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, TeamFragment()).commit()
    }

    private fun loadFavoriteFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, FavoriteFragment()).commit()
    }
}
