package com.meuticket.pos.ui.utils

import com.meuticket.pos.R
import com.meuticket.pos.base.BaseMvvmActivity
import com.meuticket.pos.ui.components.ViewDialog

fun BaseMvvmActivity.showAlertDialog(
    title: String = getString(R.string.warning_alert_title),
    message: String,
    primaryButtonText: String = getString(R.string.error_button_ok),
    primaryButtonAction: (() -> Unit)? = null
) {
    ViewDialog().apply {
        showNow(supportFragmentManager, "DIALOG")
        this.title = title
        description = message
        this.primaryButtonText = primaryButtonText
        setPrimaryButtonListener {
            if(primaryButtonAction != null)
                primaryButtonAction.invoke()
            else
                dismissAllowingStateLoss()
        }
    }
}