package com.sergius.auth.presentation

import app.cash.turbine.test
import com.sergius.auth.presentation.login.LoginViewModel
import com.sergius.auth.presentation.login.SignInScreenAction
import com.sergius.auth.presentation.truth.LoginStateSubject.Companion.assertThat
import com.sergius.domain.UserDataValidator
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainCoroutineExtension::class)
class LoginViewModelTest {
    private lateinit var viewModel: LoginViewModel
    private lateinit var authRepository: AuthRepositoryFake
    private lateinit var userDataValidator: UserDataValidator

    @BeforeEach
    fun setUp() {
        authRepository = AuthRepositoryFake()
        userDataValidator = mockk(relaxed = true )

        viewModel = LoginViewModel(
            userDataValidator = userDataValidator,
            authRepository = authRepository
        )

        coEvery { userDataValidator.isValidEmail(any()) } returns true
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
}