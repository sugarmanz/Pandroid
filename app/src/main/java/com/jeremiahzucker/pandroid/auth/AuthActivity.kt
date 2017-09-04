package com.jeremiahzucker.pandroid.auth

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import com.jeremiahzucker.pandroid.BaseActivity
import com.jeremiahzucker.pandroid.MainActivity
import com.jeremiahzucker.pandroid.R
import kotlinx.android.synthetic.main.activity_auth.*

/**
 * AuthActivity
 *
 * Author: Jeremiah Zucker
 * Date:   8/29/2017
 * Desc:   A login screen that offers login via username/password.
 */
class AuthActivity : BaseActivity(), AuthContract.View {

    private val TAG: String = AuthActivity::class.java.simpleName
    private var presenter: AuthContract.Presenter = AuthPresenter() // TODO: Inject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        // Set up the login form.
        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        sign_in_button.setOnClickListener { attemptLogin() }
    }

    override fun onResume() {
        super.onResume()
        presenter.attach(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.detach()
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    override fun showProgress(show: Boolean) {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        login_form.visibility = if (show) View.GONE else View.VISIBLE
        login_form.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 0 else 1).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_form.visibility = if (show) View.GONE else View.VISIBLE
                    }
                })

        login_progress.visibility = if (show) View.VISIBLE else View.GONE
        login_progress.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 1 else 0).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_progress.visibility = if (show) View.VISIBLE else View.GONE
                    }
                })
    }

    override fun showMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun clearErrors() {
        password.error = null
        username.error = null
    }

    override fun showErrorUsernameRequired() = showErrorOnField(username, getString(R.string.error_field_required))
    override fun showErrorUsernameInvalid() = showErrorOnField(username, getString(R.string.error_invalid_username))
    override fun showErrorPasswordRequired() = showErrorOnField(password, getString(R.string.error_field_required))
    override fun showErrorPasswordInvalid() = showErrorOnField(password, getString(R.string.error_invalid_password))
    override fun showErrorPasswordIncorrect() = showErrorOnField(password, getString(R.string.error_incorrect_password))

    private fun showErrorOnField(view: EditText, error: String) {
        view.error = error
        view.requestFocus()
    }

    private fun attemptLogin() {
        presenter.attemptLogin(username.text.toString(), password.text.toString())
    }
}
