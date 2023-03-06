package com.meuticket.pos.ui.utils

import com.meuticket.pos.R
import com.meuticket.pos.base.BaseMvvmActivity
import com.meuticket.pos.ui.components.ViewDialog

fun BaseMvvmActivity.showAlertDialog(
    message: String,
    title: String = getString(R.string.warning_alert_title),
    primaryButtonText: String = getString(R.string.error_button_ok),
    primaryButtonAction: (() -> Unit)? = null
) {
    ViewDialog().apply {
        showNow(supportFragmentManager, "DIALOG")
        this.title = title
        description = message
        this.primaryButtonText = primaryButtonText
        setPrimaryButtonListener {
            primaryButtonAction?.invoke()
            dismissAllowingStateLoss()
        }
    }
}