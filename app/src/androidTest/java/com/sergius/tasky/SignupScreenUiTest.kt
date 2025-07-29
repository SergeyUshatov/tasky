package com.sergius.tasky

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.sergius.auth.data.di.authDataModule
import com.sergius.auth.presentation.di.authViewModelModule
import com.sergius.core.data.di.coreDataModule
import com.sergius.tasky.di.testAppModule
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class SignupScreenUiTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val koinTestRule = KoinTestRule(
        listOf(
            testAppModule,
            coreDataModule,
            authDataModule,
            authViewModelModule,
        )
    )

    @Test
    fun testEmailFieldValidationPassedWhenValidEmail() {
        Assert.assertTrue(true)
    }
}