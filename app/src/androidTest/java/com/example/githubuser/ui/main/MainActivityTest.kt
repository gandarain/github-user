package com.example.githubuser.ui.main

import android.view.KeyEvent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.githubuser.R
import org.junit.Before
import org.junit.Test

class MainActivityTest {
    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun searchView() {
        onView(withId(R.id.searchView)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("gandarain"), closeSoftKeyboard())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(pressKey(KeyEvent.KEYCODE_ENTER))

        onView(withId(R.id.rv_users)).check(matches(isDisplayed()))
    }
}