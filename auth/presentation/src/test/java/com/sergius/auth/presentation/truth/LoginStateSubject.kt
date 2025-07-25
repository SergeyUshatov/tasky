package com.sergius.auth.presentation.truth

import com.google.common.truth.FailureMetadata
import com.google.common.truth.Subject
import com.google.common.truth.Truth
import com.sergius.auth.presentation.login.LoginState

class LoginStateSubject(
    metadata: FailureMetadata,
    subject: LoginState?
): Subject(metadata, subject) {
    private var actual: LoginState? = null

    init {
        this.actual = subject
    }

    fun emailFocused(): LoginStateSubject {
        emailFocus().isNotNull()
        emailFocus().isTrue()
        return this
    }

    fun emailNotFocused(): LoginStateSubject {
        emailFocus().isNotNull()
        emailFocus().isFalse()
        return this
    }

    fun passwordFocused(): LoginStateSubject {
        passwordFocus().isNotNull()
        passwordFocus().isTrue()
        return this
    }

    fun passwordNotFocused(): LoginStateSubject {
        passwordFocus().isNotNull()
        passwordFocus().isFalse()
        return this
    }

    fun passwordVisible(): LoginStateSubject {
        passwordVisibility().isNotNull()
        passwordVisibility().isTrue()
        return this
    }

    fun passwordNotVisible(): LoginStateSubject {
        passwordVisibility().isNotNull()
        passwordVisibility().isFalse()
        return this
    }

    private fun emailFocus() = check("emailState.isEmailFocused").that(actual!!.emailState.isEmailFocused)
    private fun passwordFocus() = check("passwordState.isPasswordFocused").that(actual!!.passwordState.isPasswordFocused)
    private fun passwordVisibility() = check("passwordState.isPasswordVisible").that(actual!!.passwordState.isPasswordVisible)

    companion object {
        fun assertThat(loginState: LoginState): LoginStateSubject {
            return Truth.assertAbout(loginState()).that(loginState)
        }

        private fun loginState(): Factory<LoginStateSubject, LoginState> {
            return Factory { metadata: FailureMetadata, subject: LoginState? ->
                LoginStateSubject(metadata, subject)
            }
        }
    }
}