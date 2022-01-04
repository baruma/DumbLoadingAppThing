package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var widthSize = 0
    private var heightSize = 0
    private var progress = 0f

    private var buttonLabel = ""

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Ready) { p, old, new ->
        when (new) {
            ButtonState.Ready -> {
                buttonLabel = "Ready to Download"
                invalidate()
            }

            ButtonState.Downloading -> {
                buttonLabel = "Downloading"
                startLoadAnimation()
            }
        }

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
        if (progress == 1f) {
            drawBackgroundColor(canvas)
        } else {
            drawBackgroundColor(canvas)
            drawButtonProgressAnimation(canvas)
            drawCircleProgressAnimation(canvas)
        }
        drawText(canvas, buttonLabel)
    }

    private fun startLoadAnimation() {
        val va = ValueAnimator.ofFloat(0f, 1f)
        va.duration = 1000
        va.addUpdateListener( object: ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator) {
                progress = animation.animatedFraction
                invalidate()
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

    private fun drawText(canvas: Canvas?, label: String) {
        paint.color = resources.getColor(android.R.color.white)
        paint.textSize = 50f
        canvas?.drawText(label, 50f, 60f, paint)
    }

}