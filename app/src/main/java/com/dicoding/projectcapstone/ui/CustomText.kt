package com.dicoding.projectcapstone.ui

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.dicoding.projectcapstone.R

class CustomText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val normalColor = ContextCompat.getColor(context, R.color.normal_color)
    private val clickedColor = ContextCompat.getColor(context, R.color.clicked_color)

    private var isClicked = false

    init {
        setTextColor(normalColor) // Set warna awal
        textSize = 14f
        gravity = Gravity.CENTER
        isAllCaps = false

        setOnClickListener {
            isClicked = !isClicked // Toggle state
            setTextColor(if (isClicked) clickedColor else normalColor) // Ganti warna
        }
    }
}
