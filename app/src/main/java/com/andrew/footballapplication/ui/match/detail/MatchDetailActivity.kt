package com.andrew.footballapplication.ui.match.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import com.andrew.footballapplication.R
import com.andrew.footballapplication.getDateFormat
import com.andrew.footballapplication.getTimeFormat
import com.andrew.footballapplication.model.TeamItem
import com.andrew.footballapplication.model.match.MatchItem
import com.andrew.footballapplication.showLoading
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_match_detail.*

class MatchDetailActivity : AppCompatActivity(), MatchDetailUI.View {

    companion object {
        const val EXTRA_MATCH = "extra_match"
    }

    private lateinit var teamItem: TeamItem
    private lateinit var presenter: MatchDetailPresenter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        progressBar = findViewById(R.id.progress_bar)
        presenter = MatchDetailPresenter(this)

        getMatchDetail()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getMatchDetail() {
        presenter.getMatchDetail(intent.getIntExtra(EXTRA_MATCH, 0))
        showLoading(true, progressBar)
    }

    private fun setupItemHomeTeam(item: MatchItem) {
        tv_home_team.text = item.homeTeam
        tv_home_score.text = item.homeScore ?: "-"
        tv_home_formation.text = item.homeFormation ?: "-"
        tv_home_goal_details.text = item.homeGoalDetails?.replace(';', ',') ?: "-"
        tv_home_shots.text = item.homeShots?.replace(';', ',') ?: "-"
        tv_home_goalKeeper.text = item.homeLineUpGoalkeeper?.replace(';', ',') ?: "-"
        tv_home_defense.text = item.homeLineupDefense?.replace(';', ',') ?: "-"
        tv_home_midfield.text = item.homeLineupMidfield?.replace(';', ',') ?: "-"
        tv_home_forward.text = item.homeLineupForward?.replace(';', ',') ?: "-"
        tv_home_substitutes.text = item.homeLineupSubstitutes?.replace(';', ',') ?: "-"
    }

    private fun setupItemAwayTeam(item: MatchItem) {
        tv_away_team.text = item.awayTeam
        tv_away_score.text = item.awayScore ?: "-"
        tv_away_formation.text = item.awayFormation ?: "-"
        tv_away_goal_details.text = item.awayGoalDetails?.replace(';', ',') ?: "-"
        tv_away_shots.text = item.awayShots?.replace(';', ',') ?: "-"
        tv_away_goalKeeper.text = item.awayLineUpGoalkeeper?.replace(';', ',') ?: "-"
        tv_away_defense.text = item.awayLineupDefense?.replace(';', ',') ?: "-"
        tv_away_midfield.text = item.awayLineupMidfield?.replace(';', ',') ?: "-"
        tv_away_forward.text = item.awayLineupForward?.replace(';', ',') ?: "-"
        tv_away_substitutes.text = item.awayLineupSubstitutes?.replace(';', ',') ?: "-"
    }

    override fun showFailedLoad(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun showMatchDetail(matchItem: MatchItem) {
        tv_date_event.text = getDateFormat(matchItem.dateEvent)
        tv_time_event.text = getTimeFormat(matchItem.eventTime)
        setupItemHomeTeam(matchItem)
        setupItemAwayTeam(matchItem)
        presenter.getTeamDetail(matchItem.idHomeTeam!!, matchItem.idAwayTeam!!)

        showLoading(false, progressBar)
    }

    override fun showHomeTeamBadge(homeTeamBadge: String?) {
        teamItem = TeamItem(homeTeamBadge)
        Glide.with(applicationContext).load(teamItem.strHomeTeamBadge).apply(RequestOptions().override(250,250)).into(img_home_badge)
    }

    override fun showAwayTeamBadge(awayTeamBadge: String?) {
        teamItem = TeamItem(null, awayTeamBadge)
        Glide.with(applicationContext).load(teamItem.strAwayTeamBadge).apply(RequestOptions().override(250,250)).into(img_away_badge)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
