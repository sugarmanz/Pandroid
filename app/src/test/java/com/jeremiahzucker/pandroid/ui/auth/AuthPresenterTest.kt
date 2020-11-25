package com.jeremiahzucker.pandroid.ui.auth

import android.app.Application
import com.jeremiahzucker.pandroid.PandroidApplication.Companion.initPreferences
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

/**
 * AuthPresenterTest
 *
 * Author: Jeremiah Zucker
 * Date:   8/29/2017
 * Desc:   TODO: Complete
 */
class AuthPresenterTest {

    lateinit var presenter: AuthContract.Presenter
    lateinit var view: AuthContract.View

    private val validUsername = "valid@username"
    private val invalidUsername = ""
    private val validPassword = "validPassword"
    private val invalidPassword = ""

    @Before
    fun setup() {
        view = mock(AuthContract.View::class.java)
        mock(Application::class.java).initPreferences()
        presenter = AuthPresenter()
        presenter.attach(view)
    }

    // @Test(expected = NotImplementedError::class)
    // fun testCheckAuth() {
    //     presenter.checkAuth()
    // }
    //
    // @Test
    // fun testMethodsAfterDetach() {
    //     presenter.detach()
    //     presenter.attemptLogin(null, null)
    //     presenter.checkAuth()
    //
    //     verifyZeroInteractions(view)
    // }

    @Test
    fun testAttemptAuthValidUsernameValidPassword() {
        // TODO: Will fix itself when removing preferences from presenter
        presenter.attemptLogin(validUsername, validPassword)

        verify(view, times(1)).clearErrors()
        verify(view, times(1)).showProgress(true)
        verify(view, times(1)).showProgress(false)
        verify(view, times(1)).showMain()
    }

    @Test
    fun testAttemptAuthInvalidUsernameValidPassword() {
        presenter.attemptLogin(invalidUsername, validPassword)

        verify(view, times(1)).showErrorUsernameInvalid()
    }

    @Test
    fun testAttemptAuthValidUsernameInvalidPassword() {
        presenter.attemptLogin(validUsername, invalidPassword)

        verify(view, times(1)).showErrorPasswordInvalid()
    }

    @Test
    fun testAttemptAuthValidUsernameNoPassword() {
        presenter.attemptLogin(validUsername, null)

        verify(view, times(1)).showErrorPasswordRequired()
    }

    @Test
    fun testAttemptAuthNoUsernameValidPassword() {
        presenter.attemptLogin(null, validPassword)

        verify(view, times(1)).showErrorUsernameRequired()
    }
}
