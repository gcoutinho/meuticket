package com.meuticket.pos.shared.data

import com.meuticket.pos.core.storage.LocalStorage
import com.meuticket.pos.shared.data.model.Product
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type
import javax.inject.Inject

interface ProductsRepository {
    suspend fun listProductsFromRemote(): List<Product>
    suspend fun listProductsFromLocal(): List<Product>
    suspend fun update(product: Product)
    suspend fun insert(product: Product)
    suspend fun delete(product: Product)
}

class ProductsRepositoryImpl @Inject constructor(
    val localStorage: LocalStorage,
): ProductsRepository {

    override suspend fun listProductsFromLocal(): List<Product> {
        return localStorage.getProducts()
    }

    override suspend fun update(product: Product) {
        localStorage.updateProduct(product)
    }

    override suspend fun insert(product: Product) {
        localStorage.insertProduct(product)
    }

    override suspend fun delete(product: Product) {
        localStorage.deleteProduct(product)
    }

    override suspend fun listProductsFromRemote(): List<Product> {

        if(true) {
            val listType: Type = Types.newParameterizedType(
                List::class.java,
                Product::class.java
            )
            val adapter: JsonAdapter<List<Product>> = Moshi.Builder()
                .build().adapter(listType)

            return adapter.fromJson(
                "[\n" +
                        "  {\n" +
                        "    \"uid\": \"1\",\n" +
                        "    \"name\": \"Produto 1\",\n" +
                        "    \"category_uid\": 1," +
                        "    \"image\": \"https://www.imagensempng.com.br/wp-content/uploads/2022/01/2442.png\",\n" +
                        "    \"value\": 1\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"uid\": \"2\",\n" +
                        "    \"name\": \"Produto 2 Lanche\",\n" +
                        "    \"category_uid\": 2," +
                        "    \"image\": \"https://images.ctfassets.net/lcr8qbvxj7mh/3W2icVz4CWktIhfm5XRcYK/748774d37e23a5b9ba90cbf6f8d40734/BR_RBED_250_Single-Unit_close_cold_ORIGINAL_canwidth528px.png\",\n" +
                        "    \"value\": 1\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"uid\": \"3\",\n" +
                        "    \"name\": \"Produto 3\",\n" +
                        "    \"category_uid\": 1," +
                        "    \"image\": \"https://www.imagensempng.com.br/wp-content/uploads/2022/01/2442.png\",\n" +
                        "    \"value\": 1\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"uid\": \"4\",\n" +
                        "    \"name\": \"Produto 4\",\n" +
                        "    \"category_uid\": 1," +
                        "    \"image\": \"https://images.ctfassets.net/lcr8qbvxj7mh/3W2icVz4CWktIhfm5XRcYK/748774d37e23a5b9ba90cbf6f8d40734/BR_RBED_250_Single-Unit_close_cold_ORIGINAL_canwidth528px.png\",\n" +
                        "    \"value\": 1\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"uid\": \"5\",\n" +
                        "    \"name\": \"Produto 5\",\n" +
                        "    \"category_uid\": 1," +
                        "    \"image\": \"https://www.imagensempng.com.br/wp-content/uploads/2022/01/2442.png\",\n" +
                        "    \"value\": 1\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"uid\": \"6\",\n" +
                        "    \"name\": \"Produto 6\",\n" +
                        "    \"category_uid\": 1," +
                        "    \"image\": \"https://images.ctfassets.net/lcr8qbvxj7mh/3W2icVz4CWktIhfm5XRcYK/748774d37e23a5b9ba90cbf6f8d40734/BR_RBED_250_Single-Unit_close_cold_ORIGINAL_canwidth528px.png\",\n" +
                        "    \"value\": 1\n" +
                        "  }\n" +
                        "]"
            )!!

        }

        return localStorage.getProducts()
    }
}