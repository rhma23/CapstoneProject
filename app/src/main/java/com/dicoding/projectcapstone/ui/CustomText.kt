package com.dicoding.projectcapstone.ui

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.dicoding.projectcapstone.R

class CustomText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textStyle
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        background = ContextCompat.getDrawable(context, R.drawable.back_button_selector)
        setTextColor(Color.parseColor("#4DA0C1"))
        textSize = 14f
        gravity = Gravity.CENTER
        isAllCaps = false
    }
}
