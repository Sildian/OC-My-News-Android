package com.sildian.mynews.controller.activities;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.sildian.mynews.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SettingsSearchFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void settingsSearchFragmentTest() {
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.menu_main_search), withContentDescription("Recherche"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.activity_main_toolbar),
                                        1),
                                1),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.fragment_settings_search_keywords),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        appCompatEditText.perform(scrollTo(), replaceText("pizza"), closeSoftKeyboard());

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.checkbox_section), withText("Books"),
                        childAtPosition(
                                allOf(withId(R.id.checkbox_section_row),
                                        childAtPosition(
                                                withId(R.id.fragment_settings_search_sections),
                                                1)),
                                0)));
        appCompatCheckBox.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox2 = onView(
                allOf(withId(R.id.checkbox_section), withText("Food"),
                        childAtPosition(
                                allOf(withId(R.id.checkbox_section_row),
                                        childAtPosition(
                                                withId(R.id.fragment_settings_search_sections),
                                                2)),
                                1)));
        appCompatCheckBox2.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox3 = onView(
                allOf(withId(R.id.checkbox_section), withText("Health"),
                        childAtPosition(
                                allOf(withId(R.id.checkbox_section_row),
                                        childAtPosition(
                                                withId(R.id.fragment_settings_search_sections),
                                                3)),
                                0)));
        appCompatCheckBox3.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox4 = onView(
                allOf(withId(R.id.checkbox_section), withText("Magazine"),
                        childAtPosition(
                                allOf(withId(R.id.checkbox_section_row),
                                        childAtPosition(
                                                withId(R.id.fragment_settings_search_sections),
                                                4)),
                                0)));
        appCompatCheckBox4.perform(scrollTo(), click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.fragment_settings_search_button_validate), withText("Rechercher"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.menu_main_search), withContentDescription("Recherche"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.activity_main_toolbar),
                                        1),
                                1),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.fragment_settings_search_keywords), withText("pizza"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("pizza")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
