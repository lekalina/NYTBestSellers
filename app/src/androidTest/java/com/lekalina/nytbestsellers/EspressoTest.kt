package com.lekalina.nytbestsellers

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EspressoTest {

    @Test(expected = RuntimeException::class)
    fun categoryListItemVisibilityTest() {
        // Check that a top item is visible
        onView(withText("Animals")).check(matches(isDisplayed()))

        // Scroll to the middle and click on an item
        onView(ViewMatchers.withId(R.id.list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    21,
                    click()
                )
            )

        // Check that an middle item is visible
        onView(withText("Espionage")).check(matches(isDisplayed()))

        // Scroll to the bottom and click on an item
        onView(ViewMatchers.withId(R.id.list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    40,
                    click()
                )
            )

        // Check that an item near the bottom is visible
        onView(withText("Science")).check(matches(isDisplayed()))
    }
}