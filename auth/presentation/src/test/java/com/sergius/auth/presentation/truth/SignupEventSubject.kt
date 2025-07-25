package com.sergius.auth.presentation.truth

import com.google.common.truth.FailureMetadata
import com.google.common.truth.Subject
import com.google.common.truth.Truth
import com.sergius.auth.presentation.signup.SignupEvent
import com.sergius.core.presentation.ui.UiText

class SignupEventSubject(
    metadata: FailureMetadata,
    subject: SignupEvent?
) : Subject(metadata, subject) {
    private var actual: SignupEvent? = null

    init {
        this.actual = subject
    }

    fun signupSuccessful(): SignupEventSubject {
        event().isNotNull()
        event().isEqualTo(SignupEvent.Success)
        return this
    }

    fun hasExpectedErrorResourceId(expectedResourceId: Int): SignupEventSubject {
        eventResourceId().isNotNull()
        eventResourceId().isEqualTo(expectedResourceId)
        return this
    }

    private fun event(): Subject = check("SignupEvent").that(actual!!)
    private fun eventResourceId(): Subject = check("SignupEvent.Error.resourceId")
        .that(
            ((actual!! as SignupEvent.Error).error as UiText.StringResource).id
        )

    companion object {
        fun assertThat(signupEvent: SignupEvent): SignupEventSubject {
            return Truth.assertAbout(signupEvent()).that(signupEvent)
        }

        private fun signupEvent(): Factory<SignupEventSubject, SignupEvent> {
            return Factory { metadata: FailureMetadata, subject: SignupEvent? ->
                SignupEventSubject(metadata, subject)
            }
        }
    }
}