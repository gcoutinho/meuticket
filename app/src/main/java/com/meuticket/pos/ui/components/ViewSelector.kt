package com.meuticket.pos.ui.components

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.databinding.ViewSelectorBinding

class ViewSelector @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    val binding by viewBinding(attachToRoot = true, ViewSelectorBinding::inflate)

    var label: String = ""
        set(value) {
            field = value
            binding.selectorLabel.text = value
        }

    override fun setElevation(elevation: Float) {
        super.setElevation(elevation)

        binding.innerCard.elevation = elevation
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        if(selected){
            binding.selectorLabel.setTypeface(null, Typeface.BOLD)
            elevation = 12f
        } else {
            binding.selectorLabel.setTypeface(null, Typeface.NORMAL)
            elevation = 0f
        }
    }
}