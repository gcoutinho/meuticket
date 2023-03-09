package com.meuticket.pos.payment.presentation

import com.meuticket.pos.base.BaseViewModel
import com.meuticket.pos.core.livedata.SingleLiveEvent
import com.meuticket.pos.core.payment.IPayment
import com.meuticket.pos.shared.data.model.Order
import com.meuticket.pos.shared.domain.CashInteractor
import java.util.*
import javax.inject.Inject

sealed class PaymentViewModelState {
    class ShowInputValue(val mode: Order.OrderPayment.Mode, val action: (value: Double) -> Unit): PaymentViewModelState()
    class OrderUpdate(order: Order, remaining: Double) : PaymentViewModelState()
}
class PaymentViewModel @Inject constructor(
    val payment: IPayment,
    val cashInteractor: CashInteractor
): BaseViewModel() {

    val state = SingleLiveEvent<PaymentViewModelState>()

    fun payWithCash() {
        state.value = PaymentViewModelState.ShowInputValue(Order.OrderPayment.Mode.Cash) {
            addPaymentToCurrentOrder(Order.OrderPayment(it, Order.OrderPayment.Mode.Cash))
        }
    }

    fun payWithCredit() {
        state.value = PaymentViewModelState.ShowInputValue(Order.OrderPayment.Mode.Credit) {
            addPaymentToCurrentOrder(Order.OrderPayment(it, Order.OrderPayment.Mode.Credit))
        }
    }

    fun payWithDebit() {
        state.value = PaymentViewModelState.ShowInputValue(Order.OrderPayment.Mode.Debit) {
            addPaymentToCurrentOrder(Order.OrderPayment(it, Order.OrderPayment.Mode.Debit))
        }
    }

    fun payWithPix() {
        state.value = PaymentViewModelState.ShowInputValue(Order.OrderPayment.Mode.Pix) {
            addPaymentToCurrentOrder(Order.OrderPayment(it, Order.OrderPayment.Mode.Pix))
        }
    }

    fun payWithCortesy() {
        state.value = PaymentViewModelState.ShowInputValue(Order.OrderPayment.Mode.Cortesy) {
            addPaymentToCurrentOrder(Order.OrderPayment(it, Order.OrderPayment.Mode.Cortesy))
        }
    }

    fun payWithOthers() {
        state.value = PaymentViewModelState.ShowInputValue(Order.OrderPayment.Mode.Other) {
            addPaymentToCurrentOrder(Order.OrderPayment(it, Order.OrderPayment.Mode.Other))
        }
    }

    fun finishPayment() {

    }

    private fun addPaymentToCurrentOrder(orderPayment: Order.OrderPayment) {
        runAsync({
            val currentCash = cashInteractor.getLast()!!

            if(currentCash.order == null) {
                currentCash.order = Order(
                    UUID.randomUUID().toString(),
                    currentCash.uid,
                    0,
                    Date().time
                )
            }

            currentCash.order!!.payments.add(orderPayment)
            currentCash.order!!.total = currentCash.order!!.payments.sumOf { it.value }

            cashInteractor.save(currentCash)

            currentCash
        }, onSuccess = {
            state.value = PaymentViewModelState.OrderUpdate(
                it.order!!,
                it.order!!.total - (it.order!!.payments.sumOf { it.value })
            )
        })

    }
}