package com.meuticket.pos.ui.components

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.Toolbar
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.databinding.ViewToolbarBinding

class ViewToolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : Toolbar(context, attrs) {

    val binding by viewBinding(attachToRoot = true, ViewToolbarBinding::inflate)

    init {

    }

    override fun setTitle(resId: Int) {
        super.setTitle(resId)
        binding.title.setText(resId)
    }

    override fun setTitle(title: CharSequence?) {
        super.setTitle(title)
        binding.title.text = title
    }
}