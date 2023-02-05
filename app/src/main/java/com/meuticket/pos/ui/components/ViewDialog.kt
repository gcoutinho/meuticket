package com.meuticket.pos.ui.components

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.meuticket.pos.databinding.ViewDialogBinding
import com.meuticket.pos.ui.utils.hide
import com.meuticket.pos.ui.utils.show

class ViewDialog: DialogFragment() {

    lateinit var binding: ViewDialogBinding

    var title: String? = null
    set(value) {
        field = value
        executeIfBindingIsInitialized {
            if(value == null)
                it.dialogTitle.hide()
            else
                it.dialogTitle.apply {
                    text = value
                    show()
                }
        }
    }

    var description: String? = null
        set(value) {
            field = value
            executeIfBindingIsInitialized {
                if(value == null)
                    it.dialogDescription.hide()
                else
                    it.dialogDescription.apply {
                        text = value
                        show()
                    }
            }
        }

    var icon: Int? = null
        set(value) {
            field = value
            executeIfBindingIsInitialized {
                if(value == null)
                    it.dialogIcon.hide()
                else
                    it.dialogIcon.apply {
                        setImageResource(value)
                        show()
                    }
            }
        }

    var primaryButtonText: String? = null
        set(value) {
            field = value
            executeIfBindingIsInitialized {
                if(value == null)
                    it.dialogPrimaryButton.hide()
                else
                    it.dialogPrimaryButton.apply {
                        text = value
                        show()
                    }
            }
        }

    var secondaryButtonText: String? = null
        set(value) {
            field = value
            executeIfBindingIsInitialized {
                if(value == null)
                    it.dialogSecondaryButton.hide()
                else
                    it.dialogSecondaryButton.apply {
                        text = value
                        show()
                    }
            }
        }

    fun setPrimaryButtonListener(action: () -> Unit): ViewDialog {
        executeIfBindingIsInitialized {
            it.dialogPrimaryButton.setOnClickListener {
                action.invoke()
            }
        }
        return this
    }

    fun setSecondaryButtonListener(action: () -> Unit): ViewDialog {
        executeIfBindingIsInitialized {
            it.dialogSecondaryButton.setOnClickListener {
                action.invoke()
            }
        }
        return this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ViewDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    private fun executeIfBindingIsInitialized(block: (ViewDialogBinding) -> Unit) {
        if (this::binding.isInitialized) {
            block.invoke(binding)
        }
    }
}