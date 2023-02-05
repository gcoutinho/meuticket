package com.meuticket.pos.ui.components

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import com.meuticket.pos.R
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.databinding.ViewCardBinding
import com.meuticket.pos.ui.utils.animateClick
import com.meuticket.pos.ui.utils.animateZoom

class ViewCard @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    val binding by viewBinding(true, ViewCardBinding::inflate)

    var label: String = ""
    set(value) {
        field = value
        binding.cardLabel.text = value
    }

    var icon: Int = 0
    set(value) {
        field = 0
        binding.cardIcon.setImageResource(value)
    }

    init {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.ViewCard).use {
            label = it.getString(R.styleable.ViewCard_label)?:""
            icon = it.getResourceId(R.styleable.ViewCard_icon, 0)
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {

        super.setOnClickListener { p0 ->

            binding.cardIconCircle.animateZoom()

            binding.cardLabel.animateClick().withEndAction {
                l?.onClick(p0)
            }
        }
    }
}