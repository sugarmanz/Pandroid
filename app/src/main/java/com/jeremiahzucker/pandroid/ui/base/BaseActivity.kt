package com.jeremiahzucker.pandroid.ui.base

import android.graphics.PixelFormat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.jeremiahzucker.pandroid.R
import com.jeremiahzucker.pandroid.util.setBackgroundGradient

/**
 * BaseActivity
 *
 * Author: Jeremiah Zucker
 * Date:   9/4/2017
 * Desc:   TODO: Complete
 */
abstract class BaseActivity : AppCompatActivity() {

//    Converted to xml gradient
//    override fun onAttachedToWindow() {
//        super.onAttachedToWindow()
//        // https://crazygui.wordpress.com/2010/09/05/high-quality-radial-gradient-in-android/
//        val displayMetrics = resources.displayMetrics
//        val screenHeight = displayMetrics.heightPixels
//
//        window.setBackgroundGradient(
//                ContextCompat.getColor(this, R.color.theme_dark_blue_gradientColor),
//                ContextCompat.getColor(this, R.color.theme_dark_blue_background),
//                screenHeight / 2,
//                0.5f,
//                0.5f
//        )
//        window.setFormat(PixelFormat.RGBA_8888)
//    }

}