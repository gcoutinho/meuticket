package com.meuticket.pos.shared.domain

import com.meuticket.pos.shared.data.CashRepository
import com.meuticket.pos.shared.data.model.CashRegister
import javax.inject.Inject

interface CashInteractor {
    suspend fun listAll(): List<CashRegister>
    suspend fun save(cashRegister: CashRegister)
    suspend fun getLast(): CashRegister?
}

class CashInteractorImpl @Inject constructor(
    val repository: CashRepository
): CashInteractor {

    override suspend fun listAll(): List<CashRegister> {
        return repository.listCashRegisters()
    }

    override suspend fun save(cashRegister: CashRegister) {
        repository.save(cashRegister)
    }

    override suspend fun getLast(): CashRegister? {
        return repository.getLastCashRegister()
    }

}
