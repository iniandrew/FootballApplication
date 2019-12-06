package com.andrew.footballapplication.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager, private val map: Map<String, Fragment>): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val mFragmentTitleList = map.keys.toList()
    private val mFragmentList = map.values.toList()

    override fun getItem(position: Int): Fragment = mFragmentList[position]

    override fun getCount(): Int = map.size

    override fun getPageTitle(position: Int): CharSequence? = mFragmentTitleList[position]

}