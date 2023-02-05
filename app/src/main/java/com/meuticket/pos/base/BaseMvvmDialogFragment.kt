
package com.meuticket.pos.base

import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

open class BaseMvvmDialogFragment : DaggerDialogFragment() {

    @Inject
    lateinit var factory: DaggerViewModelFactory

    /**
     * Create a Activity View Model, a shared view model between Activity and DialogFragment
     */
    inline fun <reified VM : BaseViewModel> appActivityViewModel(): ActivityVMFragmentDelegate<VM> =
        ActivityVMFragmentDelegate(VM::class) {
            this.requireBaseActivity().vmFactory
        }

    /**
     * Create a DialogFragment own View Model
     */
    inline fun <reified VM : BaseViewModel> appViewModel(): FragmentViewModelDelegate<VM> =
        FragmentViewModelDelegate(VM::class, this) { factory }

    fun requireBaseActivity() = (this.requireActivity() as BaseMvvmActivity)
}
