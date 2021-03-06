package com.andrew.footballapplication.ui.match.detail

import android.database.sqlite.SQLiteConstraintException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.andrew.footballapplication.*
import com.andrew.footballapplication.database.database
import com.andrew.footballapplication.model.team.TeamItem
import com.andrew.footballapplication.model.favorite.MatchFavorite
import com.andrew.footballapplication.model.match.MatchItem
import com.andrew.footballapplication.network.ApiRepository
import com.andrew.footballapplication.utils.getDateFormat
import com.andrew.footballapplication.utils.getTimeFormat
import com.andrew.footballapplication.utils.gone
import com.andrew.footballapplication.utils.visible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class MatchDetailActivity : AppCompatActivity(), MatchDetailUI.View {

    companion object {
        const val EXTRA_MATCH = "extra_match"
    }

    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null
    private lateinit var matchId: String

    private var homeTeam: String? = null
    private var awayTeam: String? = null
    private var eventName: String? = null
    private var eventTime: String? = null
    private var homeScore: String? = null
    private var awayScore: String? = null
    private lateinit var presenter: MatchDetailPresenter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        matchId = intent.getStringExtra(EXTRA_MATCH)!!
        progressBar = findViewById(R.id.progress_bar)
        presenter = MatchDetailPresenter(this, ApiRepository(), Gson())

        favoriteState()

        supportActionBar?.title = resources.getString(R.string.match_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getMatchDetail() {
        presenter.getMatchDetail(matchId)
        menuItem?.findItem(R.id.add_to_favorite)?.isVisible = false
    }

    private fun setupItemHomeTeam(item: MatchItem) {
        homeScore = item.homeScore
        homeTeam = item.homeTeam
        tv_home_team.text = homeTeam
        tv_home_score.text = homeScore ?: "-"
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
        awayTeam = item.awayTeam
        awayScore = item.awayScore
        tv_away_team.text = awayTeam
        tv_away_score.text = awayScore ?: "-"
        tv_away_formation.text = item.awayFormation ?: "-"
        tv_away_goal_details.text = item.awayGoalDetails?.replace(';', ',') ?: "-"
        tv_away_shots.text = item.awayShots?.replace(';', ',') ?: "-"
        tv_away_goalKeeper.text = item.awayLineUpGoalkeeper?.replace(';', ',') ?: "-"
        tv_away_defense.text = item.awayLineupDefense?.replace(';', ',') ?: "-"
        tv_away_midfield.text = item.awayLineupMidfield?.replace(';', ',') ?: "-"
        tv_away_forward.text = item.awayLineupForward?.replace(';', ',') ?: "-"
        tv_away_substitutes.text = item.awayLineupSubstitutes?.replace(';', ',') ?: "-"
    }

    override fun showFailedLoad() {
        Toast.makeText(applicationContext, resources.getString(R.string.failed_load_data), Toast.LENGTH_SHORT).show()
    }

    override fun showMatchDetail(matchItem: MatchItem) {
        eventName = matchItem.eventName
        eventTime = matchItem.eventTime
        tv_date_event.text = getDateFormat(matchItem.dateEvent)
        tv_time_event.text = getTimeFormat(matchItem.eventTime)
        setupItemHomeTeam(matchItem)
        setupItemAwayTeam(matchItem)

        presenter.getHomeTeamBadge(matchItem.idHomeTeam!!)
        presenter.getAwayTeamBadge(matchItem.idAwayTeam!!)
        menuItem?.findItem(R.id.add_to_favorite)?.isVisible = true
    }

    override fun showHomeTeamBadge(homeTeamBadge: List<TeamItem>) {
        Glide.with(applicationContext).load(homeTeamBadge[0].teamBadge).apply(RequestOptions().override(250,250)).into(img_home_badge)
    }

    override fun showAwayTeamBadge(awayTeamBadge: List<TeamItem>) {
        Glide.with(applicationContext).load(awayTeamBadge[0].teamBadge).apply(RequestOptions().override(250,250)).into(img_away_badge)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.gone()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        getMatchDetail()
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(MatchFavorite.TABLE_FAVORITE)
                .whereArgs("(ID_EVENT = {id})",
                    "id" to matchId)
            val favorite = result.parseList(classParser<MatchFavorite>())
            if (favorite.isNotEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    MatchFavorite.TABLE_FAVORITE,
                    MatchFavorite.ID_EVENT to matchId,
                    MatchFavorite.TIME_EVENT to eventTime,
                    MatchFavorite.HOME_TEAM to homeTeam,
                    MatchFavorite.HOME_SCORE to homeScore,
                    MatchFavorite.AWAY_TEAM to awayTeam,
                    MatchFavorite.AWAY_SCORE to awayScore
                )
            }
            Toast.makeText(this, resources.getText(R.string.add_to_favorite).toString(), Toast.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(
                    MatchFavorite.TABLE_FAVORITE, "(ID_EVENT = {id})",
                    "id" to matchId)
            }
            Toast.makeText(this, resources.getText(R.string.remove_from_favorite).toString(), Toast.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
