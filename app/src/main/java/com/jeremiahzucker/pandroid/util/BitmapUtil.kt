package com.jeremiahzucker.pandroid.util

import android.graphics.*

/**
 * BitmapUtil
 *
 * Author: Jeremiah Zucker
 * Date:   9/7/2017
 * Desc:   TODO: Complete
 */
// TODO: Should convert into transformation
fun Bitmap.getCroppedBitmap(): Bitmap {
    val output = Bitmap.createBitmap(width,
            height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)

    val color = 0xff424242.toInt()
    val paint = Paint()
    val rect = Rect(0, 0, width, height)

    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    paint.color = color
    // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
    canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(),
            (width / 2).toFloat(), paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(this, rect, rect, paint)
    //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
    //return _bmp;
    return output
}