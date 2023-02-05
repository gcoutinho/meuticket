package com.meuticket.pos.ui.components

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.databinding.ViewCellProductBinding
import com.meuticket.pos.ui.utils.animateClick
import com.squareup.picasso.Picasso

class ViewProductCell @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    val binding by viewBinding(true, ViewCellProductBinding::inflate)

    var image: String = ""
    set(value) {
        field = value
        Picasso.get().load(value).into(binding.image)
    }

    var title: String = ""
    set(value) {
        field = value
        binding.title.text = value
    }

    var value: String = ""
    set(value) {
        field = value
        binding.value.text = value
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener {
            l?.onClick(it)
            animateClick(
                scale = 0.8f, duration = 100
            ).withEndAction {

            }
        }
    }
}