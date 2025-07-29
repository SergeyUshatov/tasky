package com.sergius.auth.presentation

import app.cash.turbine.test
import com.sergius.auth.presentation.login.LoginViewModel
import com.sergius.auth.presentation.login.SignInScreenAction
import com.sergius.auth.presentation.truth.LoginEventSubject.Companion.assertThat
import com.sergius.auth.presentation.truth.LoginStateSubject.Companion.assertThat
import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.Result
import com.sergius.core.presentation.ui.R
import com.sergius.domain.UserDataValidator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainCoroutineExtension::class)
class LoginViewModelTest {
    private lateinit var viewModel: LoginViewModel
    private lateinit var authRepository: AuthRepositoryFake
    private lateinit var userDataValidator: UserDataValidator

    @BeforeEach
    fun setUp() {
        authRepository = AuthRepositoryFake()
        userDataValidator = UserDataValidator(EmailPatternValidatorFake)

        viewModel = LoginViewModel(
            userDataValidator = userDataValidator,
            authRepository = authRepository
        )
    }

    @Test
    fun `test email field focus`() = runTest {
        viewModel.state.test {
            assertThat(awaitItem()).emailNotFocused()

            viewModel.onAction(SignInScreenAction.OnEmailFocusChanged(isFocused = true))
            assertThat(awaitItem()).emailFocused()

            viewModel.onAction(SignInScreenAction.OnEmailFocusChanged(isFocused = false))
            assertThat(awaitItem()).emailNotFocused()
        }
    }

    @Test
    fun `test password field focus`() = runTest {
        viewModel.state.test {
            assertThat(awaitItem()).passwordNotFocused()

            viewModel.onAction(SignInScreenAction.OnPasswordFocusChanged(isFocused = true))
            assertThat(awaitItem()).passwordFocused()

            viewModel.onAction(SignInScreenAction.OnPasswordFocusChanged(isFocused = false))
            assertThat(awaitItem()).passwordNotFocused()
        }
    }

    @Test
    fun `test password visibility`() = runTest {
        viewModel.state.test {
            assertThat(awaitItem()).passwordNotVisible()

            viewModel.onAction(SignInScreenAction.OnTogglePasswordVisibility)
            assertThat(awaitItem()).passwordVisible()

            viewModel.onAction(SignInScreenAction.OnTogglePasswordVisibility)
            assertThat(awaitItem()).passwordNotVisible()
        }
    }

    @Test
    fun `test login successful`() = runTest {
        authRepository.loginResult = Result.Success(Unit)

        viewModel.events.test {
            viewModel.onAction(SignInScreenAction.OnLoginClick)
            assertThat(awaitItem()).loginSuccessful()
        }
    }

    @ParameterizedTest
    @MethodSource("eventErrorData")
    fun `test login returns network error`(error: DataError.Network, expectedResourceId: Int) = runTest {
        authRepository.loginResult = Result.Error(error)

        viewModel.events.test {
            viewModel.onAction(SignInScreenAction.OnLoginClick)
            assertThat(awaitItem()).hasExpectedErrorResourceId(expectedResourceId)
        }
    }

    companion object {

        @JvmStatic
        fun eventErrorData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(DataError.Network.UNAUTHORIZED, R.string.error_email_or_password_incorrect),
                Arguments.of(DataError.Network.CONFLICT, R.string.error_email_exists),
                Arguments.of(DataError.Network.REQUEST_TIMEOUT, R.string.error_request_timeout),
                Arguments.of(DataError.Network.BAD_REQUEST, R.string.error_bad_request),
                Arguments.of(DataError.Network.FORBIDDEN, R.string.error_forbidden),
                Arguments.of(DataError.Network.NOT_FOUND, R.string.error_not_found),
                Arguments.of(DataError.Network.UNPROCESSABLE, R.string.error_unprocessable),
                Arguments.of(DataError.Network.TOO_MANY_REQUESTS, R.string.error_too_many_requests),
                Arguments.of(DataError.Network.NO_INTERNET, R.string.error_no_internet),
                Arguments.of(DataError.Network.PAYLOAD_TOO_LARGE, R.string.error_payload_too_large),
                Arguments.of(DataError.Network.SERVER_ERROR, R.string.error_server_error),
                Arguments.of(DataError.Network.SERIALIZATION, R.string.error_serialization),
                Arguments.of(DataError.Network.UNKNOWN, R.string.error_unknown),
            )
        }
    }
}