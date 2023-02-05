package com.meuticket.pos.ui.utils

import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat

fun View.hide() {
    visibility = View.GONE
}
fun View.show() {
    visibility = View.VISIBLE
}
fun View.drawableFromId(it: Int): Drawable? {
    return ContextCompat.getDrawable(context, it)
}