package com.cuncis.sampletourguide.utils

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.cuncis.sampletourguide.R
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.createBalloon

object Tooltips {
    fun setupBalloon(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        layout: Int,
        height: Int
    ): Balloon {
        return createBalloon(context) {
            setLayout(layout)
            setArrowSize(15)
            setArrowOrientation(ArrowOrientation.BOTTOM)
            setArrowPosition(0.75f)
            setWidthRatio(0.50f)
            setHeight(height)
            setCornerRadius(8f)
            setElevation(10)
            setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            setBalloonAnimation(BalloonAnimation.FADE)
            setLifecycleOwner(lifecycleOwner)
        }
    }
}