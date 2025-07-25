package com.sergius.auth.presentation

import android.content.Context
import app.cash.turbine.test
import com.sergius.auth.presentation.signup.SignUpScreenAction
import com.sergius.auth.presentation.signup.SignupViewModel
import com.sergius.auth.presentation.truth.SignUpStateSubject.Companion.assertThat
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
class SignupViewModelTest {
    private lateinit var viewModel: SignupViewModel
    private lateinit var authRepository: AuthRepositoryFake
    private lateinit var userDataValidator: UserDataValidator
    private lateinit var context: Context

    @BeforeEach
    fun setUp() {
        authRepository = AuthRepositoryFake()
        userDataValidator = mockk(relaxed = true )
        context = mockk(relaxed = true)

        viewModel = SignupViewModel(
            userDataValidator = userDataValidator,
            authRepository = authRepository
        )

        coEvery { userDataValidator.isValidEmail(any()) } returns true
        coEvery { userDataValidator.validatePassword(any()).isValidPassword } returns true
    }

    @Test
    fun `test name field focus`() = runTest {
        viewModel.state.test {
            assertThat(awaitItem()).nameNotFocused()

            viewModel.onAction(SignUpScreenAction.OnNameFocusChanged(isFocused = true))
            assertThat(awaitItem()).nameFocused()

            viewModel.onAction(SignUpScreenAction.OnNameFocusChanged(isFocused = false))
            assertThat(awaitItem()).nameNotFocused()
        }
    }

    @Test
    fun `test email field focus`() = runTest {
        viewModel.state.test {
            assertThat(awaitItem()).emailNotFocused()

            viewModel.onAction(SignUpScreenAction.OnEmailFocusChanged(isFocused = true))
            assertThat(awaitItem()).emailFocused()

            viewModel.onAction(SignUpScreenAction.OnEmailFocusChanged(isFocused = false))
            assertThat(awaitItem()).emailNotFocused()
        }
    }

    @Test
    fun `test password field focus`() = runTest {
        viewModel.state.test {
            assertThat(awaitItem()).passwordNotFocused()

            viewModel.onAction(SignUpScreenAction.OnPasswordFocusChanged(isFocused = true))
            assertThat(awaitItem()).passwordFocused()

            viewModel.onAction(SignUpScreenAction.OnPasswordFocusChanged(isFocused = false))
            assertThat(awaitItem()).passwordNotFocused()
        }
    }

    @Test
    fun `test password visibility`() = runTest {
        viewModel.state.test {
            assertThat(awaitItem()).passwordNotVisible()

            viewModel.onAction(SignUpScreenAction.OnTogglePasswordVisibility)
            assertThat(awaitItem()).passwordVisible()

            viewModel.onAction(SignUpScreenAction.OnTogglePasswordVisibility)
            assertThat(awaitItem()).passwordNotVisible()
        }
    }
}