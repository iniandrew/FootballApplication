package com.andrew.footballapplication.ui.team.detail

import android.database.sqlite.SQLiteConstraintException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.andrew.footballapplication.R
import com.andrew.footballapplication.database.database
import com.andrew.footballapplication.model.favorite.TeamFavorite
import com.andrew.footballapplication.model.team.TeamItem
import com.andrew.footballapplication.network.ApiRepository
import com.andrew.footballapplication.utils.gone
import com.andrew.footballapplication.utils.visible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_team_detail.*
import kotlinx.android.synthetic.main.activity_team_detail.tv_team_name
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class TeamDetailActivity : AppCompatActivity(), TeamDetailUI.View {

    companion object {
        const val EXTRA_TEAM = "extra_team"
    }

    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null
    private lateinit var teamId: String

    private var teamName: String? = null
    private var teamBadge: String? = null
    private var teamDesc: String? = null

    private lateinit var presenter: TeamDetailPresenter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        teamId = intent.getStringExtra(EXTRA_TEAM)!!
        progressBar = findViewById(R.id.progress_bar)
        presenter = TeamDetailPresenter(this, ApiRepository(), Gson())

        favoriteState()

        supportActionBar?.title = teamName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getTeamDetail() {
        presenter.getTeamDetail(teamId.toInt())
        menuItem?.findItem(R.id.add_to_favorite)?.isVisible = false
    }

    override fun showFailedLoad() {
        Toast.makeText(applicationContext, resources.getString(R.string.failed_load_data), Toast.LENGTH_SHORT).show()
    }

    override fun showTeamDetail(item: TeamItem) {
        teamName = item.strTeam
        teamBadge = item.teamBadge
        teamDesc = item.strDescription

        Glide.with(this).load(item.strStadiumThumb).into(img_team_banner)
        Glide.with(this).load(item.teamBadge).apply(RequestOptions().override(150, 150)).into(img_team_badge)
        tv_team_name.text = item.strTeam
        tv_team_overview.text = item.strDescription
        tv_team_formed_year.text = item.intFormedYear
        tv_team_country.text = item.strCountry
        tv_team_website.text = item.strWebsite

        menuItem?.findItem(R.id.add_to_favorite)?.isVisible = true
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
        getTeamDetail()
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
            val result = select(TeamFavorite.TABLE_FAVORITE)
                .whereArgs("(ID_TEAM = {id})",
                    "id" to teamId)
            val favorite = result.parseList(classParser<TeamFavorite>())
            if (favorite.isNotEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    TeamFavorite.TABLE_FAVORITE,
                    TeamFavorite.ID_TEAM to teamId,
                    TeamFavorite.TEAM_NAME to tv_team_name.text,
                    TeamFavorite.TEAM_DESC to teamDesc,
                    TeamFavorite.TEAM_BADGE to teamBadge
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
                    TeamFavorite.TABLE_FAVORITE, "(ID_TEAM = {id})",
                    "id" to teamId)
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
