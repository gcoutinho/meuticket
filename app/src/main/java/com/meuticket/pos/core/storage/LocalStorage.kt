package com.meuticket.pos.core.storage

import android.util.Log
import com.meuticket.pos.core.storage.dao.CategoryDao
import com.meuticket.pos.core.storage.dao.EventDao
import com.meuticket.pos.core.storage.dao.ProductDao
import com.meuticket.pos.core.storage.dao.UserDao
import com.meuticket.pos.shared.data.model.Category
import com.meuticket.pos.shared.data.model.Event
import com.meuticket.pos.shared.data.model.Product
import com.meuticket.pos.shared.data.model.User
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
    fun updateUser(uid: Int, username: String, password: String, admin: Boolean)
    fun deleteUser(user: User)
}

class LocalStorageImpl @Inject constructor(
    val userDao: UserDao,
    val productDao: ProductDao,
    val categoryDao: CategoryDao,
    val eventDao: EventDao
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
        userDao.insert(User(name = username, password = password, admin = admin))
    }

    override fun updateUser(uid: Int, username: String, password: String, admin: Boolean) {
        userDao.update(User(uid, username, password, admin))
    }

    override fun deleteUser(user: User) {
        userDao.delete(user)
    }

    override fun saveUsers(data: List<User>) {
        userDao.insertAll(data)
    }

    override fun getCategories(): List<Category> {

        return try {
            categoryDao.getAll()
        } catch (ex: Exception) {
            emptyList()
        }
    }

    override fun getEvents(): List<Event> {
        return try {
            eventDao.getAll()
        } catch (ex: Exception) {
            emptyList()
        }
    }

    override fun saveCategories(data: List<Category>) {
        categoryDao.insertAll(data)
    }

    override fun findUser(user: String, password: String): User? {
        return userDao.findByNamePassword(user, password)
    }

    companion object {
        const val PRODUCTS_LIST = "products_list"
        const val CATEGORY_LIST = "category_list"
    }

}