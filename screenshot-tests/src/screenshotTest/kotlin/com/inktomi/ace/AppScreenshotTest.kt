package com.inktomi.ace

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.tooling.preview.Preview
import com.android.tools.screenshot.PreviewTest
import org.junit.Rule

class AppScreenshotTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Preview
    @PreviewTest
    @Composable
    fun AppPreview() {
        App()
    }
}
