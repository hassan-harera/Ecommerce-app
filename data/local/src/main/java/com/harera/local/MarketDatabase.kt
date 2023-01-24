package com.harera.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.harera.local.converter.AddressConverter
import com.harera.local.converter.DateConverter
import com.harera.local.model.*


@Database(
    version = 3,
    entities = [
        CartItemEntity::class,
        CategoryEntity::class,
        OfferEntity::class,
        UserEntity::class,
        WishItemEntity::class,
        ProductEntity::class,
    ],
    exportSchema = true,
)
@TypeConverters(DateConverter::class, AddressConverter::class)
abstract class MarketDatabase : RoomDatabase() {

    abstract fun getMarketDao(): MarketDao
    abstract fun getOfferDao(): OfferDao
}