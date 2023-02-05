package com.meuticket.pos.ui.components

import android.content.Context
import android.util.AttributeSet
import com.meuticket.pos.ui.utils.animateClick

class ViewButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : androidx.appcompat.widget.AppCompatButton(context, attrs) {

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener {

            animateClick(
                scale = 0.8f, duration = 100
            ).withEndAction {
                l?.onClick(it)
            }
        }
    }
}