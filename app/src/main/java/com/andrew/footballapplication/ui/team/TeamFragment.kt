package com.andrew.footballapplication.ui.team


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
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
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class TeamFragment : Fragment(), TeamUI.View {

    private var teamList: MutableList<TeamItem> = mutableListOf()
    private lateinit var progressBar: ProgressBar
    private lateinit var rvTeams: RecyclerView
    private lateinit var adapter: TeamAdapter
    private lateinit var presenter: TeamPresenter
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.teams_progressBar)
        rvTeams = view.findViewById(R.id.rv_teams)
        spinner = view.findViewById(R.id.spinner)
        presenter = TeamPresenter(this, ApiRepository(), Gson())

        setupSpinner()
        setupRecyclerView()
        getTeamList()

        setHasOptionsMenu(true)
    }

    private fun setupSpinner() {
        val spinnerItems = resources.getStringArray(R.array.league_name)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter
    }

    private fun getTeamList() {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val leagueId = resources.getIntArray(R.array.league_id)
                presenter.getTeamList(leagueId[p2])
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }
    }

    override fun setupRecyclerView() {
        rvTeams.layoutManager = LinearLayoutManager(context)
        rvTeams.setHasFixedSize(true)
        adapter = TeamAdapter {data ->
            startActivity<TeamDetailActivity>(TeamDetailActivity.EXTRA_TEAM to data.idTeam)
        }
        rvTeams.adapter = adapter
    }

    override fun showFailedLoad() {
        Toast.makeText(context, resources.getString(R.string.failed_load_data), Toast.LENGTH_SHORT).show()
    }

    override fun showTeamList(teamResponse: TeamResponse) {
        teamList = teamResponse.results
        adapter.setData(teamList)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.gone()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_action_search -> {
                context?.startActivity<SearchTeamActivity>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
