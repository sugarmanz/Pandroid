package com.jz.pandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent



class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent) // should probs use no history
        finish()
    }
}
