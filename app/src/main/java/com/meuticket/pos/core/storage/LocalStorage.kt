package com.meuticket.pos.core.storage

import android.content.SharedPreferences
import android.util.Log
import com.meuticket.pos.core.storage.dao.UserDao
import com.meuticket.pos.shared.data.model.Category
import com.meuticket.pos.shared.data.model.Product
import com.meuticket.pos.shared.data.model.User
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type
import javax.inject.Inject

interface LocalStorage {

    fun saveOrder()
    fun getOrder()

    fun saveProduct()
    fun getProduct()

    fun getProducts(): List<Product>
    fun saveProducts(products: List<Product>)
    fun getUsers(): List<User>
    fun saveUsers(data: List<User>)
    fun getCategories(): List<Category>
    fun findUser(user: String, password: String): User?
}

class LocalStorageImpl @Inject constructor(
    val moshi: Moshi,
    val sharedPreferences: SharedPreferences,
    val userDao: UserDao
): LocalStorage {
    override fun saveOrder() {
        TODO("Not yet implemented")
    }

    override fun getOrder() {
        TODO("Not yet implemented")
    }

    override fun saveProduct() {
        TODO("Not yet implemented")
    }

    override fun getProduct() {
        TODO("Not yet implemented")
    }

    override fun getProducts(): List<Product> {
        val productsString = sharedPreferences.getString(PRODUCTS_LIST, "")?:""

        val listType: Type = Types.newParameterizedType(
            List::class.java,
            Product::class.java
        )
        val adapter: JsonAdapter<List<Product>> = moshi.adapter(listType)

        return try {
            adapter.fromJson(productsString)?: emptyList()
        } catch (ex: Exception) {
            emptyList()
        }
    }

    override fun saveProducts(products: List<Product>) {

        val listType: Type = Types.newParameterizedType(
            List::class.java,
            Product::class.java
        )
        val adapter: JsonAdapter<List<Product>> = moshi.adapter(listType)

        sharedPreferences
            .edit()
            .putString(PRODUCTS_LIST, adapter.toJson(products))
            .apply()
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

    override fun saveUsers(data: List<User>) {
        userDao.insertAll(data)
    }

    override fun getCategories(): List<Category> {
        val dataString = sharedPreferences.getString(CATEGORY_LIST, "")?:""

        val listType: Type = Types.newParameterizedType(
            List::class.java,
            Category::class.java
        )
        val adapter: JsonAdapter<List<Category>> = moshi.adapter(listType)

        return try {
            adapter.fromJson(dataString)?: emptyList()
        } catch (ex: Exception) {
            emptyList()
        }
    }

    override fun findUser(user: String, password: String): User? {
        return userDao.findByNamePassword(user, password)
    }

    companion object {
        const val PRODUCTS_LIST = "products_list"
        const val CATEGORY_LIST = "category_list"
    }

}