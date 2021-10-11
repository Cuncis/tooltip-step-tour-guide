package com.cuncis.sampletourguide.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringDef
import androidx.annotation.StringRes
import com.cuncis.sampletourguide.R

data class TooltipItem(
    @Type val type: String,
    @StringRes val title: Int,
    @StringRes val message: Int,
    @StringRes val positiveButtonTextRes: Int,
    @StringRes val negativeButtonTextRes: Int,
    val orderInSeries: Int,
    val totalTooltipsInSeries: Int,
    var action: (() -> Unit)? = null
) {

    companion object {
        // Identifiers for the tooltips. Use your own
        const val TOOLTIP_1 = "TOOLTIP_1"
        const val TOOLTIP_2 = "TOOLTIP_2"
        const val TOOLTIP_3 = "TOOLTIP_3"

        fun tooltip1() = TooltipItem(
            TOOLTIP_1,
            R.string.title_1,
            R.string.message_1,
            R.string.continue_1,
            R.string.skip_1,
            orderInSeries = 0,
            totalTooltipsInSeries = 3
        )

        fun tooltip2() = TooltipItem(
            TOOLTIP_2,
            R.string.title_2,
            R.string.message_2,
            R.string.continue_2,
            R.string.skip_2,
            orderInSeries = 1,
            totalTooltipsInSeries = 3
        )

        fun tooltip3(action: () -> Unit) = TooltipItem(
            TOOLTIP_3,
            R.string.title_3,
            R.string.message_3,
            R.string.continue_3,
            R.string.skip_3,
            orderInSeries = 2,
            totalTooltipsInSeries = 3,
            action = action
        )
    }

    @Retention
    @StringDef(TOOLTIP_1, TOOLTIP_2, TOOLTIP_3)
    annotation class Type
}