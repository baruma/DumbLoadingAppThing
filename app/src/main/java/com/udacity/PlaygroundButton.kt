package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.renderscript.Sampler
import android.util.AttributeSet
import android.util.Log
import android.view.View

class PlaygroundButton
@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    private var heightSize = 0
    private var widthSize = 0

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    var progress = 0f

    fun startLoadAnimation() {
        val va = ValueAnimator.ofFloat(0f, 1f)
        va.duration = 1000 //in millis
        va.addUpdateListener( object: ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator) {
                progress = animation.animatedFraction
                invalidate()
                Log.d("TAG", "startLoadAnimation: ${animation.animatedFraction}")
            }
        })
        va.start()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawButtonProgressAnimation(canvas)
        drawCircleProgressAnimation(canvas)
    }

    private fun drawButtonProgressAnimation(canvas: Canvas?) {
        paint.color = resources.getColor(android.R.color.holo_blue_bright)
        canvas?.drawRect(0f, 0f, progress * width, height.toFloat(), paint)
    }

    private fun drawCircleProgressAnimation(canvas: Canvas?) {
        paint.color = resources.getColor(android.R.color.holo_green_light)
        canvas?.drawArc(RectF(0f, 0f, 200f, 200f), 270f, progress * 360f, true, paint )

    }
}