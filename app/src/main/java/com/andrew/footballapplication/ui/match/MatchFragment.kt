package com.andrew.footballapplication.ui.match


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager

import com.andrew.footballapplication.R
import com.andrew.footballapplication.adapter.ViewPagerAdapter
import com.andrew.footballapplication.ui.match.next.NextMatchFragment
import com.andrew.footballapplication.ui.match.previous.PreviousMatchFragment
import com.google.android.material.tabs.TabLayout
import org.jetbrains.anko.startActivity

/**
 * A simple [Fragment] subclass.
 */
class MatchFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = view.findViewById(R.id.viewPager)
        tabLayout = view.findViewById(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
        setupViewPager(viewPager)
        setHasOptionsMenu(true)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager,
            mapOf(
                getString(R.string.previous_match) to PreviousMatchFragment(),
                getString(R.string.next_match) to NextMatchFragment()
            ))
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_action_search -> {
                context?.startActivity<SearchMatchActivity>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
