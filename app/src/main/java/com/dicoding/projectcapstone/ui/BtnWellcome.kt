package com.dicoding.projectcapstone.ui

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dicoding.projectcapstone.R


class BtnWellcome @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.buttonStyle
) : AppCompatButton(context, attrs, defStyleAttr) {

    init {
        background = ContextCompat.getDrawable(context, R.drawable.btn_left_rounded_selector)
        setTextColor(ContextCompat.getColor(context, android.R.color.white))
        gravity = Gravity.CENTER
        isAllCaps = false
    }
}