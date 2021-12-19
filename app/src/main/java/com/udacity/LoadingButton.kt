package com.udacity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.icu.util.Measure
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var widthSize = 0
    private var heightSize = 0
    private var progress = 0f

    private lateinit var buttonLabel: String

    private val paint = Paint().apply {
//        color = backgroundColor
        isAntiAlias = true
//        isDither = true
//        style = Paint.Style.STROKE
    }

    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Ready) { p, old, new ->
        when (new) {
// Set up label in XML - I forgot to
            ButtonState.Ready -> {
                buttonLabel = "Ready to Download"
            }

            ButtonState.Downloading -> {
//                valueAnimator.cancel()
//                buttonLabel = "downloading, please wait"
//                rectProgressWidth = 0f
                buttonLabel = "Downloading"
                startLoadAnimation()
//                invalidate()
            }
        }

//        valueAnimator.start()

    }

    fun setState(state: ButtonState) {
        buttonState = state
    }


    init {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(MeasureSpec.getSize(w), heightMeasureSpec, 0)

        widthSize = w
        heightSize = h

        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawBackgroundColor(canvas)
        drawButtonProgressAnimation(canvas)
        drawCircleProgressAnimation(canvas)
    }

    private fun startLoadAnimation() {
        val va = ValueAnimator.ofFloat(0f, 1f)
        va.duration = 1000
        va.addUpdateListener( object: ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator) {
                progress = animation.animatedFraction
                invalidate()
//                Log.d("TAG", "startLoadAnimation: ${animation.animatedFraction}")
            }
        })
        va.start()
    }

    private fun drawBackgroundColor(canvas: Canvas?) {
        paint.color = resources.getColor(android.R.color.holo_red_light)
        canvas?.drawRect(0f, 0f, widthSize.toFloat(), heightSize.toFloat(), paint)
    }

    private fun drawButtonProgressAnimation(canvas: Canvas?) {
        paint.color = resources.getColor(android.R.color.holo_red_dark)
        canvas?.drawRect(0f, 0f, progress * widthSize, heightSize.toFloat(), paint)
    }

    private fun drawCircleProgressAnimation(canvas: Canvas?) {
        paint.color = resources.getColor(android.R.color.holo_green_light)
        canvas?.drawArc(RectF(0f, 0f, 100f, 100f), 270f, progress * 360f, true, paint )
    }

}