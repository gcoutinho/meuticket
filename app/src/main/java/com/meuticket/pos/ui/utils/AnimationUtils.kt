package com.meuticket.pos.ui.utils

import android.view.View
import android.view.ViewPropertyAnimator

fun View.animateZoom(
    scale: Float = 1.3f,
    translation: Float = 25f,
    duration: Long = 150
) {

    animate()
        .scaleX(scale)
        .scaleY(scale)
        .translationY(translation)
        .translationZ(translation)
        .setDuration(duration)
        .withEndAction {
            animate()
                .scaleX(1f)
                .scaleY(1f)
                .translationY(0f)
                .translationZ(0f)
                .setDuration(duration)
                .start()
        }
        .start()
}

fun View.animateClick(
    scale: Float = 0.7f,
    duration: Long = 150
): ViewPropertyAnimator {
    val animation = animate()
        .scaleX(scale)
        .scaleY(scale)
        .setDuration(duration)
        .withEndAction {
            animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(duration)
                .start()
        }
        animation.start()
    return animation
}