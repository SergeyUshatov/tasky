package com.sergius.auth.data

import com.sergius.auth.domain.UserDataValidator
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class UserDataValidatorTest {
    private lateinit var userDataValidator: UserDataValidator

    @BeforeEach
    fun setUp() {
        userDataValidator = UserDataValidator(EmailPatternValidatorFake)
    }

    @Test
    fun `test password valid`() {
        val passwordState = userDataValidator.validatePassword("123123abC")
        Assertions.assertTrue(passwordState.isValidPassword)
    }

    @ParameterizedTest
    @MethodSource("invalidPasswords")
    fun `test password invalid`(password: String) {
        val passwordState = userDataValidator.validatePassword(password)
        Assertions.assertFalse(passwordState.isValidPassword)
        assertThat(passwordState.isValidPassword, `is`(false))
    }

    companion object {

        @JvmStatic
        fun invalidPasswords(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(""),
                Arguments.of("123456Ab"),
                Arguments.of("123456abc"),
                Arguments.of("123456ABC"),
                Arguments.of("Abcdefghi"),
            )
        }
    }
}