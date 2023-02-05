package com.meuticket.pos.ui.components

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.databinding.ViewCellRegisterBinding
import com.meuticket.pos.ui.utils.animateClick
import com.meuticket.pos.ui.utils.hide
import com.meuticket.pos.ui.utils.show
import com.squareup.picasso.Picasso

class ViewRegisterCell @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    val binding by viewBinding(true, ViewCellRegisterBinding::inflate)

    var image: String? = null
    set(value) {
        field = value
        if(value == null)
            binding.image.hide()
        else{
            binding.image.show()
            Picasso.get().load(value).into(binding.image)
        }
    }

    var title: String = ""
    set(value) {
        field = value
        binding.title.text = value
    }

    var subtitle: String = ""
    set(value) {
        field = value
        binding.subtitle.text = value
    }

    fun setEditClickListener(l: OnClickListener?) {
        binding.edit.setOnClickListener {
            binding.edit.animateClick(duration = 100).withEndAction {
                l?.onClick(it)
            }
        }
    }

    fun setDeleteClickListener(l: OnClickListener?) {
        binding.delete.setOnClickListener {
            binding.delete.animateClick(duration = 100).withEndAction {
                l?.onClick(it)
            }
        }
    }
}