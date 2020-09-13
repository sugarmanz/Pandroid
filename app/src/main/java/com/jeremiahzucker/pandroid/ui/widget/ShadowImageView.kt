package com.jeremiahzucker.pandroid.ui.widget

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import androidx.appcompat.widget.AppCompatImageView

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/8/16
 * Time: 4:23 PM
 * Desc: ShadowImageView
 * Stole from [android.support.v4.widget.SwipeRefreshLayout]'s implementation to display beautiful shadow
 * for circle ImageView.
 */
class ShadowImageView : AppCompatImageView {

    private var mShadowRadius: Int = 0

    // Animation
    private var mRotateAnimator: ObjectAnimator? = null
    private var mLastAnimationValue: Long = 0

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        val density = context.resources.displayMetrics.density
        val shadowXOffset = (density * X_OFFSET).toInt()
        val shadowYOffset = (density * Y_OFFSET).toInt()

        mShadowRadius = (density * SHADOW_RADIUS).toInt()

        val circle: ShapeDrawable
        if (elevationSupported()) {
            circle = ShapeDrawable(OvalShape())
            elevation = SHADOW_ELEVATION * density
        } else {
            val oval = OvalShadow(mShadowRadius)
            circle = ShapeDrawable(oval)
            setLayerType(LAYER_TYPE_SOFTWARE, circle.paint)
            circle.paint.setShadowLayer(mShadowRadius.toFloat(), shadowXOffset.toFloat(), shadowYOffset.toFloat(), KEY_SHADOW_COLOR)
            val padding = mShadowRadius
            // set padding so the inner image sits correctly within the shadow.
            setPadding(padding, padding, padding, padding)
        }
        circle.paint.isAntiAlias = true
        circle.paint.color = DEFAULT_BACKGROUND_COLOR
        background = circle

        mRotateAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f)
        mRotateAnimator!!.duration = 7200
        mRotateAnimator!!.interpolator = LinearInterpolator()
        mRotateAnimator!!.repeatMode = ValueAnimator.RESTART
        mRotateAnimator!!.repeatCount = ValueAnimator.INFINITE
    }

    private fun elevationSupported(): Boolean {
        return Build.VERSION.SDK_INT >= 21
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (!elevationSupported()) {
            setMeasuredDimension(measuredWidth + mShadowRadius * 2, measuredHeight + mShadowRadius * 2)
        }
    }

    // Animation

    fun startRotateAnimation() {
        mRotateAnimator!!.cancel()
        mRotateAnimator!!.start()
    }

    fun cancelRotateAnimation() {
        mLastAnimationValue = 0
        mRotateAnimator!!.cancel()
    }

    fun pauseRotateAnimation() {
        mLastAnimationValue = mRotateAnimator!!.currentPlayTime
        mRotateAnimator!!.cancel()
    }

    fun resumeRotateAnimation() {
        mRotateAnimator!!.start()
        mRotateAnimator!!.currentPlayTime = mLastAnimationValue
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (mRotateAnimator !=
                null) {
            mRotateAnimator!!.cancel()
            mRotateAnimator = null
        }
    }

    /** Draw oval shadow below ImageView under lollipop */
    private inner class OvalShadow(shadowRadius: Int) : OvalShape() {
        private var mRadialGradient: RadialGradient? = null
        private val mShadowPaint: Paint = Paint()

        init {
            mShadowRadius = shadowRadius
            updateRadialGradient(rect().width().toInt())
        }

        override fun onResize(width: Float, height: Float) {
            super.onResize(width, height)
            updateRadialGradient(width.toInt())
        }

        override fun draw(canvas: Canvas, paint: Paint) {
            val viewWidth = this@ShadowImageView.width
            val viewHeight = this@ShadowImageView.height
            canvas.drawCircle((viewWidth / 2).toFloat(), (viewHeight / 2).toFloat(), (viewWidth / 2).toFloat(), mShadowPaint)
            canvas.drawCircle((viewWidth / 2).toFloat(), (viewHeight / 2).toFloat(), (viewWidth / 2 - mShadowRadius).toFloat(), paint)
        }

        private fun updateRadialGradient(diameter: Int) {
            mRadialGradient = RadialGradient((diameter / 2).toFloat(), (diameter / 2).toFloat(),
                    mShadowRadius.toFloat(), intArrayOf(FILL_SHADOW_COLOR, Color.TRANSPARENT), null, Shader.TileMode.CLAMP)
            mShadowPaint.shader = mRadialGradient
        }
    }

    companion object {
        private const val KEY_SHADOW_COLOR = 0x1E000000
        private const val FILL_SHADOW_COLOR = 0x3D000000

        private const val X_OFFSET = 0f
        private const val Y_OFFSET = 1.75f

        private const val SHADOW_RADIUS = 24f
        private const val SHADOW_ELEVATION = 16

        private const val DEFAULT_BACKGROUND_COLOR = 0xFF3C5F78.toInt()
    }
}
