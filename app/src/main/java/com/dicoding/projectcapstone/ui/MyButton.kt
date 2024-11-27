package com.dicoding.projectcapstone.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dicoding.projectcapstone.R

class MyButton : AppCompatButton {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var txtColor: Int = 0
    private var ButtonNormal: Drawable
    private var ButtonPressed: Drawable
    init {
        txtColor = ContextCompat.getColor(context, android.R.color.background_light)
        ButtonNormal = ContextCompat.getDrawable(context, R.drawable.register_button_normal) as Drawable
        ButtonPressed = ContextCompat.getDrawable(context, R.drawable.register_button_pressed) as Drawable
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = if(isPressed) ButtonPressed else ButtonNormal
        setTextColor(txtColor)
        textSize = 20f
        gravity = Gravity.CENTER
        text = "Sign In"
        isAllCaps = false
    }
}