package com.andrew.footballapplication.ui.match.classement


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.andrew.footballapplication.R
import com.andrew.footballapplication.adapter.ClassementAdapter
import com.andrew.footballapplication.model.classement.ClassementItem
import com.andrew.footballapplication.model.classement.ClassementResponse
import com.andrew.footballapplication.network.ApiRepository
import com.andrew.footballapplication.utils.gone
import com.andrew.footballapplication.utils.visible
import com.google.gson.Gson

/**
 * A simple [Fragment] subclass.
 */
class ClassementFragment : Fragment(), ClassementUI.View {

    private var classementList: MutableList<ClassementItem> = mutableListOf()
    private lateinit var progressBar: ProgressBar
    private lateinit var rvNextMatch: RecyclerView
    private lateinit var adapter: ClassementAdapter
    private lateinit var presenter: ClassementPresenter
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_classement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.classement_progressBar)
        rvNextMatch = view.findViewById(R.id.rv_classement)
        spinner = view.findViewById(R.id.spinner)
        presenter = ClassementPresenter(this, ApiRepository(), Gson())

        setupSpinner()
        setupRecyclerView()
        getClassement()
    }

    private fun setupSpinner() {
        val spinnerItems = resources.getStringArray(R.array.league_name)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter
    }

    private fun getClassement() {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val leagueId = resources.getIntArray(R.array.league_id)
                presenter.getClassement(leagueId[p2])
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }
    }

    override fun setupRecyclerView() {
        rvNextMatch.layoutManager = LinearLayoutManager(context)
        rvNextMatch.setHasFixedSize(true)
        adapter = ClassementAdapter()
        rvNextMatch.adapter = adapter
    }

    override fun showFailedLoad() {
        Toast.makeText(context, resources.getString(R.string.failed_load_data), Toast.LENGTH_SHORT).show()
    }

    override fun showClassement(classementResponse: ClassementResponse) {
        classementList = classementResponse.results
        adapter.setData(classementList)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.gone()
    }

}
