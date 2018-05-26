package com.marvel.marvel

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.marvel.marvel.main.dagger.MockModel
import com.marvel.marvel.main.model.pojos.Result
import com.marvel.marvel.main.view.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rx.Observable
import java.util.*


@RunWith(AndroidJUnit4::class)
class MainActivityPositiveTest {

    private val mockComicsViewModel: List<Result>
        get() {
            val list = ArrayList<Result>()
            val model = Result()
            model.title = TITLE
            list.add(model)
            return list
        }

    @SuppressWarnings("unused")
    @get:Rule
    var activityTestRule: ActivityTestRule<MainActivity> =
        object : ActivityTestRule<MainActivity>(MainActivity::class.java) {
            override fun beforeActivityLaunched() {
                val list = mockComicsViewModel
                val observable = Observable.just(list)
                MockModel.observable = observable
            }
        }

    @Test
    fun when_ClickOnMenu_then_AcitivtyIsLaunched() {
        onView(withId(R.id.budget)).perform(click())
        onView(withId(R.id.go)).check(matches(isDisplayed()))
    }

    companion object {
        private const val TITLE = "title"
    }
}
