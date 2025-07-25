package com.sergius.auth.presentation.truth

import com.google.common.truth.FailureMetadata
import com.google.common.truth.Subject
import com.google.common.truth.Truth
import com.sergius.auth.presentation.login.LoginEvent
import com.sergius.core.presentation.ui.UiText

class LoginEventSubject(
    metadata: FailureMetadata,
    subject: LoginEvent?
) : Subject(metadata, subject) {


    private var actual: LoginEvent? = null

    init {
        this.actual = subject
    }

    fun loginSuccessful(): LoginEventSubject {
        event().isNotNull()
        event().isEqualTo(LoginEvent.Success)
        return this
    }

    fun hasExpectedErrorResourceId(expectedResourceId: Int): LoginEventSubject {
        eventResourceId().isNotNull()
        eventResourceId().isEqualTo(expectedResourceId)
        return this
    }

    private fun event(): Subject = check("LoginEvent").that(actual!!)
    private fun eventResourceId(): Subject = check("LoginEvent.Error.resourceId")
        .that(
            ((actual!! as LoginEvent.Error).error as UiText.StringResource).id
        )

    companion object {
        fun assertThat(loginEvent: LoginEvent): LoginEventSubject {
            return Truth.assertAbout(loginEvent()).that(loginEvent)
        }

        private fun loginEvent(): Factory<LoginEventSubject, LoginEvent> {
            return Factory { metadata: FailureMetadata, subject: LoginEvent? ->
                LoginEventSubject(metadata, subject)
            }
        }
    }
}