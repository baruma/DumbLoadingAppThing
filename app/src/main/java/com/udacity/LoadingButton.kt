package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var widthSize = 0
    private var heightSize = 0

    private val valueAnimator = ValueAnimator()

    // Colors
    private val backgroundColor = ResourcesCompat.getColor(resources, R.color.customOrange, null)
    private val transformColor = ResourcesCompat.getColor(resources, R.color.customYellow, null)

    // Paint
    private val paint = Paint().apply {
        color = backgroundColor
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
    }

    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Ready) { p, old, new ->

    }

    init {

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // Include Functions to draw :
        // 1.button animation (left to right)
        // 2. Progress circle
        // 3. background color (static)
        // 4. text (static)
        drawBackgroundColor(canvas)

    }

    fun drawBackgroundColor(canvas: Canvas?) {
        paint.color = transformColor
        canvas?.drawRect(0f, 0f, widthSize.toFloat(), heightSize.toFloat(), paint)
    }

}