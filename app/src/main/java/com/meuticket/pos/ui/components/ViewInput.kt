package com.meuticket.pos.ui.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import com.meuticket.pos.R
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.databinding.ViewInputBinding
import com.meuticket.pos.ui.utils.drawableFromId
import com.meuticket.pos.ui.utils.hide
import com.meuticket.pos.ui.utils.show

class ViewInput @JvmOverloads constructor(
    context: Context, private val attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    val binding by viewBinding(true, ViewInputBinding::inflate)

    val editText: EditText = binding.inputEditText

    var label: String = ""
        set(value) {
            field = value
            binding.inputLabel.text = value
            if(value.isEmpty()) {
                binding.inputLabel.hide()
            } else {
                binding.inputLabel.show()
            }
        }

    var text: String
     get() {
         return binding.inputEditText.text.toString()
     }
    set(value) {
        binding.inputEditText.setText(value)
    }

    init {
        setupAttrs()
    }

    private fun setupAttrs() {
        val att = context.obtainStyledAttributes(
            attrs,
            R.styleable.ViewInput)

        att.let {
            label = it.getString(R.styleable.ViewInput_label)?:""

            it.getResourceId(R.styleable.ViewInput_drawableEnd, 0).takeIf { it > 0 }?.let { drawableId ->
                binding.drawableEnd.setImageResource(drawableId)
                binding.inputEditText.setPadding(0,0,
                    context.resources.getDimension(R.dimen.drawableEndMargin).toInt(), 0)
            }

            it.getInteger(R.styleable.ViewInput_imeOptions, 0).takeIf { it > 0 }?.let {
                binding.inputEditText.setSingleLine()
                binding.inputEditText.imeOptions = it
            }

            it.getInteger(R.styleable.ViewInput_inputType, 0).takeIf { it > 0 }?.let {
                binding.inputEditText.inputType = it
            }
            it.getString(R.styleable.ViewInput_hint)?.let {
                binding.inputEditText.hint = it
            }
        }
        att.recycle()
    }

    fun addTextWatcher(watcher: TextWatcher) {
        binding.inputEditText.addTextChangedListener(watcher)
    }
}
