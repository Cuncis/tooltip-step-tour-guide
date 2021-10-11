package com.cuncis.sampletourguide.utils

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import com.cuncis.sampletourguide.R
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.FocusShape
import me.toptas.fancyshowcase.listener.OnViewInflateListener

class TooltipHandler {
    var tooltipView: FancyShowCaseView? = null
    var anchor: View? = null
    var onSkipListener: TooltipSkipListener? = null

    companion object {

        /**
         * Function to initialize the [FancyShowCaseView] with the given parameters
         *
         * @param activity the [Activity] in which the tooltip should be shown
         * @param tooltipItem the [TooltipItem] containing the data of the tooltip to be shown
         * @param anchor the view on which the focus should be
         * @param isRoundRect whether the focus of the [anchor] should be round-rectangular or not. Pass false to show circular focus
         */
        fun prepare(activity: Activity, tooltipItem: TooltipItem, anchor: View, isRoundRect: Boolean) = TooltipHandler().apply {
            this.anchor = anchor
            tooltipView = FancyShowCaseView.Builder(activity).apply {
                focusOn(anchor)
                customView(R.layout.layout_onboarding_tooltip, object : OnViewInflateListener {
                    override fun onViewInflated(view: View) {
//                        view.setLayout(tooltipItem)
//                        view.setListeners(tooltipItem)
//                        (view.layoutParams as? ViewGroup.MarginLayoutParams)?.let { params ->
//                            params.topMargin = tooltipView?.focusCenterY.orZero() + tooltipView?.focusHeight.orZero() / 2 + view.dip(if (isRoundRect) 14 else 32)
//                        }
//                        (view.iv_pointer?.layoutParams as? ViewGroup.MarginLayoutParams)?.let { params ->
//                            params.leftMargin = tooltipView?.focusCenterX.orZero() - view.dip(13)
//                        }
                    }
                })
                if (isRoundRect) {
                    focusShape(FocusShape.ROUNDED_RECTANGLE)
                }
                closeOnTouch(false)
                showOnce(tooltipItem.type)
            }.build()
        }
    }

//    private fun View.setLayout(tooltip: TooltipItem) {
//        ?.textResource = tooltip.title
//        tv_onboarding_message?.textResource = tooltip.message
//        btn_finish?.textResource = tooltip.positiveButtonTextRes
//        btn_skip?.textResource = tooltip.negativeButtonTextRes
//        cpi_onboarding?.showImageIconIndicator(tooltip.totalTooltipsInSeries)
//        cpi_onboarding?.handleViewPagerScroll(tooltip.totalTooltipsInSeries, tooltip.orderInSeries)
//    }
//
//    private fun View.setListeners(tooltip: TooltipItem) {
//        if (tooltip.action != null) {
//            this.b?.onDebounceClick {
//                onSkipListener?.skipAll()
//                tooltip.action?.invoke()
//            }
//        } else {
//            btn_yes?.onDebounceClick {
//                hide()
//            }
//        }
//        btn_skip?.onDebounceClick {
//            onSkipListener?.skipAll()
//        }
//    }

    private fun hide() {
        tooltipView?.hide()
    }

    interface TooltipSkipListener {
        fun skipAll()
    }
}