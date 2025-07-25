package com.sergius.auth.presentation.truth

import com.google.common.truth.FailureMetadata
import com.google.common.truth.Subject
import com.google.common.truth.Truth
import com.sergius.auth.presentation.signup.SignupState

class SignUpStateSubject(
    metadata: FailureMetadata,
    subject: SignupState?
): Subject(metadata, subject) {
    private var actual: SignupState? = null

    init {
        this.actual = subject
    }

    fun nameFocused(): SignUpStateSubject {
        nameFocus().isNotNull()
        nameFocus().isTrue()
        return this
    }

    fun nameNotFocused(): SignUpStateSubject {
        nameFocus().isNotNull()
        nameFocus().isFalse()
        return this
    }

    fun emailFocused(): SignUpStateSubject {
        emailFocus().isNotNull()
        emailFocus().isTrue()
        return this
    }

    fun emailNotFocused(): SignUpStateSubject {
        emailFocus().isNotNull()
        emailFocus().isFalse()
        return this
    }

    fun passwordFocused(): SignUpStateSubject {
        passwordFocus().isNotNull()
        passwordFocus().isTrue()
        return this
    }

    fun passwordNotFocused(): SignUpStateSubject {
        passwordFocus().isNotNull()
        passwordFocus().isFalse()
        return this
    }

    fun passwordVisible(): SignUpStateSubject {
        passwordVisibility().isNotNull()
        passwordVisibility().isTrue()
        return this
    }

    fun passwordNotVisible(): SignUpStateSubject {
        passwordVisibility().isNotNull()
        passwordVisibility().isFalse()
        return this
    }

    fun notSigningUp(): SignUpStateSubject {
        isSigningUp().isNotNull()
        isSigningUp().isFalse()
        return this
    }

    fun signingUp(): SignUpStateSubject {
        isSigningUp().isNotNull()
        isSigningUp().isTrue()
        return this
    }

    private fun nameFocus() = check("nameState.isNameFocused").that(actual!!.nameState.isNameFocused)
    private fun emailFocus() = check("emailState.isEmailFocused").that(actual!!.emailState.isEmailFocused)
    private fun passwordFocus() = check("passwordState.isPasswordFocused").that(actual!!.passwordState.isPasswordFocused)
    private fun passwordVisibility() = check("passwordState.isPasswordVisible").that(actual!!.passwordState.isPasswordVisible)
    private fun isSigningUp() = check("isSigningUp").that(actual!!.isSigningUp)

    companion object {
        fun assertThat(signupState: SignupState): SignUpStateSubject {
            return Truth.assertAbout(signupState()).that(signupState)
        }

        private fun signupState(): Factory<SignUpStateSubject, SignupState> {
            return Factory { metadata: FailureMetadata, subject: SignupState? ->
                SignUpStateSubject(metadata, subject)
            }
        }
    }
}