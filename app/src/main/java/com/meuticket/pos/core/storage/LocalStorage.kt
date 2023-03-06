package com.meuticket.pos.core.storage

import android.util.Log
import com.meuticket.pos.core.storage.dao.*
import com.meuticket.pos.shared.data.model.*
import java.util.UUID
import javax.inject.Inject

interface LocalStorage {

    fun saveOrder()
    fun getOrder()

    fun getProducts(): List<Product>
    fun saveProducts(products: List<Product>)
    fun getUsers(): List<User>
    fun saveUsers(data: List<User>)
    fun getCategories(): List<Category>
    fun findUser(user: String, password: String): User?
    fun getEvents(): List<Event>
    fun saveCategories(data: List<Category>)
    fun saveUser(username: String, password: String, admin: Boolean)
    fun updateUser(uid: String, username: String, password: String, admin: Boolean)
    fun deleteUser(user: User)
    fun updateProduct(product: Product)
    fun insertProduct(product: Product)
    fun deleteProduct(product: Product)
    fun deleteCategory(category: Category)
    fun saveCategory(category: Category)
    fun saveEvent(event: Event)
    fun saveEvents(data: List<Event>)
    fun deleteEvent(event: Event)
    fun listCashRegisters(): List<CashRegister>
    fun getLastCashRegister(): CashRegister?
    fun saveCashRegister(cashRegister: CashRegister)
}

class LocalStorageImpl @Inject constructor(
    val userDao: UserDao,
    val productDao: ProductDao,
    val categoryDao: CategoryDao,
    val eventDao: EventDao,
    val cashRegisterDao: CashRegisterDao
): LocalStorage {
    override fun saveOrder() {
        TODO("Not yet implemented")
    }

    override fun getOrder() {
        TODO("Not yet implemented")
    }

    override fun getProducts(): List<Product> {

        return try {
            productDao.getAll()
        } catch (ex: Exception) {
            ex.printStackTrace()
            emptyList()
        }
    }

    override fun saveProducts(products: List<Product>) {

        productDao.insertAll(products)
    }

    override fun getUsers(): List<User> {

        return try {
            val users = userDao.getAll()
            users
        } catch (ex: Exception) {
            Log.e("DB", ex.message?:ex.stackTraceToString())
            emptyList()
        }
    }

    override fun saveUser(username: String, password: String, admin: Boolean) {
        userDao.insert(User(uid = UUID.randomUUID().toString(), name = username, password = password, admin = admin))
    }

    override fun updateUser(uid: String, username: String, password: String, admin: Boolean) {
        userDao.update(User(uid, username, password, admin))
    }

    override fun deleteUser(user: User) {
        userDao.delete(user)
    }

    override fun updateProduct(product: Product) {
        productDao.update(product)
    }

    override fun insertProduct(product: Product) {
        productDao.insert(product)
    }

    override fun deleteProduct(product: Product) {
        productDao.delete(product)
    }

    override fun deleteCategory(category: Category) {
        categoryDao.delete(category)
    }

    override fun saveCategory(category: Category) {
        categoryDao.save(category)
    }

    override fun saveEvent(event: Event) {
        eventDao.insertOrUpdate(event)
    }

    override fun saveEvents(data: List<Event>) {
        eventDao.insertAll(data)
    }

    override fun deleteEvent(event: Event) {
        eventDao.delete(event)
    }

    override fun listCashRegisters(): List<CashRegister> {
        return cashRegisterDao.getAll()
    }

    override fun getLastCashRegister(): CashRegister? {
        return cashRegisterDao.findLast()
    }

    override fun saveCashRegister(cashRegister: CashRegister) {
        cashRegisterDao.save(cashRegister)
    }

    override fun saveUsers(data: List<User>) {
        userDao.insertAll(data)
    }

    override fun getCategories(): List<Category> {

        return try {
            categoryDao.getAll()
        } catch (ex: Exception) {
            ex.printStackTrace()
            emptyList()
        }
    }

    override fun getEvents(): List<Event> {
        return try {
            eventDao.getAll()
        } catch (ex: Exception) {
            ex.printStackTrace()
            emptyList()
        }
    }

    override fun saveCategories(data: List<Category>) {
        categoryDao.insertAll(data)
    }

    override fun findUser(user: String, password: String): User? {
        return userDao.findByNamePassword(user, password)
    }

}