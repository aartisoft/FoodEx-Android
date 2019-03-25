package com.korlab.foodex.UI

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt

data class BottomBarItem(
        val index: Int,
        val text: String,
        val textSize: Float,
        val typeface: Typeface,
        @ColorInt val textColor: Int,
        val drawable: Drawable,
        val type: ReadableBottomBar.ItemType
)