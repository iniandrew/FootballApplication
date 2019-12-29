package com.andrew.footballapplication

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.andrew.footballapplication.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.andrew.footballapplication.R.id.*
import com.andrew.footballapplication.R.drawable.*
import org.hamcrest.CoreMatchers.allOf

@RunWith(AndroidJUnit4::class)
class AppBehaviourTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testAppBehaviour() {
        onView(withId(bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(rv_league)).check(matches(isDisplayed())).perform(swipeUp())
        Thread.sleep(500)
        onView(withId(rv_league)).check(matches(isDisplayed())).perform(swipeDown())

        // Match
        Thread.sleep(2000)
        onView(withId(navigation_match)).perform(click())
        Thread.sleep(500)
        onView(allOf(withId(spinner), isDisplayed())).perform(click())
        Thread.sleep(500)
        onView(withText("Spanish La Liga")).perform(click())
        onView(withId(rv_previous_match)).check(matches(isDisplayed()))
        testRecyclerView(rv_previous_match, 8)
        testSearchView("Arsenal")

        onView(allOf(withId(viewPager))).perform(swipeLeft())
        Thread.sleep(500)
        onView(allOf(withId(viewPager))).perform(swipeRight())

        Thread.sleep(2000)
        onView(withId(navigation_favorite)).perform(click())
    }

    private fun testRecyclerView(recyclerView: Int, position: Int) {
        Thread.sleep(2000)
        onView(allOf(withId(recyclerView), isDisplayed())).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position))
        Thread.sleep(500)
        onView(allOf(withId(recyclerView), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click()))
        Thread.sleep(2000)

        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        if (isDisplayed().matches(ic_favorite_border)) {
            onView(withText(R.string.add_to_favorite)).check(matches(isDisplayed()))
        }

        if (isDisplayed().matches(ic_favorite)) {
            onView(withText(R.string.remove_from_favorite)).check(matches(isDisplayed()))
        }

        pressBack()
    }

    private fun testSearchView(query: String) {
        Thread.sleep(2000)
        onView(allOf(withId(menu_action_search), isClickable())).perform(click())
        onView(withId(search_src_text)).perform(click()).perform(typeTextIntoFocusedView(query))
        onView(withId(search_src_text)).perform(pressImeActionButton())
        Thread.sleep(2000)
        pressBack()
        Thread.sleep(1000)
        pressBack()
    }
}