package com.dicoding.projectcapstone.ui

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.dicoding.projectcapstone.R

class CustomViewText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(context, attrs, defStyleAttr), View.OnClickListener {

    private var defaultColor: Int = ContextCompat.getColor(context, android.R.color.black)
    private var clickedColor: Int = ContextCompat.getColor(context, R.color.pressed_color)

    init {
        // Set underline text
        paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG

        setTextColor(defaultColor)

        setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (currentTextColor == defaultColor) {
            setTextColor(clickedColor)
        } else {
            setTextColor(defaultColor)
        }
    }
}
