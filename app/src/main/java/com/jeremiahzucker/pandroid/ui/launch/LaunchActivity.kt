package com.jeremiahzucker.pandroid.ui.launch

import android.content.Intent
import android.os.Bundle

import com.jeremiahzucker.pandroid.cache.Preferences
import com.jeremiahzucker.pandroid.ui.auth.AuthActivity
import com.jeremiahzucker.pandroid.ui.base.BaseActivity
import com.jeremiahzucker.pandroid.ui.main.MainActivity

class LaunchActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(
            this,
            if (Preferences.userAuthToken == null)
                AuthActivity::class.java else MainActivity::class.java
        )
        startActivity(intent)
        finish()
    }
}
