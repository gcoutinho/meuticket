package com.meuticket.pos.admin.presentation

import com.meuticket.pos.base.BaseViewModel
import com.meuticket.pos.core.livedata.SingleLiveEvent
import com.meuticket.pos.shared.data.model.CashRegister
import com.meuticket.pos.shared.domain.CashInteractor
import javax.inject.Inject

sealed class AdminViewModelState {
    class ShowError(val message: String): AdminViewModelState()
    object GoToOpenCash: AdminViewModelState()
    class GoToCloseCash(val cashRegister: CashRegister): AdminViewModelState()
}
class AdminViewModel @Inject constructor(
    val cashInteractor: CashInteractor
): BaseViewModel() {

    val state = SingleLiveEvent<AdminViewModelState>()

    fun openCashClicked() {
        runAsync({
            val lastCash = cashInteractor.getLast()
            if(lastCash != null && lastCash.closeDate == null) {
                //current cash is opened
                throw RuntimeException("O caixa já está aberto!")
            } else {
                AdminViewModelState.GoToOpenCash
            }
        }, onSuccess = {
            state.value = it
        }, onError = {
            state.value = AdminViewModelState.ShowError(it.message?:it.toString())
        })


    }

    fun closeCashClicked() {
        runAsync({
            val lastCash = cashInteractor.getLast()
            if(lastCash == null || lastCash.closeDate != null) {
                //no cash opened
                throw RuntimeException("O caixa não está aberto!")
            } else {
                AdminViewModelState.GoToCloseCash(lastCash)
            }
        }, onSuccess = {
            state.value = it
        }, onError = {
            state.value = AdminViewModelState.ShowError(it.message?:it.toString())
        })
    }
}