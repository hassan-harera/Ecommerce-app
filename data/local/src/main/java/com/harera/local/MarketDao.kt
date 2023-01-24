package com.harera.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harera.local.model.CategoryEntity
import com.harera.local.model.ProductEntity

@Dao
interface MarketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(list: List<CategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(list: CategoryEntity)

    @Insert
    suspend fun insertProducts(list: List<ProductEntity>)

    @Query("select * from categoryEntity")
    suspend fun getCategories(): List<CategoryEntity>

    @Query("select * from ProductEntity where productId = :productId")
    suspend fun getProduct(productId: String): ProductEntity

    @Insert
    suspend fun insertProduct(product: ProductEntity)
}