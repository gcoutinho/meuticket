
package com.meuticket.pos.base

import com.meuticket.pos.core.session.Session
import com.meuticket.pos.login.presentation.LoginActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

open class BaseMvvmActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var vmFactory: DaggerViewModelFactory

    @Inject
    lateinit var session: Session

    inline fun <reified VM : BaseViewModel> appViewModel(): ViewModelDelegate<VM> {
        return ViewModelDelegate(VM::class, this) { this.vmFactory }
    }
}
