package com.udacity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.icu.util.Measure
import android.util.AttributeSet
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
    private var rectProgressWidth = 0f

    private var valueAnimator = ValueAnimator()

    private lateinit var buttonLabel: String

    // Colors - unused.
    private val backgroundColor = ResourcesCompat.getColor(resources, R.color.customBlue, null)
//    private val transformColor = ResourcesCompat.getColor(resources, R.color.customNavy, null)

    // Paint
    private val paint = Paint().apply {
//        color = backgroundColor
        isAntiAlias = true
//        isDither = true
//        style = Paint.Style.STROKE
    }

    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Ready) { p, old, new ->
        when (new) {

            ButtonState.Ready -> {
                // Include if/else check for whether radio button was selected
                buttonLabel = "ready to download"
                valueAnimator = ValueAnimator.ofFloat(0f, widthSize.toFloat())
                valueAnimator.setDuration(3000)
                valueAnimator.addUpdateListener { animation ->
                    rectProgressWidth = animation.animatedValue as Float
                    invalidate()
                }
            }

            ButtonState.Downloading -> {
//                valueAnimator.cancel()
                buttonLabel = "downloading, please wait"
                rectProgressWidth = 0f
                invalidate()
            }
        }

        valueAnimator.start()

//        valueAnimator.addListener(object : AnimatorListenerAdapter(){
//            override fun onAnimationEnd(animation: Animator?) {
//                rectProgressWidth = 0f
//                if(buttonState == ButtonState.Ready){
//                    buttonState = ButtonState.Ready
//                }
//            }
//        })
//        valueAnimator.start()

    }


    init {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(MeasureSpec.getSize(w), heightMeasureSpec, 0)

        widthSize = w
        heightSize = h

        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // Include Functions to draw :
        // 1. background color (static)
        drawBackgroundColor(canvas)

        // 2.button animation (left to right)
        drawButtonProgressAnimation(canvas)
        // 3. Progress circle
        // 4. text (static)
    }

    private fun drawBackgroundColor(canvas: Canvas?) {
        paint.color = resources.getColor(android.R.color.holo_red_light)
        canvas?.drawRect(0f, 0f, widthSize.toFloat(), heightSize.toFloat(), paint)
    }

    private fun drawButtonProgressAnimation(canvas: Canvas?) {
        paint.color = resources.getColor(android.R.color.holo_red_dark)
        canvas?.drawRect(0f, 0f, widthSize.toFloat(), heightSize.toFloat(), paint)
    }

}