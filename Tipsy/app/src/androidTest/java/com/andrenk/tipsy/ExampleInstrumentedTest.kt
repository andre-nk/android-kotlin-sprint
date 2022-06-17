package com.andrenk.tipsy

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.containsString

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule
import androidx.test.espresso.matcher.ViewMatchers.withId as withId1

@RunWith(AndroidJUnit4::class)
class TipCalculatorTest {

    //Prompt compiler to run this Activity before any testing
    @get: Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    //
    @Test
    fun calculate20PercentTip(){
        onView(withId1(R.id.cost_of_service))
            .perform(typeText("50"))
            .perform(ViewActions.closeSoftKeyboard())

        onView(withId1(R.id.calculate_btn))
            .perform(click())

        onView(withId1(R.id.text_result))
            .check(matches(withText(containsString("10"))))
    }
}