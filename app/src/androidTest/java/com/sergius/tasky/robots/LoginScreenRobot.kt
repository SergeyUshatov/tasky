package com.sergius.tasky.robots

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.pressKey
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.sergius.core.presentation.designsystem.R
import com.sergius.tasky.MainActivity
import com.sergius.tasky.ResourceUtil

typealias LoginComposeRule = AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>

private const val basicTextFieldTag = "basic_text_field"
private const val securedFieldTag = "basic_secure_text_field"
private const val loginButtonTag = "login_button"
private const val textFieldEndIconDescription = "text field end icon"

@ExperimentalTestApi
fun LoginComposeRule.waitUntilAtLeastOneExists2(matcher: SemanticsMatcher, timeoutMillis: Long = 5000) =
    this.waitUntilAtLeastOneExists(matcher, timeoutMillis)

@OptIn(ExperimentalTestApi::class)
class LoginScreenRobot(
    private val composeRule: LoginComposeRule
) {
    private val showPassword = ResourceUtil.getStringResource(R.string.show_password)
    private val hidePassword = ResourceUtil.getStringResource(R.string.hide_password)

    fun fillEmailField(email: String): LoginScreenRobot {
        composeRule.onNodeWithTag(basicTextFieldTag)
            .performClick()
            .assertIsFocused()
            .performTextInput(email)
        return this
    }

    fun assertEmailFieldDisplayed(): LoginScreenRobot {
        composeRule.onNodeWithText("Email address")
            .assertIsDisplayed()
        return this
    }

    fun assertEmailValidationPassed(): LoginScreenRobot {
        composeRule.onNodeWithContentDescription(textFieldEndIconDescription)
            .assertIsDisplayed()
        return this
    }

    fun assertNoEmailValidationMark(): LoginScreenRobot {
        composeRule.onNodeWithContentDescription(textFieldEndIconDescription)
            .assertIsNotDisplayed()
        return this
    }

    fun assertPasswordFieldDisplayed(): LoginScreenRobot {
        composeRule.onNodeWithText("Password")
            .assertIsDisplayed()
        return this
    }

    fun fillPasswordField(password: String): LoginScreenRobot {
        composeRule.onNodeWithTag(securedFieldTag)
            .performClick()
            .assertIsFocused()
            .performTextInput(password)
        return this
    }

    fun clearPasswordField(): LoginScreenRobot {
        composeRule.onNodeWithTag(securedFieldTag)
            .performTextClearance()
        return this
    }

    fun assertHiddenPasswordIconIsDisplayed(): LoginScreenRobot {
        composeRule.onNodeWithContentDescription(hidePassword)
            .assertIsNotDisplayed()
        composeRule.onNodeWithContentDescription(showPassword)
            .assertIsDisplayed()

        return this
    }

    fun togglePasswordVisibility(): LoginScreenRobot {
        val showPasswordNode = composeRule.onNodeWithContentDescription(showPassword)
        if (showPasswordNode.isDisplayed()) {
            showPasswordNode.performClick()
        } else {
            composeRule.onNodeWithContentDescription(hidePassword)
                .performClick()
        }
        return this
    }

    fun assertShownPasswordIconIsDisplayed(): LoginScreenRobot {
        composeRule.onNodeWithContentDescription(showPassword)
            .assertIsNotDisplayed()
        composeRule.onNodeWithContentDescription(hidePassword)
            .assertIsDisplayed()

        return this
    }

    fun removeLastCharsFromEmailField(charCount: Int): LoginScreenRobot {
        val node = composeRule.onNodeWithTag(basicTextFieldTag)
            .performClick()
            .assertIsFocused()
        repeat(charCount) { node.performKeyInput { pressKey(Key.Backspace) } }
        return this
    }

    fun assertLoginButtonIsDisplayed(): LoginScreenRobot {
        composeRule.onNodeWithTag(loginButtonTag)
            .assertIsDisplayed()
        return this
    }

    fun assertLoginButtonEnabled(): LoginScreenRobot {
        composeRule.onNodeWithTag(loginButtonTag)
            .assertIsEnabled()
        return this
    }

    fun assertLoginButtonNotEnabled(): LoginScreenRobot {
        composeRule.onNodeWithTag(loginButtonTag)
            .assertIsNotEnabled()
        return this
    }

}