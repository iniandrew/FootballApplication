package com.andrew.footballapplication.ui.match

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrew.footballapplication.R
import com.andrew.footballapplication.adapter.MatchAdapter
import com.andrew.footballapplication.gone
import com.andrew.footballapplication.model.match.MatchItem
import com.andrew.footballapplication.model.match.MatchResponse
import com.andrew.footballapplication.showLoading
import com.andrew.footballapplication.ui.match.detail.MatchDetailActivity
import com.andrew.footballapplication.visible
import org.jetbrains.anko.startActivity

class SearchMatchActivity : AppCompatActivity(), MatchUI.View {

    private var matchList: List<MatchItem> = listOf()
    private lateinit var adapter: MatchAdapter
    private lateinit var presenter: MatchPresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvEmpty: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_match)

        presenter = MatchPresenter(this)
        progressBar = findViewById(R.id.progress_bar)
        recyclerView = findViewById(R.id.rv_match)
        tvEmpty = findViewById(R.id.tv_empty)

        showLoading(false, progressBar)
        setupRecyclerView()

        supportActionBar?.title = resources.getString(R.string.search_event)
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
                getMatchByQuery(query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

    override fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        adapter = MatchAdapter {data ->
            startActivity<MatchDetailActivity>(MatchDetailActivity.EXTRA_MATCH to data.idEvent)
        }
        recyclerView.adapter = adapter
    }

    private fun getMatchByQuery(query: String) {
        presenter.getMatchByQuery(query)
        showLoading(true, progressBar)
    }

    override fun showFailedLoad(error: String) {
        tvEmpty.text = error
        tvEmpty.visible()
        showLoading(false, progressBar)
    }

    override fun showListMatch(matchResponse: MatchResponse) {
        matchList = matchResponse.result.filter {
            it.strSport == "Soccer"
        }
        if (matchList.isEmpty()) {
            tvEmpty.visible()
        } else {
            tvEmpty.gone()
        }
        adapter.setData(matchList)
        showLoading(false, progressBar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
