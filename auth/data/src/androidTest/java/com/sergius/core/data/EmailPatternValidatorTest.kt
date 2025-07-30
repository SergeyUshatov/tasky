package com.sergius.core.data

import com.forkingcode.androidjunitparams.AndroidJUnitParamsRunner
import com.sergius.auth.data.di.authDataModule
import com.sergius.common.anroidtest.KoinTestRule
import com.sergius.auth.domain.PatternValidator
import junitparams.Parameters
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.get

@RunWith(AndroidJUnitParamsRunner::class)
class EmailPatternValidatorTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule(
        listOf(authDataModule)
    )

    private lateinit var emailPatternValidator: PatternValidator

    @Before
    fun setup() {
        emailPatternValidator = get<PatternValidator>()
    }

    @Test
    @Parameters(method = "validEmails")
    fun testValidEmail(email: String) {
        assertTrue(emailPatternValidator.matches(email))
    }

    @Test
    @Parameters(method = "invalidEmails")
    fun testInvalidEmail(email: String) {
        assertFalse(emailPatternValidator.matches(email))
    }

    private fun validEmails() = arrayOf(
        "test@test.com",
        "test@test.com.com",
        "test.test@test.org",
        "test-test@test.net",
    )

    private fun invalidEmails() = arrayOf(
        "test@test",
        "test@.com",
        "@test.com",
        "test@com",
        "test@.com.",
        "test@com.",
        "test@.com.",
        "test@-com.com",
    )

}