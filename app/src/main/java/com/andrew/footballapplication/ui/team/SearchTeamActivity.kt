package com.andrew.footballapplication.ui.team

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrew.footballapplication.R
import com.andrew.footballapplication.adapter.TeamAdapter
import com.andrew.footballapplication.model.team.TeamItem
import com.andrew.footballapplication.model.team.TeamResponse
import com.andrew.footballapplication.network.ApiRepository
import com.andrew.footballapplication.ui.team.detail.TeamDetailActivity
import com.andrew.footballapplication.utils.gone
import com.andrew.footballapplication.utils.visible
import com.google.gson.Gson
import org.jetbrains.anko.appcompat.v7.coroutines.onClose
import org.jetbrains.anko.startActivity

class SearchTeamActivity : AppCompatActivity(), TeamUI.View {

    private var teamList: List<TeamItem> = listOf()
    private lateinit var adapter: TeamAdapter
    private lateinit var presenter: TeamPresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvEmpty: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_team)

        presenter = TeamPresenter(this, ApiRepository(), Gson())
        progressBar = findViewById(R.id.progress_bar)
        recyclerView = findViewById(R.id.rv_teams)
        tvEmpty = findViewById(R.id.tv_empty)

        setupRecyclerView()
        progressBar.gone()

        supportActionBar?.title = resources.getString(R.string.search_team)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem =  menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.isIconified = false
        searchMatch(searchView)
        return super.onCreateOptionsMenu(menu)
    }

    private fun searchMatch(searchView: SearchView) {
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                getTeamByQuery(query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean = false
        })
        searchView.onClose {
            onBackPressed()
        }
    }

    override fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        adapter = TeamAdapter {data ->
            startActivity<TeamDetailActivity>(TeamDetailActivity.EXTRA_TEAM to data.idTeam)
        }
        recyclerView.adapter = adapter
    }

    private fun getTeamByQuery(query: String) {
        presenter.getTeamByQuery(query)
    }

    override fun showFailedLoad() {
        Toast.makeText(applicationContext, resources.getString(R.string.failed_load_data), Toast.LENGTH_SHORT).show()
        tvEmpty.visible()
    }

    override fun showTeamList(teamResponse: TeamResponse) {
        teamList = teamResponse.results.filter {
            it.strSport == "Soccer"
        }
        if (teamList.isEmpty()) {
            tvEmpty.visible()
        } else {
            tvEmpty.gone()
        }
        adapter.setData(teamList)
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
