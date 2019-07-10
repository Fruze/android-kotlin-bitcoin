package com.lkand.bitcoin_tracker

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.widget.TextView
import com.lkand.bitcoin_tracker.feature.dashboard.view.DashboardActivity
import org.hamcrest.Matchers.instanceOf
import org.junit.Test
import org.junit.Rule

class DashboardActivityTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(DashboardActivity::class.java)

    @Test
    fun checkInitialStateTest() {
        onView(withId(R.id.dashboardBuyLabel))
            .check(matches(isDisplayed()))
            .check(matches(withText("Buy Rate")))

        onView(withId(R.id.dashboardSellLabel))
            .check(matches(isDisplayed()))
            .check(matches(withText("Sell Rate")))

        onView(withId(R.id.dashboardBuyPrice))
            .check(matches(isDisplayed()))
            .check(matches(withHint("Loading ...")))

        onView(withId(R.id.dashboardSellPrice))
            .check(matches(isDisplayed()))
            .check(matches(withHint("Loading ...")))
    }

    @Test
    fun checkPriceAfterFiveSecond() {
        Thread.sleep(5000)

        val buyPrice = this.activityRule.activity.findViewById<TextView>(R.id.dashboardBuyPrice)
        assertThat(buyPrice.text.toString().toDouble(), instanceOf(Double::class.java))

        val sellPrice = this.activityRule.activity.findViewById<TextView>(R.id.dashboardSellPrice)
        assertThat(sellPrice.text.toString().toDouble(), instanceOf(Double::class.java))
    }

}