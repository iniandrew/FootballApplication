package com.andrew.footballapplication.ui.match.next


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.andrew.footballapplication.R
import com.andrew.footballapplication.adapter.MatchAdapter
import com.andrew.footballapplication.model.match.MatchItem
import com.andrew.footballapplication.model.match.MatchResponse
import com.andrew.footballapplication.showLoading
import com.andrew.footballapplication.ui.match.MatchPresenter
import com.andrew.footballapplication.ui.match.MatchUI
import com.andrew.footballapplication.ui.match.detail.MatchDetailActivity
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class NextMatchFragment : Fragment(), MatchUI.View {

    private var matchList: MutableList<MatchItem> = mutableListOf()
    private lateinit var progressBar: ProgressBar
    private lateinit var rvNextMatch: RecyclerView
    private lateinit var adapter: MatchAdapter
    private lateinit var presenter: MatchPresenter
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.next_match_progressBar)
        rvNextMatch = view.findViewById(R.id.rv_next_match)
        spinner = view.findViewById(R.id.spinner)
        presenter = MatchPresenter(this)

        setupSpinner()
        setupRecyclerView()
        getPreviousMatch()
    }

    private fun setupSpinner() {
        val spinnerItems = resources.getStringArray(R.array.league_name)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter
    }

    private fun getPreviousMatch() {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val leagueId = resources.getIntArray(R.array.league_id)
                presenter.getNextMatch(leagueId[p2])
                showLoading(true, progressBar)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }
    }

    override fun setupRecyclerView() {
        rvNextMatch.layoutManager = LinearLayoutManager(context)
        rvNextMatch.setHasFixedSize(true)
        adapter = MatchAdapter {data ->
            startActivity<MatchDetailActivity>(MatchDetailActivity.EXTRA_MATCH to data.idEvent)
        }
        rvNextMatch.adapter = adapter
    }

    override fun showFailedLoad() {
        Toast.makeText(context, resources.getString(R.string.failed_load_data), Toast.LENGTH_SHORT).show()
    }

    override fun showListMatch(matchResponse: MatchResponse) {
        matchList = matchResponse.results
        adapter.setData(matchList)
        showLoading(false, progressBar)
    }
}
