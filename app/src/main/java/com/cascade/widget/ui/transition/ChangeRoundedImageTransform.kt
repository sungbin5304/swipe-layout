package com.cascade.widget.ui.transition

import android.animation.Animator
import android.animation.AnimatorSet
import android.transition.TransitionValues
import android.view.ViewGroup
import android.animation.ValueAnimator
import android.content.Context
import android.transition.ChangeBounds
import android.util.AttributeSet
import com.cascade.widget.ui.view.RoundedImageView
import com.cascade.widget.R

class ChangeRoundedImageTransform(context: Context?, attrs: AttributeSet?) : ChangeBounds(context, attrs) {
    private var fromRadius: Int = 0
    private var toRadius: Int = 0

    init {
        if (context != null && attrs != null) {
            val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.RoundedImageTransition, 0, 0)
            fromRadius = ta.getDimensionPixelSize(R.styleable.RoundedImageTransition_fromRadius, fromRadius)
            toRadius = ta.getDimensionPixelSize(R.styleable.RoundedImageTransition_toRadius, toRadius)
        }
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues,
        endValues: TransitionValues
    ): Animator {
        val changeBounds = super.createAnimator(sceneRoot, startValues, endValues)

        val changeRadius = ValueAnimator.ofInt(fromRadius, toRadius)
        changeRadius.addUpdateListener { animator ->
            val view = startValues.view
            if (view is RoundedImageView) {
                view.radius = animator.animatedValue as Int
            }
        }

        return AnimatorSet().apply {
            playTogether(changeBounds, changeRadius)
        }
    }

    fun getFromRadius(): Int {
        return fromRadius
    }

    fun getToRadius(): Int {
        return toRadius
    }
}