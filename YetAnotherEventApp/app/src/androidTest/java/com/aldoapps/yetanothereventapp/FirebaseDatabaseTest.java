package com.aldoapps.yetanothereventapp;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by aldo on 10/27/16.
 */
@RunWith(AndroidJUnit4.class)
public class FirebaseDatabaseTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule
        <>(MainActivity.class);

    @Test
    public void pushButtonToSendData() throws Exception {
        String expectedString = "Hello GDG!";

        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.result)).check(matches(withText(expectedString)));
    }
}
