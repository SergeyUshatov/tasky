package com.sergius.tasky

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.sergius.auth.data.di.authDataModule
import com.sergius.auth.presentation.di.authViewModelModule
import com.sergius.core.data.di.coreDataModule
import com.sergius.tasky.di.testAppModule
import com.sergius.tasky.robots.LoginScreenRobot
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest

class LoginScreenUiTest : KoinTest {

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

    private lateinit var loginScreenRobot: LoginScreenRobot

    @Before
    fun setup() {
        loginScreenRobot = LoginScreenRobot(composeRule)
    }

    @Test
    fun testEmailValidationMarkDisplayedWhenValidEmail() {
        loginScreenRobot
            .assertEmailFieldDisplayed()
            .assertNoEmailValidationMark()
            .fillEmailField("test@test.com")
            .assertEmailValidationPassed()
    }

    @Test
    fun testEmailValidationMarkNotDisplayedWhenInvalidEmail() {
        loginScreenRobot
            .assertEmailFieldDisplayed()
            .assertNoEmailValidationMark()
            .fillEmailField("test@test.")
            .assertNoEmailValidationMark()
            .fillEmailField("com")
            .assertEmailValidationPassed()
            .removeLastCharsFromEmailField("com".length)
    }

    @Test
    fun testTogglePasswordVisibility() {
        loginScreenRobot
            .assertPasswordFieldDisplayed()
            .fillPasswordField("abracadabra")
            .assertHiddenPasswordIconIsDisplayed()
            .togglePasswordVisibility()
            .assertShownPasswordIconIsDisplayed()
    }

    @Test
    fun testLoginButtonEnabledState() {
        loginScreenRobot
            .assertLoginButtonIsDisplayed()
            .assertLoginButtonNotEnabled()
            .fillEmailField("test@test.com")
            .assertLoginButtonNotEnabled()
            .fillPasswordField("t")
            .assertLoginButtonEnabled()
            .clearPasswordField()
            .assertLoginButtonNotEnabled()
            .fillPasswordField("test")
            .assertLoginButtonEnabled()
            .removeLastCharsFromEmailField(3)
            .assertLoginButtonNotEnabled()
    }
}