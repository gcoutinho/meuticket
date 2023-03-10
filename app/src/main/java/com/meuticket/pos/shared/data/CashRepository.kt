package com.meuticket.pos.shared.data

import com.meuticket.pos.core.storage.LocalStorage
import com.meuticket.pos.shared.data.model.CashRegister
import javax.inject.Inject

interface CashRepository {
    fun listCashRegisters(): List<CashRegister>
    fun getLastCashRegister(): CashRegister?
    fun save(cashRegister: CashRegister)
}
class CashRepositoryImpl @Inject constructor(
    val localStorage: LocalStorage
): CashRepository {
    override fun listCashRegisters(): List<CashRegister> {
        return localStorage.listCashRegisters()
    }

    override fun getLastCashRegister(): CashRegister? {
        return localStorage.getLastCashRegister()
    }

    override fun save(cashRegister: CashRegister) {
        localStorage.saveCashRegister(cashRegister)
    }
}