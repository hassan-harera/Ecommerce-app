package com.harera.ecommerce.local.di

import android.app.Application
import com.harera.ecommerce.local.LocalDataSource
import com.harera.ecommerce.local.LocalDataSourceImpl
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

//    @Provides
//    @Singleton
//    fun bindMarketDatabase(application: Application): MarketDatabase =
//        Room
//            .databaseBuilder(
//                application,
//                MarketDatabase::class.java,
//                "Market"
//            )
////            .addTypeConverter(TimestampConverter::class.java)
//            .build()

    @Provides
    @Singleton
    fun bindMarketDao(): LocalDataSource =
        LocalDataSourceImpl()

    @Provides
    @Singleton
    fun bindMarketSqlDelight(application: Application): AndroidSqliteDriver =
        AndroidSqliteDriver(
            context = application,
            name = "movies.db",
            schema = Schema,
        )

    object Schema : SqlDriver.Schema {
        override val version: Int
            get() = 1

        override fun create(driver: SqlDriver) {

        }

        override fun migrate(driver: SqlDriver, oldVersion: Int, newVersion: Int) {

        }
    }
}