package com.example.intuitivecats.breeds.ui


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.intuitivecats.R
import com.example.intuitivecats.breeds.model.Image
import com.example.intuitivecats.breeds.model.Weight
import com.example.intuitivecats.factory.BreedFactory
import com.example.intuitivecats.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@MediumTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class BreedDetailsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun givenBreedInArgsDisplaysBreedDetails() {
        val breedItem = BreedFactory.create(
            id = "Good breed",
            image = Image(
                1445,

                "0XYvRd7oD",
                "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg",
                1204
            ),
            description = "Native to the Greek islands known as the Cyclades in the Aegean Sea, these are natural cats, meaning they developed without humans getting involved in their breeding. As a breed, Aegean Cats are rare, although they are numerous on their home islands. They are generally friendly toward people and can be excellent cats for families with children.",
            origin = "Greece",
            weight = Weight(
                "7 - 10",
                "3 - 5"
            ),
            wikipedia_url = "https://en.wikipedia.org/wiki/American_Bobtail"
        )
        val bundle = BreedDetailsFragmentArgs(breedItem).toBundle()
        launchFragmentInHiltContainer<BreedDetailsFragment>(bundle)
        onView(withId(R.id.btnMoreinfo))
            .check(matches(isDisplayed()))
        onView(withText(breedItem.origin)).check(matches(isDisplayed()))
        onView(withText(breedItem.description)).check(matches(isDisplayed()))
    }
}