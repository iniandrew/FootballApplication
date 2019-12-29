package com.andrew.footballapplication.ui.league.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.andrew.footballapplication.R
import com.andrew.footballapplication.utils.gone
import com.andrew.footballapplication.model.league.LeagueItem
import com.andrew.footballapplication.network.ApiRepository
import com.andrew.footballapplication.utils.visible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson

class LeagueDetailActivity : AppCompatActivity(), LeagueDetailUI.View {

    companion object {
        const val EXTRA_LEAGUE_ID = "extra_league_id"
    }

    private lateinit var presenter: LeagueDetailPresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var imgLeagueBanner: ImageView
    private lateinit var imgLeagueBadge: ImageView
    private lateinit var tvLeagueName: TextView
    private lateinit var tvLeagueDescription: TextView
    private lateinit var tvLeagueFormedYear: TextView
    private lateinit var tvLeagueCountry: TextView
    private lateinit var tvLeagueWebsite: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league_detail)

        progressBar = findViewById(R.id.progressBar)
        imgLeagueBanner = findViewById(R.id.img_league_banner)
        imgLeagueBadge = findViewById(R.id.img_league_badge)
        tvLeagueName = findViewById(R.id.tv_league_name)
        tvLeagueDescription = findViewById(R.id.tv_league_overview)
        tvLeagueFormedYear = findViewById(R.id.tv_league_formed_year)
        tvLeagueCountry = findViewById(R.id.tv_league_country)
        tvLeagueWebsite = findViewById(R.id.tv_league_website)

        getLeagueDetail()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getLeagueDetail() {
        presenter = LeagueDetailPresenter(this, ApiRepository(), Gson())
        presenter.getLeagueDetail(intent.getIntExtra(EXTRA_LEAGUE_ID, 0))
    }

    override fun showFailedLoad() {
        Toast.makeText(applicationContext, resources.getString(R.string.failed_load_data), Toast.LENGTH_SHORT).show()
    }

    override fun showLeagueDetail(item: LeagueItem) {
        Glide.with(this).load(item.leagueBanner).into(imgLeagueBanner)
        Glide.with(this).load(item.leagueBadge).apply(RequestOptions().override(150, 150)).into(imgLeagueBadge)
        tvLeagueName.text = item.leagueName
        tvLeagueDescription.text = item.leagueDescription
        tvLeagueCountry.text = item.leagueCountry
        tvLeagueFormedYear.text = item.leagueFormedYear
        tvLeagueWebsite.text = item.leagueWebsite
        supportActionBar?.title = item.leagueName
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.gone()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
