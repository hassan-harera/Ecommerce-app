package com.englizya.complaint

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.toPackage
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.englizya.navigation.home.HomeActivity
import com.englizya.profile.ProfileFragment
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComplaintFragmentTest {

    @get:Rule
    val activityRule = ActivityTestRule(SplashA::class.java)

    @Before
    fun setUp() {
        ActivityScenario
        FragmentScenario.launch(ProfileFragment::class.java)
    }

    @Test
    fun validateIntentSentToPackage() {
        return Intent(InstrumentationRegistry.getInstrumentation().context, MainActivity::class.java)
            .apply {
                putExtra("TEST", "TEST")
            }
    }

    @Test
    fun listGoesOverTheFold() {
    }
}