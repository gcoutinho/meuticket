package com.meuticket.pos.base

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

inline fun <VB : ViewBinding> Activity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> VB
) = lazy(LazyThreadSafetyMode.NONE) {
    bindingInflater(layoutInflater)
}

fun <VB : ViewBinding> ViewGroup.viewBinding(
    attachToRoot: Boolean,
    binding: (LayoutInflater, ViewGroup, Boolean) -> VB
) = lazy(LazyThreadSafetyMode.NONE) {
    binding(LayoutInflater.from(context), this, attachToRoot)
}


fun <VB : ViewBinding> ViewGroup.bindViewBinding(
    attachToRoot: Boolean,
    binding: (LayoutInflater, ViewGroup, Boolean) -> VB
): VB {
    return binding(LayoutInflater.from(context), this, attachToRoot)
}
