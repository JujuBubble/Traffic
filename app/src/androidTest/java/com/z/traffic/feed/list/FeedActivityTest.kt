package com.z.traffic.feed.list

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.z.traffic.R
import com.z.traffic.TrafficInstrumentationTest
import com.z.traffic.feed.application.components.MockBaseComponent
import com.z.traffic.feed.datasource.DataSource
import com.z.traffic.feed.mocks.MockCamerasDataSource
import com.z.traffic.feed.models.Camera
import com.z.traffic.feed.models.ImageData
import com.z.traffic.feed.models.Location
import com.z.traffic.feed.view.FeedActivity
import com.z.traffic.feed.viewmodel.FeedViewModel
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
@LargeTest
class FeedActivityTest : TrafficInstrumentationTest() {
    @Inject
    lateinit var dataSource: DataSource
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Rule
    @JvmField
    var testRule = ActivityTestRule<FeedActivity>(FeedActivity::class.java)

    @Before
    fun setup() {
        (app.baseComponent as MockBaseComponent).inject(this)

        (dataSource as MockCamerasDataSource).setCameraData(listOf())
        (dataSource as MockCamerasDataSource).setErrorState(null)
        (dataSource as MockCamerasDataSource).setLoadingState(false)
        (dataSource as MockCamerasDataSource).setFirstLoadingState(false)
    }

    @Test
    fun testLoadingScreen() {
        // Test loading screen visible
        (dataSource as MockCamerasDataSource).setFirstLoadingState(true)
        onView(withId(R.id.screenLoading)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        // Test loading screen hidden
        (dataSource as MockCamerasDataSource).setFirstLoadingState(false)
        onView(withId(R.id.screenLoading)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun testRefreshButton() {
        (dataSource as MockCamerasDataSource).setLoadingState(false)

        // Assert if retry button is in correct state
        onView(withId(R.id.retryButton)).check(matches(withText("Refresh cameras")))
    }

    @Test
    fun testCameraList() {
        mockList()

        // Scroll through view
        for (i in 1..10) {
            onView(withId(R.id.recyclerView)).perform(swipeLeft())
            onView(withId(R.id.retryButton)).check(matches(withText("Refresh cameras")))
            onView(allOf(withId(R.id.cameraId), first(withId(R.id.cameraId))))
                .check(matches(withText(containsString("ID"))))
        }

        for (i in 1..10) {
            onView(withId(R.id.recyclerView)).perform(swipeRight())
            onView(allOf(withId(R.id.cameraId), first(withId(R.id.cameraId))))
                .check(matches(withText(containsString("ID"))))
        }
    }

    @Test
    fun testListSelection() {
        mockList()

        val viewModel = ViewModelProviders.of(testRule.activity, viewModelFactory)
            .get(FeedViewModel::class.java)

        // Assert if selection from map mirrors recycler view
        for (index in listOf(10, 30, 50, 1)) {
            viewModel.selectIndex(index)

            onView(allOf(withId(R.id.cameraId), first(withId(R.id.cameraId))))
                .check(matches(withText(containsString("ID $index"))))
        }
    }

    @Test
    fun testCardContent() {
        mockList()

        // Assert if contents
        onView(allOf(withId(R.id.cameraId), first(withId(R.id.cameraId))))
            .check(matches(withText(containsString("ID 1"))))
        onView(allOf(withId(R.id.cameraLastUpdated), first(withId(R.id.cameraLastUpdated))))
            .check(matches(withText(containsString("TIMESTAMP"))))
        onView(allOf(withId(R.id.cameraLocation), first(withId(R.id.cameraLocation))))
            .check(matches(withText(containsString("42.00, 12.00"))))
    }

    @Test
    fun testError() {
        (dataSource as MockCamerasDataSource).setErrorState(Error(""))

        // Assert if snackbar is displayed
        onView(withId(com.google.android.material.R.id.snackbar_action)).check(matches(isDisplayed()))
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(containsString(app.resources.getString(R.string.error_message)))))
    }

    @Test
    fun testImageViewActivity() {
        mockList()

        // Tap on an image
        onView(allOf(withId(R.id.cameraId), first(withId(R.id.cameraId)))).perform(click())

        // Check if the image view is launched
        onView(withId(R.id.cameraImageDetail)).check(matches(isDisplayed()))
    }

    private fun mockList() {
        val list = mutableListOf<Camera>()
        for (i in 1..100) {
            val camera = Camera(
                timestamp = "TIMESTAMP",
                image = "https://homepages.cae.wisc.edu/~ece533/images/cat.png",
                location = Location(42.0F, 12.0F),
                cameraId = "ID $i",
                imageMetadata = ImageData(
                    height = 100,
                    width = 100,
                    md5 = ""
                )
            )
            list.add(camera)
        }
        (dataSource as MockCamerasDataSource).setCameraData(list)
    }

    private fun <T> first(matcher: Matcher<T>): Matcher<T>? {
        return object : BaseMatcher<T>() {
            var isFirst = true
            override fun matches(item: Any): Boolean {
                if (isFirst && matcher.matches(item)) {
                    isFirst = false
                    return true
                }
                return false
            }

            override fun describeTo(description: Description) {
                description.appendText("should return first matching item")
            }
        }
    }
}
