package com.example.intuitivecats.breeds.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.intuitivecats.R
import com.example.intuitivecats.launchFragmentInHiltContainer
import com.example.pagerv.PagerDataAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@MediumTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class BreedsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var adapter: PagerDataAdapter

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun verifyBreedsDisplayed() {
        launchFragmentInHiltContainer<BreedsFragment>(null)
        Thread.sleep(2000)
        onView(withId(R.id.recyclerViewCats)).check(matches(isDisplayed()))
        onView(withText("Aegean")).check(matches(isDisplayed()))
    }
}
