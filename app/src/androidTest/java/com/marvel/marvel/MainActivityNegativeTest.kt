package com.marvel.marvel

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.marvel.marvel.main.dagger.MockModel
import com.marvel.marvel.main.model.pojos.Result
import com.marvel.marvel.main.view.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rx.Observable


@RunWith(AndroidJUnit4::class)
class MainActivityNegativeTest {
    companion object {
        private const val ERROR = "error"
    }

    @SuppressWarnings("unused")
    @get:Rule
    var activityTestRule: ActivityTestRule<MainActivity> =
        object : ActivityTestRule<MainActivity>(MainActivity::class.java) {
            override fun beforeActivityLaunched() {
                val observable = Observable.error<List<Result>>(Exception(ERROR))
                MockModel.observable = observable
            }
        }

    @Test
    fun when_ObserverReturnsError_then_userIsAlerted() {
        onView(withText(ERROR)).check(matches(isDisplayed()))
    }

    @Test
    fun when_ObserverReturnsError_then_progressIsStopped() {
        onView(withId(R.id.progress)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)))
    }
}
